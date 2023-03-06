package be.technobel.fbrassine.labobackend.repository;

import be.technobel.fbrassine.labobackend.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

//    @Query("select u from User u where u.login = ?1")
    Optional<User> findByLogin(String login);
    boolean existsByEmail(String email);

}
