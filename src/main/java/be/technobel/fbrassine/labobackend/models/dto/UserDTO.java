package be.technobel.fbrassine.labobackend.models.dto;

import be.technobel.fbrassine.labobackend.models.entity.User;
import be.technobel.fbrassine.labobackend.models.entity.UserRole;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class UserDTO {
    private final Long id;
    private final String email;
    private final String name;
    private final String firstName;
    private final String phoneNumber;
    private final String adress;
    public static UserDTO toDto(User entity) {
        if( entity == null )
            return null;

        return new UserDTO(
                entity.getId(),
                entity.getEmail(),
                entity.getName(),
                entity.getFirstName(),
                entity.getPhoneNumber(),
                entity.getAdress()
        );
    }
}
