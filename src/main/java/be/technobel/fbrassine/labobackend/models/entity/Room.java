package be.technobel.fbrassine.labobackend.models.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter @Setter
public class Room {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column(name = "room_id", nullable = false)
    private long id;
    @Column( nullable = false )
    private String name;
    @Column( nullable = false )
    private int numberPlaces;
    @Column( nullable = false )
    private boolean teacherRoom;

    @ManyToMany
    private Set<Material> materials = new LinkedHashSet<>();
    @OneToMany(mappedBy = "room")
    private Set<RoomRequest> roomRequests = new LinkedHashSet<>();

}
