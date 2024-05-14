package project.controller.management;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.dto.management.PersonDto;
import project.dto.management.PersonForm;
import project.model.Pagination;
import project.service.management.PersonService;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v.1/person/")
public class PersonController {
    private final PersonService modelService;

    @RequestMapping(value = "listAllDto", method = GET)
    @PreAuthorize("hasAuthority('List Person')")
    public ResponseEntity<List<PersonDto>> listAllDto() {
        return new ResponseEntity<>(modelService.listAllDto(), OK);
    }

    @RequestMapping(value = "list-by-home/{homeId}", method = GET)
    @PreAuthorize("hasAuthority('List Person')")
    public ResponseEntity<List<PersonDto>> listAllDto(@PathVariable Long homeId) {
        return new ResponseEntity<>(modelService.listByHomeId(homeId), OK);
    }

    @RequestMapping(value = "list", method = POST)
    @PreAuthorize("hasAuthority('List Person')")
    public ResponseEntity<Map<String, Object>> list(@RequestParam(required = false) String search, @RequestBody Pagination pagination) {
        return new ResponseEntity<>(modelService.list(search, pagination), OK);
    }

    @RequestMapping(value = "get-by-id/{id}", method = GET)
    @PreAuthorize("hasAuthority('Get Person By Id')")
    public ResponseEntity<PersonDto> findById(@PathVariable Long id) {
        return new ResponseEntity<>(new PersonDto(modelService.findById(id)), OK);
    }

    @RequestMapping(value = "create", method = POST)
    @PreAuthorize("hasAuthority('Create Person')")
    public ResponseEntity<String> create(@RequestBody PersonForm form) {
        return ResponseEntity.ok(modelService.create(form));
    }

    @RequestMapping(value = "update", method = PUT)
    @PreAuthorize("hasAuthority('Update Person')")
    public ResponseEntity<String> update(@RequestBody PersonForm form) {
        return ResponseEntity.ok(modelService.update(form));
    }

    @RequestMapping(value = "delete/{id}", method = DELETE)
    @PreAuthorize("hasAuthority('Delete Person')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        modelService.delete(id);
        return ResponseEntity.ok("SUCCESSFULLY Deleted...");
    }
}
