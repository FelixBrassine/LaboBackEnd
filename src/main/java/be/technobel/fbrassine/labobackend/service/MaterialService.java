package be.technobel.fbrassine.labobackend.service;

import be.technobel.fbrassine.labobackend.models.dto.MaterialDTO;
import be.technobel.fbrassine.labobackend.models.form.MaterialForm;

import java.util.List;

public interface MaterialService {

    List<MaterialDTO> getAll();
    MaterialDTO getOne(Long id);
    void patchName(Long id, String name);
    void create( MaterialForm form );
    void delete( Long id );
}
