package be.technobel.fbrassine.labobackend.repository;

import be.technobel.fbrassine.labobackend.models.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepository extends JpaRepository<Material,Long> {
}
