package project.controller.report;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.dto.report.BillsDto;
import project.dto.report.BillsForm;
import project.model.Pagination;
import project.service.report.BillsService;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v.1/bills/")
public class BillsController {
    private final BillsService modelService;

    @RequestMapping(value = "listAllDto", method = GET)
    @PreAuthorize("hasAuthority('List Bills')")
    public ResponseEntity<List<BillsDto>> listAllDto() {
        return new ResponseEntity<>(modelService.listAllDto(), OK);
    }

    @RequestMapping(value = "list-by-home/{homeId}", method = GET)
    @PreAuthorize("hasAuthority('List Bills')")
    public ResponseEntity<List<BillsDto>> listAllDto(@PathVariable Long homeId) {
        return new ResponseEntity<>(modelService.listByHomeId(homeId), OK);
    }

    @RequestMapping(value = "list", method = POST)
    @PreAuthorize("hasAuthority('List Bills')")
    public ResponseEntity<Map<String, Object>> list(@RequestParam(required = false) String search, @RequestBody Pagination pagination) {
        return new ResponseEntity<>(modelService.list(search, pagination), OK);
    }

    @RequestMapping(value = "get-by-id/{id}", method = GET)
    @PreAuthorize("hasAuthority('Get Bills By Id')")
    public ResponseEntity<BillsDto> findById(@PathVariable Long id) {
        return new ResponseEntity<>(new BillsDto(modelService.findById(id)), OK);
    }

    @RequestMapping(value = "create", method = POST)
    @PreAuthorize("hasAuthority('Create Bills')")
    public ResponseEntity<String> create(@RequestBody BillsForm form) {
        return ResponseEntity.ok(modelService.create(form));
    }

    @RequestMapping(value = "update", method = PUT)
    @PreAuthorize("hasAuthority('Update Bills')")
    public ResponseEntity<String> update(@RequestBody BillsForm form) {
        return ResponseEntity.ok(modelService.update(form));
    }

    @RequestMapping(value = "delete/{id}", method = DELETE)
    @PreAuthorize("hasAuthority('Delete Bills')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        modelService.delete(id);
        return ResponseEntity.ok("SUCCESSFULLY Deleted...");
    }
}
