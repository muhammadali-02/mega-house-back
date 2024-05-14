package project.controller.references;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.dto.references.CommunalTypeDto;
import project.dto.references.CommunalTypeForm;
import project.model.references.CommunalType.Code;
import project.service.references.CommunalTypeService;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v.1/communal-type/")
public class CommunalTypeController {
    private final CommunalTypeService modelService;

    @RequestMapping(value = "list", method = GET)
    @PreAuthorize("hasAuthority('List References')")
    public ResponseEntity<List<CommunalTypeDto>> listAllDto() {
        return new ResponseEntity<>(modelService.listAllDto(), OK);
    }

    @RequestMapping(value = "codes", method = GET)
    @PreAuthorize("hasAuthority('Get References')")
    public ResponseEntity<Code[]> codes() {
        return ResponseEntity.ok(Code.values());
    }

    @RequestMapping(value = "get-by-id/{id}", method = GET)
    @PreAuthorize("hasAuthority('Get References')")
    public ResponseEntity<CommunalTypeDto> findById(@PathVariable Long id) {
        return new ResponseEntity<>(new CommunalTypeDto(modelService.findById(id)), OK);
    }

    @RequestMapping(value = "create", method = POST)
    @PreAuthorize("hasAuthority('Create References')")
    public ResponseEntity<String> create(@RequestBody CommunalTypeForm form) {
        return ResponseEntity.ok(modelService.create(form));
    }

    @RequestMapping(value = "update", method = PUT)
    @PreAuthorize("hasAuthority('Update References')")
    public ResponseEntity<String> update(@RequestBody CommunalTypeForm form) {
        return ResponseEntity.ok(modelService.update(form));
    }

    @RequestMapping(value = "delete/{id}", method = DELETE)
    @PreAuthorize("hasAuthority('Delete References')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        modelService.delete(id);
        return ResponseEntity.ok("SUCCESSFULLY Deleted...");
    }
}
