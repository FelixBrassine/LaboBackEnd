package be.technobel.fbrassine.labobackend.controller;

import be.technobel.fbrassine.labobackend.models.dto.MaterialDTO;
import be.technobel.fbrassine.labobackend.models.form.MaterialForm;
import be.technobel.fbrassine.labobackend.service.MaterialService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/material")
public class MaterialController {
    private final MaterialService materialService;

    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @GetMapping("/all")
    public List<MaterialDTO> displayMaterials(){
        return materialService.getAll();
    }
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid MaterialForm form) {
        materialService.create(form);
    }
    @PatchMapping("/{id:[0-9]+}")
    public void patchName(@PathVariable Long id, @RequestParam String name){
        materialService.patchName(id,name);
    }
    @GetMapping("/{id:[0-9]+}")
    public MaterialDTO getOne(@PathVariable long id){
        return materialService.getOne(id);
    }
    @DeleteMapping("/{id:[0-9]+}")
    public void delete(@PathVariable long id){
        materialService.delete(id);
    }
}
