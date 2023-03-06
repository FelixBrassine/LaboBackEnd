package be.technobel.fbrassine.labobackend.models.dto;

import be.technobel.fbrassine.labobackend.models.entity.Material;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MaterialDTO {
    private final long id;
    private final String name;

    public static MaterialDTO toDto(Material entity){

        if( entity == null )
            return null;

        return MaterialDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();

    }
}
