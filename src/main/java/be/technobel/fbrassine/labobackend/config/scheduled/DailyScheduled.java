package be.technobel.fbrassine.labobackend.config.scheduled;

import be.technobel.fbrassine.labobackend.models.entity.RequestStatus;
import be.technobel.fbrassine.labobackend.models.entity.RoomRequest;
import be.technobel.fbrassine.labobackend.models.entity.Status;
import be.technobel.fbrassine.labobackend.repository.RoomRequestRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
@EnableAsync
@EnableScheduling
public class DailyScheduled {

    private final RoomRequestRepository repository;

    public DailyScheduled(RoomRequestRepository repository) {
        this.repository = repository;
    }

    @Async
    @Scheduled(cron="001***")
    public void removePastRequest(){
        repository.findDatePassed().stream()
            .forEach(request -> {
        Status status = new Status();
        status.setRoomRequest(request);
        status.setDate(LocalDateTime.now());
        status.setStatus(RequestStatus.PASSED);
        repository.save(request);
        });
    }
}
