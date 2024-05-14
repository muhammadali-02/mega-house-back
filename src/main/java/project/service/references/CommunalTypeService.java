package project.service.references;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.dto.references.CommunalTypeDto;
import project.dto.references.CommunalTypeForm;
import project.exception.RecordNotFoundException;
import project.model.references.CommunalType;
import project.repository.references.CommunalTypeRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommunalTypeService {
    private final CommunalTypeRepository modelRepository;

    public List<CommunalTypeDto> listAllDto() {
        return modelRepository.findAllModels();
    }

    public void save(CommunalType model) {
        modelRepository.save(model);
    }

    public CommunalType findById(Long id) {
        return modelRepository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        modelRepository.deleteById(id);
    }

    public String create(CommunalTypeForm form) {
        save(convertToModel(form, new CommunalType()));
        return "Successfully created";
    }

    public String update(CommunalTypeForm form) {
        if (form.getId() == null || form.getId().equals(0L)) throw new RecordNotFoundException("Invalid id...");
        save(convertToModel(form, findById(form.getId())));
        return "Successfully updated";
    }

    public CommunalType convertToModel(CommunalTypeForm form, CommunalType model) {
        model.setNameUz(form.getNameUz());
        model.setNameRu(form.getNameRu());
        model.setType(form.getType());
        model.setCode(form.getCode());
        return model;
    }
}
