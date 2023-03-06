package be.technobel.fbrassine.labobackend.service.impl;

import be.technobel.fbrassine.labobackend.exceptions.ResourceNotFoundException;
import be.technobel.fbrassine.labobackend.models.dto.MaterialDTO;
import be.technobel.fbrassine.labobackend.models.entity.Material;
import be.technobel.fbrassine.labobackend.models.form.MaterialForm;
import be.technobel.fbrassine.labobackend.repository.MaterialRepository;
import be.technobel.fbrassine.labobackend.service.MaterialService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialServiceImpl implements MaterialService {

    private final MaterialRepository materialRepository;
    public MaterialServiceImpl(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    @Override
    public List<MaterialDTO> getAll() {
        return materialRepository.findAll().stream()
                .map( MaterialDTO::toDto )
                .toList();
    }

    @Override
    public MaterialDTO getOne(Long id) {
        return materialRepository.findById(id)
                .map(MaterialDTO::toDto)
                .orElseThrow( () -> new ResourceNotFoundException(Material.class, id) );
    }

    @Override
    public void patchName(Long id, String name) {
        Material material = materialRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException(Material.class, id) );
        material.setName(name);
        materialRepository.save(material);
    }

    @Override
    public void create(MaterialForm form) {
        materialRepository.save( form.toEntity() );
    }


    @Override
    public void delete(Long id) {
        Material material = materialRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException(Material.class, id) );
        materialRepository.save(material);
    }
}
