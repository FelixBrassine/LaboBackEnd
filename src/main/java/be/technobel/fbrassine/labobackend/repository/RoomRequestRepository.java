package be.technobel.fbrassine.labobackend.repository;

import be.technobel.fbrassine.labobackend.models.entity.RequestStatus;
import be.technobel.fbrassine.labobackend.models.entity.RoomRequest;
import be.technobel.fbrassine.labobackend.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRequestRepository extends JpaRepository<RoomRequest, Long> {
    List<RoomRequest> findByMadeBy(User user);
    @Query("SELECT r FROM RoomRequest r WHERE r.date < NOW()")
    List<RoomRequest> findDatePassed();
}
