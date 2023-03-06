package be.technobel.fbrassine.labobackend.models.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Status{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_id", nullable = false)
    private Long id;

    @Column(name = "date")
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    @Column(name = "request_status", nullable = false)
    private RequestStatus status;

    @ManyToOne
    @JoinColumn(name = "register_request_id")
    private RegisterRequest registerRequest;

    @ManyToOne
    @JoinColumn(name = "room_request_id")
    private RoomRequest roomRequest;
}
