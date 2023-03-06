package be.technobel.fbrassine.labobackend.service;

import be.technobel.fbrassine.labobackend.models.dto.RegisterRequestDTO;
import be.technobel.fbrassine.labobackend.models.dto.RoomRequestDTO;
import be.technobel.fbrassine.labobackend.models.entity.RequestStatus;
import be.technobel.fbrassine.labobackend.models.form.RoomRequestForm;

import java.util.List;

public interface RoomRequestService {
    void create(RoomRequestForm form, String login);
    RoomRequestDTO getOne(Long id, String username);
    void deleteOneById(Long id, String username);
    List<RoomRequestDTO> getAll(String username);
    void refuseRequest(Long id);
    void relocateRequest(Long roomId, Long requestId);
    void acceptRequest(Long roomId, Long requestId);
}
