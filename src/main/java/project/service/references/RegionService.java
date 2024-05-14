package project.service.references;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.dto.references.RegionDto;
import project.exception.RecordNotFoundException;
import project.model.Status;
import project.model.references.Region;
import project.repository.references.RegionRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegionService {
    private final RegionRepository modelRepository;

    public void save(Region model) {
        modelRepository.save(model);
    }

    public Region findById(Long id) {
        return modelRepository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        try {
            modelRepository.deleteById(id);
        } catch (Exception e) {
            Region region = findById(id);
            region.setStatus(Status.DELETED);
            save(region);
        }
    }

    public RegionDto create(RegionDto form) {
        Region region = convertToModel(form, new Region());
        if (form.getParentId() != null) region.setParent(findById(form.getParentId()));
        save(region);
        if (region.getParent() != null && region.getParent().getId() != null) {
            if (region.getParent().getHierarchy() != null)
                region.setHierarchy(region.getParent().getHierarchy() + "," + region.getId().toString());
            else region.setHierarchy(region.getParent().getId() + "," + region.getId().toString());
        } else region.setHierarchy(region.getId().toString());
        save(region);
        return new RegionDto(region);
    }

    public RegionDto update(RegionDto form) {
        if (form.getId() == null || form.getId().equals(0L)) throw new RecordNotFoundException("Invalid id...");
        Region region = findById(form.getId());
        if (region == null) throw new RecordNotFoundException("NOT FOUND");
        convertToModel(form, region);
        if (form.getParentId() != null)
            region.setParent(findById(form.getParentId()));
        save(region);
        if (region.getParent() != null && region.getParent().getId() != null) {
            if (region.getParent().getHierarchy() != null) {
                region.setHierarchy(region.getParent().getHierarchy() + "," + region.getId().toString());
            } else region.setHierarchy(region.getParent().getId() + "," + region.getId().toString());
        } else region.setHierarchy(region.getId().toString());
        save(region);
        return new RegionDto(region);
    }

    public Region convertToModel(RegionDto form, Region model) {
        model.setNameRu(form.getNameRu());
        model.setNameUz(form.getNameUz());
        model.setNameEn(form.getNameEn());
        model.setOrder(form.getOrder());
        model.setSoato(form.getSoato());
        return model;
    }

    public List<Region> findAllByParentIsNull() {
        return modelRepository.findAllByParentIsNull(Status.DELETED);
    }

    public List<Region> findAllByParentId(Long parentId) {
        return modelRepository.findAllByParentIdAndStatusNot(parentId, Status.DELETED);
    }

    public Region getRegionBySoato(Long regionSoato) {
        return modelRepository.getRegionBySoato(regionSoato, Status.DELETED);
    }

    public List<Region> findAllUzbRegions(Long uzb) {
        return modelRepository.findAllUzbRegions(uzb);
    }
}
