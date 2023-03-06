package be.technobel.fbrassine.labobackend.service;

import be.technobel.fbrassine.labobackend.models.dto.RoomDTO;
import be.technobel.fbrassine.labobackend.models.form.RoomForm;

import java.util.List;

public interface RoomService {
    List<RoomDTO> getAll();
    RoomDTO getOne(Long id);
    void create(RoomForm form);
    void update(Long id, RoomForm form);
    void delete(Long id);
    void addMaterial(Long materialId, Long roomId);
}