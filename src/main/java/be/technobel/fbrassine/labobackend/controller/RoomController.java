package be.technobel.fbrassine.labobackend.controller;

import be.technobel.fbrassine.labobackend.models.form.RoomForm;
import be.technobel.fbrassine.labobackend.service.RoomService;
import be.technobel.fbrassine.labobackend.models.dto.RoomDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping({"", "/all"})
    public List<RoomDTO> getAll(){
        return roomService.getAll();
    }
    @GetMapping("/{id:[0-9]+}")
    public RoomDTO getOne(@PathVariable long id){
        return roomService.getOne(id);
    }
    @PostMapping("/add")
    public void create(@RequestBody @Valid RoomForm form){
        roomService.create(form);
    }
    @PutMapping("/{id:[0-9]+}/update")
    public void update(@PathVariable long id, @RequestBody @Valid RoomForm form){
        roomService.update(id, form);
    }
    @PatchMapping("/{id:[0-9]+}")
    public void addProduct(@RequestParam Long idProduct, @PathVariable long id){
        roomService.addMaterial(idProduct,id);
    }
    @DeleteMapping("/{id:[0-9]+}")
    public void delete(@PathVariable long id){
        roomService.delete(id);
    }
}
