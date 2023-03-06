package be.technobel.fbrassine.labobackend.models.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter @Setter
public class RegisterRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "register_request_id", nullable = false)
    private Long id;
    @Column(nullable = false)
    private LocalDateTime date;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String confirm;
    @Column(nullable = false)
    private String login;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private String adress;
    @OneToMany(mappedBy = "registerRequest", cascade = CascadeType.ALL)
    private Set<Status> RegisterStatusHistory = new LinkedHashSet<>();
}
