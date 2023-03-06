package be.technobel.fbrassine.labobackend.models.dto;

import be.technobel.fbrassine.labobackend.models.entity.RegisterRequest;
import lombok.Data;

import java.util.Comparator;
import java.util.List;

@Data
public class RegisterRequestDTO {
    private final Long id;
    private final String email;
    private final String name;
    private final String firstName;
    private final String login;
    private final String phoneNumber;
    private final String adress;
    private final List<StatusDTO> statusHistory;
    private final String currentStatus;

    public static RegisterRequestDTO toDto(RegisterRequest entity){
        if( entity == null )
            return null;

        List<StatusDTO> statusHistory = entity.getRegisterStatusHistory().stream()
                .map(StatusDTO::toDto)
                .sorted(Comparator.comparing(StatusDTO::getDate).reversed())
                .toList();

        String currentStatus = statusHistory.stream()
                .findFirst()
                .map(status -> status.getStatus().name())
                .orElse(null);

        return new RegisterRequestDTO(
                entity.getId(),
                entity.getEmail(),
                entity.getName(),
                entity.getFirstName(),
                entity.getLogin(),
                entity.getPhoneNumber(),
                entity.getAdress(),
                statusHistory,
                currentStatus
        );
    }
}
