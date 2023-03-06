package be.technobel.fbrassine.labobackend.service.impl;

import be.technobel.fbrassine.labobackend.exceptions.AlreadyContainException;
import be.technobel.fbrassine.labobackend.exceptions.ResourceNotFoundException;
import be.technobel.fbrassine.labobackend.models.dto.RoomDTO;
import be.technobel.fbrassine.labobackend.models.entity.Material;
import be.technobel.fbrassine.labobackend.models.entity.Room;
import be.technobel.fbrassine.labobackend.models.form.RoomForm;
import be.technobel.fbrassine.labobackend.repository.MaterialRepository;
import be.technobel.fbrassine.labobackend.repository.RoomRepository;
import be.technobel.fbrassine.labobackend.service.RoomService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final MaterialRepository materialRepository;

    public RoomServiceImpl(RoomRepository roomRepository,
                           MaterialRepository materialRepository) {
        this.roomRepository = roomRepository;
        this.materialRepository = materialRepository;
    }

    @Override
    public RoomDTO getOne(Long id) {
        return roomRepository.findById(id)
                .map(RoomDTO::toDto)
                .orElseThrow( () -> new ResourceNotFoundException(Room.class, id) );
    }

    @Override
    public List<RoomDTO> getAll() {
        return roomRepository.findAll().stream()
                .map(RoomDTO::toDto)
                .toList();
    }
    @Override
    public void create(RoomForm form) {
        roomRepository.save(form.toEntity());
    }
    @Override
    public void update(Long id, RoomForm form) {
        Room toUpdate = roomRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException(Room.class, id) );

        toUpdate.setName( form.getName() );
        toUpdate.setNumberPlaces( form.getNumberPlaces() );
        toUpdate.setMaterials(
                new HashSet<>(materialRepository.findAllById(form.getMaterialsId()))
        );
        toUpdate.setTeacherRoom( form.isTeacherRoom() );

        roomRepository.save(toUpdate);
    }
    @Override
    public void delete(Long id) {

        Room entity = roomRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException(Room.class, id) );

        roomRepository.delete(entity);
    }

    @Override
    public void addMaterial(Long materialId, Long roomId) {

        Room room = roomRepository.findById(roomId)
                .orElseThrow( () -> new ResourceNotFoundException(Room.class, roomId) );

        Material material = materialRepository.findById(materialId)
                .orElseThrow( () -> new ResourceNotFoundException(Material.class, materialId) );

        if ( room.getMaterials().contains( material ) )
            throw new AlreadyContainException(Room.class, roomId, Material.class, materialId);

        room.getMaterials().add(material);

        roomRepository.save(room);
    }
}
