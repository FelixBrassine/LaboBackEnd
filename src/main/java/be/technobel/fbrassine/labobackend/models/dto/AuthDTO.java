package be.technobel.fbrassine.labobackend.models.dto;

import be.technobel.fbrassine.labobackend.models.entity.UserRole;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class AuthDTO {

    private final String token;
    private final String login;
    private final Set<UserRole> roles;

}
