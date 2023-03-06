package be.technobel.fbrassine.labobackend.controller;

import be.technobel.fbrassine.labobackend.models.dto.RegisterRequestDTO;
import be.technobel.fbrassine.labobackend.models.entity.RequestStatus;
import be.technobel.fbrassine.labobackend.models.form.RegisterRequestForm;
import be.technobel.fbrassine.labobackend.service.RegisterRequestService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/register")
public class RegisterRequestController {

    private final RegisterRequestService requestService;

    public RegisterRequestController(RegisterRequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping("/all")
    public List<RegisterRequestDTO> getAll(){
        return requestService.getAll();
    }
    @GetMapping("/{id:[0-9]+}")
    public RegisterRequestDTO getOne(@PathVariable long id){
        return requestService.getOne(id);
    }
    @PutMapping("/{id:[0-9]+}")
    public void update(@PathVariable long id, @RequestBody @Valid RegisterRequestForm form){
        requestService.update(id, form);
    }
    @PatchMapping("/{id:[0-9]+}/refuse")
    public void processRefusalForm( @PathVariable long id){
        requestService.refuseRequest(id);
    }
    @PatchMapping("/{id:[0-9]+}/accept")
    public void processAcceptalForm( @PathVariable long id){
        requestService.acceptRequest(id);
    }
}