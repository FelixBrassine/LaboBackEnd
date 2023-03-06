package be.technobel.fbrassine.labobackend.repository;

import be.technobel.fbrassine.labobackend.models.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room,Long> {
}
