package be.technobel.fbrassine.labobackend.models.dto;

import be.technobel.fbrassine.labobackend.models.entity.RoomRequest;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Data
public class RoomRequestDTO {

    private final Long id;
    private final LocalDateTime beginAt;
    private final LocalDateTime endAt;
    private final int neededCapacity;
    private final String justification;
    private final List<StatusDTO> statusHistory;
    private final String currentStatus;
    private final UserDTO madeBy;
    private final RoomDTO room;

    public static RoomRequestDTO toDto(RoomRequest entity){
        if( entity == null )
            return null;

        List<StatusDTO> statusHistory = entity.getRoomStatusHistory().stream()
                .map(StatusDTO::toDto)
                .sorted(Comparator.comparing(StatusDTO::getDate).reversed())
                .toList();

        String currentStatus = statusHistory.stream()
                .findFirst()
                .map(status -> status.getStatus().name())
                .orElse(null);

        return new RoomRequestDTO(
                entity.getId(),
                entity.getDate().atTime(entity.getBeginTime()),
                entity.getDate().atTime(entity.getEndTime()),
                entity.getNeededCapacity(),
                entity.getJustification(),
                statusHistory,
                currentStatus,
                UserDTO.toDto(entity.getMadeBy()),
                RoomDTO.toDto(entity.getRoom())
        );


    }
}
