package be.technobel.fbrassine.labobackend.models.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter @Setter
public class RoomRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_request_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    private LocalTime beginTime;
    @Column(nullable = false)
    private LocalTime endTime;

    @Column(name = "needed_capacity", nullable = false)
    private int neededCapacity;

    @Column(nullable = false)
    private String justification;

    @OneToMany(mappedBy = "roomRequest", cascade = CascadeType.ALL)
    private Set<Status> RoomStatusHistory = new LinkedHashSet<>();
    @ManyToMany
    private Set<Material> materials = new LinkedHashSet<>();
    @ManyToOne
    @JoinColumn(name = "made_by_id", nullable = false)
    private User madeBy;
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

}
