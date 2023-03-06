package be.technobel.fbrassine.labobackend.models.dto;

import be.technobel.fbrassine.labobackend.models.entity.Room;
import lombok.Builder;
import lombok.Data;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
public class RoomDTO {
    private long id;
    private String name;
    private int numberPlaces;
    private Set<MaterialDTO> materials;
    private boolean teacherRoom;
    public static RoomDTO toDto(Room entity){

        if( entity == null )
            return null;

        return RoomDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .numberPlaces(entity.getNumberPlaces())
                .teacherRoom(entity.isTeacherRoom())
                .materials(
                        entity.getMaterials().stream()
                                .map(MaterialDTO::toDto)
                                .collect(Collectors.toSet())
                )
                .build();

    }
}
