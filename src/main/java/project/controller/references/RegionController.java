package project.controller.references;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.dto.references.RegionDto;
import project.exception.RecordNotFoundException;
import project.model.references.Region;
import project.service.references.RegionService;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v.1/region")
public class RegionController {
    private final RegionService modelService;

    @RequestMapping(value = "list", method = GET)
    @PreAuthorize("hasAuthority('List References')")
    public ResponseEntity<List<RegionDto>> listAll() {
        List<Region> regions = modelService.findAllByParentIsNull();
        List<RegionDto> regionDto = new ArrayList<>(regions.size());
        regions.forEach(region -> regionDto.add(new RegionDto(region, modelService, true)));
        return new ResponseEntity<>(regionDto, OK);
    }

    @RequestMapping(value = "listByParent", method = GET)
    @PreAuthorize("hasAuthority('List References')")
    public ResponseEntity<List<RegionDto>> listRegionByParent(@RequestParam(required = false) Long parentId) {
        List<Region> regions;
        if (parentId == null || parentId.equals(0L)) regions = modelService.findAllByParentIsNull();
        else regions = modelService.findAllByParentId(parentId);
        List<RegionDto> regionDto = new ArrayList<>(regions.size());
        regions.forEach(region -> regionDto.add(new RegionDto(region)));
        return new ResponseEntity<>(regionDto, OK);
    }

    @RequestMapping(value = "get-by-id/{id}", method = GET)
    @PreAuthorize("hasAuthority('Get References')")
    public ResponseEntity<RegionDto> regionById(@PathVariable Long id) {
        Region region = modelService.findById(id);
        if (region == null) throw new RecordNotFoundException("NOT FOUND");
        return new ResponseEntity<>(new RegionDto(region), OK);
    }


    @RequestMapping(value = "get-by-soato/{soato}", method = GET)
    @PreAuthorize("hasAuthority('Get References')")
    public ResponseEntity<RegionDto> regionBySoato(@PathVariable Long soato) {
        Region region = modelService.getRegionBySoato(soato);
        if (region == null) throw new RecordNotFoundException("NOT FOUND");
        return new ResponseEntity<>(new RegionDto(region), OK);
    }

    @RequestMapping(value = "create", method = POST)
    @PreAuthorize("hasAuthority('Create References')")
    public ResponseEntity<RegionDto> create(@Valid @RequestBody RegionDto regionForm) {
        return ResponseEntity.ok(modelService.create(regionForm));
    }

    @RequestMapping(value = "update", method = PUT)
    @PreAuthorize("hasAuthority('Update References')")
    public ResponseEntity<RegionDto> update(@RequestBody RegionDto form) {
        return ResponseEntity.ok(modelService.update(form));
    }

    @RequestMapping(value = "delete/{id}", method = DELETE)
    @PreAuthorize("hasAuthority('Delete References')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        modelService.delete(id);
        return ResponseEntity.ok("SUCCESSFULLY Deleted...");
    }
}
