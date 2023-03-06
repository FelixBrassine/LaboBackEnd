package be.technobel.fbrassine.labobackend.models.form;

import be.technobel.fbrassine.labobackend.models.entity.Material;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MaterialForm {
    @NotBlank(message = "Enter a name")
    private String name;

    public Material toEntity(){
        Material material = new Material();
        material.setName(name);
        return material;
    }
}
