package be.technobel.fbrassine.labobackend.models.dto;

import be.technobel.fbrassine.labobackend.models.entity.RequestStatus;
import be.technobel.fbrassine.labobackend.models.entity.Status;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class StatusDTO implements Serializable {
    private final LocalDateTime date;
    private final Long id;
    private final RequestStatus status;

    public static StatusDTO toDto(Status entity){
        if( entity == null )
            return null;

        return new StatusDTO(
                entity.getDate(),
                entity.getId(),
                entity.getStatus()
        );
    }

}
