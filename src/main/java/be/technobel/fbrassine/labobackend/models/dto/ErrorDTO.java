package be.technobel.fbrassine.labobackend.models.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorDTO {

    private final String message;
    private final HttpStatus status;
    private final LocalDateTime requestMadeAt;
    private final String URI;

}
