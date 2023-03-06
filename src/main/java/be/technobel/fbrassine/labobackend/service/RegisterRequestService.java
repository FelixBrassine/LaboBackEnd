package be.technobel.fbrassine.labobackend.service;

import be.technobel.fbrassine.labobackend.models.dto.RegisterRequestDTO;
import be.technobel.fbrassine.labobackend.models.entity.RequestStatus;
import be.technobel.fbrassine.labobackend.models.form.RegisterRequestForm;

import java.util.List;

public interface RegisterRequestService {

    List<RegisterRequestDTO> getAll();
    RegisterRequestDTO getOne(Long id);
    void update(Long id, RegisterRequestForm form);
    void refuseRequest(Long id);
    void acceptRequest(Long id);
}
