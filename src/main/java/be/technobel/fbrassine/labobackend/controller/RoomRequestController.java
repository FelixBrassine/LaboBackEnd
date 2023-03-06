package be.technobel.fbrassine.labobackend.controller;

import be.technobel.fbrassine.labobackend.models.dto.RegisterRequestDTO;
import be.technobel.fbrassine.labobackend.models.dto.RoomRequestDTO;
import be.technobel.fbrassine.labobackend.models.entity.RequestStatus;
import be.technobel.fbrassine.labobackend.models.form.RoomRequestForm;
import be.technobel.fbrassine.labobackend.service.RoomRequestService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/request")
public class RoomRequestController {
    private final RoomRequestService requestService;

    public RoomRequestController(RoomRequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping("/all")
    public List<RoomRequestDTO> getAll(Authentication authentication){
        return requestService.getAll(authentication.getPrincipal().toString());
    }
    @GetMapping("/{id:[0-9]+}")
    public RoomRequestDTO getOne(@PathVariable long id, Authentication authentication){
        return requestService.getOne(id, authentication.getPrincipal().toString());
    }
    @DeleteMapping("/{id:[0-9]+}")
    public void deleteOneById(@PathVariable long id, Authentication authentication){
        requestService.deleteOneById(id, authentication.getPrincipal().toString());
    }
    @PostMapping("/add")
    public void create(@RequestBody @Valid RoomRequestForm form, Authentication authentication){
        requestService.create(form, authentication.getPrincipal().toString());
    }
    @PatchMapping("{id:[0-9]+}/refuse")
    public void refuseRequest(@PathVariable Long id){
        requestService.refuseRequest(id);
    }
    @PatchMapping("{id:[0-9]+}/accept")
    public void acceptRequest(@PathVariable Long id, @RequestParam long roomId){
        requestService.acceptRequest(id, roomId);
    }
    @PatchMapping("{id:[0-9]+}/relocate")
    public void relocateRequest(@PathVariable Long id, @RequestParam long roomId){
        requestService.relocateRequest(id, roomId);
    }
}
