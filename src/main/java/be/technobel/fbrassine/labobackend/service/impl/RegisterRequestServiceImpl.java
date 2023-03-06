package be.technobel.fbrassine.labobackend.service.impl;

import be.technobel.fbrassine.labobackend.exceptions.RequestStatusException;
import be.technobel.fbrassine.labobackend.models.dto.RegisterRequestDTO;
import be.technobel.fbrassine.labobackend.exceptions.ResourceNotFoundException;
import be.technobel.fbrassine.labobackend.models.entity.*;
import be.technobel.fbrassine.labobackend.models.form.RegisterRequestForm;
import be.technobel.fbrassine.labobackend.repository.RegisterRequestRepository;
import be.technobel.fbrassine.labobackend.repository.UserRepository;
import be.technobel.fbrassine.labobackend.service.EmailService;
import be.technobel.fbrassine.labobackend.service.RegisterRequestService;
import org.apache.catalina.connector.Request;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class RegisterRequestServiceImpl implements RegisterRequestService {

    private final RegisterRequestRepository requestRepository;
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final EmailService emailService;

    public RegisterRequestServiceImpl(RegisterRequestRepository requestRepository,
                                      PasswordEncoder encoder,
                                      UserRepository userRepository,
                                      EmailService emailService) {
        this.requestRepository = requestRepository;
        this.encoder = encoder;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }


    @Override
    public List<RegisterRequestDTO> getAll() {
        return requestRepository.findAll().stream()
                .map(RegisterRequestDTO::toDto)
                .toList();
    }


    @Override
    public RegisterRequestDTO getOne(Long id) {
        return requestRepository.findById(id)
                .map(RegisterRequestDTO::toDto)
                .orElseThrow( () -> new ResourceNotFoundException(Request.class, id));
    }

    @Override
    public void update(Long id, RegisterRequestForm form) {
        RegisterRequest toUpdate = requestRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException(Request.class, id) );
        toUpdate.setEmail(form.getEmail());
        toUpdate.setName(form.getName());
        toUpdate.setFirstName(form.getFirstName());
        toUpdate.setLogin(form.getFirstName().toLowerCase().charAt(0) + form.getName().toLowerCase());
        toUpdate.setPhoneNumber(form.getPhoneNumber());
        form.setAdress(form.getAdress());
        requestRepository.save(toUpdate);
    }

    @Override
    public void acceptRequest(Long id) {
        RegisterRequest request = requestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Request.class, id));

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setName(request.getName());
        user.setLogin(request.getLogin());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setAdress(request.getAdress());
        Set<UserRole> studentRoles = new HashSet<>();
        studentRoles.add(UserRole.STUDENT);
        user.setRoles(studentRoles);

        userRepository.save(user);

        Status currentStatus = request.getRegisterStatusHistory().stream()
                .max(Comparator.comparing(Status::getDate))
                .orElseThrow( () -> new RequestStatusException());

        if( currentStatus != null && currentStatus.getStatus() != RequestStatus.PENDING )
            throw new RequestStatusException();

        Status status = new Status();
        status.setRegisterRequest(request);
        status.setDate(LocalDateTime.now());
        status.setStatus(RequestStatus.ACCEPTED);

        request.getRegisterStatusHistory().add(status);
        requestRepository.save(request);
        emailService.sendRequestInfo(user,"Register Accepted", "WELCOME IN LABOBACKEND APP ! ");
    }
    @Override
    public void refuseRequest(Long id) {
        RegisterRequest request = requestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Request.class, id));

        Status currentStatus = request.getRegisterStatusHistory().stream()
                .max(Comparator.comparing(Status::getDate))
                .orElseThrow( () -> new RequestStatusException());


        if( currentStatus != null && currentStatus.getStatus() != RequestStatus.PENDING )
            throw new RequestStatusException();

        Status status = new Status();
        status.setRegisterRequest(request);
        status.setDate(LocalDateTime.now());
        status.setStatus(RequestStatus.REFUSED);

        request.getRegisterStatusHistory().add(status);
        requestRepository.save(request);
    }
}
