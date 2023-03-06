package be.technobel.fbrassine.labobackend.models.form;

import be.technobel.fbrassine.labobackend.models.entity.Material;
import be.technobel.fbrassine.labobackend.models.entity.Room;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.Set;

@Data
public class RoomForm {
    @NotBlank(message = "Enter a name")
    @NotNull(message = "Enter a name")
    private String name;
    @Positive (message = "Enter a value greater than 0")
    private int numberPlaces;
    private boolean teacherRoom;
    private Set<Long> materialsId;
    public Room toEntity(){
        Room room = new Room();
        room.setName(name);
        room.setNumberPlaces(numberPlaces);
        room.setTeacherRoom(teacherRoom);
        return room;
    }
}
