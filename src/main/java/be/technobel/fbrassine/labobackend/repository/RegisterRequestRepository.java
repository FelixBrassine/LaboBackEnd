package be.technobel.fbrassine.labobackend.repository;

import be.technobel.fbrassine.labobackend.models.entity.RegisterRequest;
import be.technobel.fbrassine.labobackend.models.entity.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RegisterRequestRepository extends JpaRepository<RegisterRequest, Long> {

}
