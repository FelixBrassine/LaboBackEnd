package be.technobel.fbrassine.labobackend.service.impl;

import be.technobel.fbrassine.labobackend.exceptions.LocateRoomNotValidException;
import be.technobel.fbrassine.labobackend.exceptions.RequestStatusException;
import be.technobel.fbrassine.labobackend.exceptions.ResourceNotFoundException;
import be.technobel.fbrassine.labobackend.models.dto.RoomRequestDTO;
import be.technobel.fbrassine.labobackend.models.entity.*;
import be.technobel.fbrassine.labobackend.models.form.RoomRequestForm;
import be.technobel.fbrassine.labobackend.repository.MaterialRepository;
import be.technobel.fbrassine.labobackend.repository.RoomRepository;
import be.technobel.fbrassine.labobackend.repository.RoomRequestRepository;
import be.technobel.fbrassine.labobackend.repository.UserRepository;
import be.technobel.fbrassine.labobackend.service.EmailService;
import be.technobel.fbrassine.labobackend.service.RoomRequestService;
import be.technobel.fbrassine.labobackend.service.RoomService;
import org.apache.catalina.connector.Request;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;

@Service
public class RoomRequestServiceImpl implements RoomRequestService {
    private  final UserRepository userRepository;
    private  final RoomRequestRepository roomRequestRepository;
    private final MaterialRepository materialRepository;
    private final EmailService emailService;
    private final RoomService roomService;
    private final RoomRepository roomRepository;

    public RoomRequestServiceImpl(UserRepository userRepository,
                                  RoomRequestRepository roomRequestRepository,
                                  MaterialRepository materialRepository,
                                  EmailService emailService,
                                  RoomService roomService,
                                  RoomRepository roomRepository) {
        this.userRepository = userRepository;
        this.roomRequestRepository = roomRequestRepository;
        this.materialRepository = materialRepository;
        this.emailService = emailService;
        this.roomService = roomService;
        this.roomRepository = roomRepository;
    }


    @Override
    public void create(RoomRequestForm form, String login) {
        RoomRequest roomRequest = form.toEntity();

        User user = userRepository.findByLogin(login)
                .orElseThrow( () -> new ResourceNotFoundException(User.class, login));

        roomRequest.setMadeBy(user);
        roomRequest.setJustification(form.getJustification());
        roomRequest.setNeededCapacity(form.getNeededCapacity());
        roomRequest.setDate(form.getDate());
        roomRequest.setBeginTime(form.getBeginAt());
        roomRequest.setEndTime(form.getEndAt());
        roomRequest.setMaterials(
                new LinkedHashSet<>(materialRepository.findAllById(form.getMaterialIds()))
        );
        Status status = new Status();
        status.setRoomRequest(roomRequest);
        status.setStatus(RequestStatus.PENDING);

        roomRequest.getRoomStatusHistory().add(status);

        roomRequestRepository.save( roomRequest );

        User admin = userRepository.findById(1L)
                .orElseThrow( () -> new ResourceNotFoundException(User.class, login));

        String text = "A new request with id : " + roomRequest.getId()  + " from : "
                + user.getFirstName() + " " + user.getName() + "was made.";

        emailService.sendRequestInfo(admin, "New Request", text);
    }

    @Override
    public List<RoomRequestDTO> getAll(String username) {

        User user = userRepository.findByLogin(username)
                .orElseThrow( () -> new ResourceNotFoundException(User.class,username));
        if (user.getRoles().contains(UserRole.ADMIN)){
            return roomRequestRepository.findAll().stream()
                    .map(RoomRequestDTO::toDto)
                    .toList();
        }
        return roomRequestRepository.findByMadeBy(user).stream()
                .map(RoomRequestDTO::toDto)
                .toList();
    }
    @Override
    public RoomRequestDTO getOne(Long id, String username) {

        User user = userRepository.findByLogin(username)
                .orElseThrow( () -> new ResourceNotFoundException(User.class,username));
        RoomRequest  request = roomRequestRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException( RoomRequest.class, id));
        RoomRequestDTO  requestDTO = roomRequestRepository.findById(id)
                .map(RoomRequestDTO::toDto)
                .orElseThrow( () -> new ResourceNotFoundException( RoomRequest.class, id));

        if (user.getRoles().contains(UserRole.ADMIN)){
            return requestDTO;
        } else if (request.getMadeBy() == user) {
            return requestDTO;
        }else{
            throw new ResourceNotFoundException(RoomRequest.class, id);
        }
    }

    @Override
    public void deleteOneById(Long id, String username) {

        User user = userRepository.findByLogin(username)
                .orElseThrow( () -> new ResourceNotFoundException(User.class,username));
        RoomRequest  request = roomRequestRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException( RoomRequest.class, id));

        if (user.getRoles().contains(UserRole.ADMIN)){
            roomRequestRepository.delete(request);
        } else if (request.getMadeBy() == user) {
            roomRequestRepository.delete(request);
        }else{
            throw new ResourceNotFoundException(RoomRequest.class, id);
        }
    }

    @Override
    public void refuseRequest(Long id) {

        RoomRequest request = roomRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Request.class, id));

        Status currentStatus = request.getRoomStatusHistory().stream()
                .max(Comparator.comparing(Status::getDate))
                .orElseThrow(RequestStatusException::new);


        if( currentStatus != null && currentStatus.getStatus() != RequestStatus.PENDING )
            throw new RequestStatusException();

        Status status = new Status();
        status.setRoomRequest(request);
        status.setDate(LocalDateTime.now());
        status.setStatus(RequestStatus.REFUSED);
        request.getRoomStatusHistory().add(status);
        roomRequestRepository.save(request);

        String text = "Your request with the id : " + id +
                " for the : " + request.getDate().getDayOfMonth() + " / "+ request.getDate().getMonth() + " / "+ request.getDate().getYear() +
                " have been refused . " ;
        emailService.sendRequestInfo(request.getMadeBy(), "Room request refused", text);
    }

    @Override
    public void relocateRequest(Long roomId, Long requestId) {

        RoomRequest request = roomRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException(Request.class, requestId));
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException(Request.class, roomId));

        Status currentStatus = request.getRoomStatusHistory().stream()
                .max(Comparator.comparing(Status::getDate))
                .orElseThrow(RequestStatusException::new);


        if( currentStatus != null && currentStatus.getStatus() != RequestStatus.PENDING )
            throw new RequestStatusException();

        Status status = new Status();
        status.setRoomRequest(request);
        status.setDate(LocalDateTime.now());

        request.getRoomStatusHistory().add(status);
        if (request.getMadeBy().getRoles().contains(UserRole.ADMIN)
                || request.getMadeBy().getRoles().contains(UserRole.TEACHER)){
            status.setStatus(RequestStatus.RELOCATING);
            request.setRoom(room);
        } else if (room.isTeacherRoom() && !(request.getMadeBy().getRoles().contains(UserRole.ADMIN))
                && !(request.getMadeBy().getRoles().contains(UserRole.TEACHER))){
            status.setStatus(RequestStatus.PENDING);
            throw new LocateRoomNotValidException(Room.class, requestId);
        } else {
            status.setStatus(RequestStatus.RELOCATING);
            request.setRoom(room);
        }
        roomRequestRepository.save(request);

        String text = "Your request with the id : " + requestId +
                " for the : " + request.getDate().getDayOfMonth() + " / "+ request.getDate().getMonth() + " / "+ request.getDate().getYear() +
                " have been relocated  at the room : " + roomId + " ." ;
        emailService.sendRequestInfo(request.getMadeBy(), "Room request relocated", text);
    }

    @Override
    public void acceptRequest(Long roomId, Long requestId) {

        RoomRequest request = roomRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException(Request.class, requestId));

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException(Request.class, roomId));

        Status currentStatus = request.getRoomStatusHistory().stream()
                .max(Comparator.comparing(Status::getDate))
                .orElseThrow(RequestStatusException::new);


        if( currentStatus != null && currentStatus.getStatus() != RequestStatus.PENDING )
            throw new RequestStatusException();

        Status status = new Status();
        status.setRoomRequest(request);
        status.setDate(LocalDateTime.now());

        request.getRoomStatusHistory().add(status);
        if (request.getMadeBy().getRoles().contains(UserRole.ADMIN)
                || request.getMadeBy().getRoles().contains(UserRole.TEACHER)){
            status.setStatus(RequestStatus.ACCEPTED);
            request.setRoom(room);
        } else if (room.isTeacherRoom() && !(request.getMadeBy().getRoles().contains(UserRole.ADMIN))
                && !(request.getMadeBy().getRoles().contains(UserRole.TEACHER))){
            status.setStatus(RequestStatus.PENDING);
            throw new LocateRoomNotValidException(Room.class, requestId);
        } else {
            status.setStatus(RequestStatus.ACCEPTED);
            request.setRoom(room);
        }
        roomRequestRepository.save(request);

        String text = "Your request with the id : " + requestId +
                " for the : " + request.getDate().getDayOfMonth() + " / "+ request.getDate().getMonth() + " / "+ request.getDate().getYear() +
                " have been accepted at the room : " + room.getName()+ " ." ;
        emailService.sendRequestInfo(request.getMadeBy(), "Room request accepted", text);
    }
}
