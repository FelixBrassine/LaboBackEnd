package be.technobel.fbrassine.labobackend.utils;

import be.technobel.fbrassine.labobackend.models.entity.*;
import be.technobel.fbrassine.labobackend.repository.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Component
@Log4j2
public class DataInit implements InitializingBean {
    private final MaterialRepository materialRepository;
    private final RegisterRequestRepository registerRequestRepository;
    private final RoomRepository roomRepository;
    private final RoomRequestRepository roomRequestRepository;
    private final UserRepository repository;
    private final PasswordEncoder encoder;
    public DataInit(UserRepository repository,
                    RoomRequestRepository roomRequestRepository,
                    RegisterRequestRepository registerRequestRepository,
                    RoomRepository roomRepository,
                    MaterialRepository materialRepository,
                    PasswordEncoder encoder) {
        this.repository = repository;
        this.roomRequestRepository = roomRequestRepository;
        this.registerRequestRepository = registerRequestRepository;
        this.roomRepository = roomRepository;
        this.materialRepository = materialRepository;
        this.encoder = encoder;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        log.info("-- INITIALIZING DB DATA --");

        //USER

        User admin = new User();
        admin.setEmail("admin@admin");
        admin.setPassword(encoder.encode("Aa.0"));
        admin.setName("admin");
        admin.setAdress("rue du curé 5 1234 outsimplou");
        admin.setFirstName("admin");
        admin.setLogin("aadmin");
        admin.setPhoneNumber("0123456789");
        Set<UserRole> adminRoles = new HashSet<>();
        adminRoles.add(UserRole.ADMIN);
        adminRoles.add(UserRole.TEACHER);
        adminRoles.add(UserRole.STUDENT);
        admin.setRoles(adminRoles);
        repository.save(admin);

        User teacher = new User();
        teacher.setEmail("teacher@teacher");
        teacher.setPassword(encoder.encode("Aa.0"));
        teacher.setName("teacher");
        teacher.setAdress("rue du curé 5 1234 outsimplou");
        teacher.setFirstName("teacher");
        teacher.setLogin("tteacher");
        teacher.setPhoneNumber("0123456789");
        Set<UserRole> teacherRoles = new HashSet<>();
        teacherRoles.add(UserRole.TEACHER);
        teacherRoles.add(UserRole.STUDENT);
        teacher.setRoles(teacherRoles);
        repository.save(teacher);

        User student = new User();
        student.setEmail("brassine1987@gmail.com");
        student.setPassword(encoder.encode("Aa.0"));
        student.setName("Brassine");
        student.setAdress("rue du curé 5 1234 outsimplou");
        student.setFirstName("Félix");
        student.setLogin("fbrassine");
        student.setPhoneNumber("0123456789");
        Set<UserRole> studentRoles = new HashSet<>();
        studentRoles.add(UserRole.STUDENT);
        student.setRoles(studentRoles);
        repository.save(student);


        //MATERIAL

        Material material1 = new Material();
        material1.setName("Projecteur");
        materialRepository.save(material1);

        Material material2 = new Material();
        material2.setName("Tableau numérique");
        materialRepository.save(material2);

        Set<Material> materialSet1 = new HashSet<>();
        materialSet1.add(material1);
        materialSet1.add(material2);

        Set<Material> materialSet2 = new HashSet<>();
        materialSet2.add(material1);

        //ROOM

        Room room1 = new Room();
        room1.setTeacherRoom(true);
        room1.setNumberPlaces(20);
        room1.setName("Teacher room");
        room1.setMaterials(materialSet1);
        roomRepository.save(room1);

        Room room2 = new Room();
        room2.setTeacherRoom(false);
        room2.setNumberPlaces(50);
        room2.setName("Student room");
        room2.setMaterials(materialSet2);
        roomRepository.save(room2);


        //REGISTER REQUEST

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("register@request.com");
        registerRequest.setDate(LocalDateTime.now());
        registerRequest.setPassword(encoder.encode("Aa.0"));
        registerRequest.setConfirm(encoder.encode("Aa.0"));
        registerRequest.setName("Register");
        registerRequest.setFirstName("Request");
        registerRequest.setPhoneNumber("0123456789");
        registerRequest.setAdress("Eue du register 1, 1234 Request");
        registerRequest.setLogin("rrequest");
        Set<Status> statusHistory1 = new HashSet<>();
        Status status1 = new Status();
        status1.setStatus(RequestStatus.PENDING);
        status1.setRegisterRequest(registerRequest);
        status1.setDate(LocalDateTime.now());
        statusHistory1.add(status1);
        registerRequest.setRegisterStatusHistory(statusHistory1);
        registerRequestRepository.save(registerRequest);

        //ROOM REQUEST

        RoomRequest roomRequest = new RoomRequest();
        roomRequest.setDate(LocalDate.now().plusDays(5));
        roomRequest.setJustification("It's just for the test !");
        roomRequest.setBeginTime(LocalTime.of(10,43,12));
        roomRequest.setEndTime(LocalTime.of(10,43,12));
        roomRequest.setMadeBy(student);
        roomRequest.setNeededCapacity(30);
        roomRequest.setMaterials(materialSet2);
        Set<Status> statusHistory = new HashSet<>();
        Status status = new Status();
        status.setStatus(RequestStatus.PENDING);
        status.setRoomRequest(roomRequest);
        status.setDate(LocalDateTime.now());
        statusHistory.add(status);
        roomRequest.setRoomStatusHistory(statusHistory);
        roomRequestRepository.save(roomRequest);

        RoomRequest roomRequest2 = new RoomRequest();
        roomRequest2.setDate(LocalDate.now().plusDays(5));
        roomRequest2.setJustification("It's just for the test !");
        roomRequest2.setBeginTime(LocalTime.of(10,43,12));
        roomRequest2.setEndTime(LocalTime.of(10,43,12));
        roomRequest2.setMadeBy(admin);
        roomRequest2.setNeededCapacity(30);
        roomRequest2.setMaterials(materialSet2);
        Set<Status> statusHistory2 = new HashSet<>();
        Status status2 = new Status();
        status2.setStatus(RequestStatus.PENDING);
        status2.setRoomRequest(roomRequest2);
        status2.setDate(LocalDateTime.now());
        statusHistory2.add(status2);
        roomRequest2.setRoomStatusHistory(statusHistory2);
        roomRequestRepository.save(roomRequest2);

        log.info("-- DATA INIT FINISHED --");
    }
}
