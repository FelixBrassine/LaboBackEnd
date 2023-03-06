package be.technobel.fbrassine.labobackend.models.form;

import be.technobel.fbrassine.labobackend.models.dto.RoomDTO;
import be.technobel.fbrassine.labobackend.models.entity.RoomRequest;
import be.technobel.fbrassine.labobackend.validation.constraint.MaxTime;
import be.technobel.fbrassine.labobackend.validation.constraint.MinFuture;
import be.technobel.fbrassine.labobackend.validation.constraint.MinTime;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Data
public class RoomRequestForm {
    @NotBlank
    private String justification;
    @Min(1) @Max(300)
    @NotNull
    private Integer neededCapacity;
    @MinFuture(amount = 3, unit = ChronoUnit.DAYS)
    private LocalDate date;
    @MinTime(h=8, m=30)
    private LocalTime beginAt;
    @MaxTime(h=20)
    private LocalTime endAt;
    private List<Long> materialIds;

    public RoomRequest toEntity(){
        RoomRequest request = new RoomRequest();

        request.setDate(date);
        request.setJustification(justification);
        request.setBeginTime(beginAt);
        request.setEndTime(endAt);
        request.setNeededCapacity(neededCapacity);

        return request;
    }

}
