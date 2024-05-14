package project.controller.management;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.dto.management.HomeDto;
import project.dto.management.HomeFileDto;
import project.dto.management.HomeForm;
import project.model.Pagination;
import project.service.management.HomeService;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v.1/home/")
public class HomeController {
    private final HomeService modelService;

    @RequestMapping(value = "listAllDto", method = GET)
    @PreAuthorize("hasAuthority('List Home')")
    public ResponseEntity<List<HomeDto>> listAllDto() {
        return new ResponseEntity<>(modelService.listAllDto(), OK);
    }

    @RequestMapping(value = "list", method = POST)
    @PreAuthorize("hasAuthority('List Home')")
    public ResponseEntity<Map<String, Object>> list(@RequestParam(required = false) String search, @RequestBody Pagination pagination) {
        return new ResponseEntity<>(modelService.list(search, pagination), OK);
    }

    @RequestMapping(value = "get-by-id/{id}", method = GET)
    @PreAuthorize("hasAuthority('Get Home By Id')")
    public ResponseEntity<HomeDto> findById(@PathVariable Long id) {
        return new ResponseEntity<>(new HomeDto(modelService.findById(id)), OK);
    }

    @RequestMapping(value = "create", method = POST)
    @PreAuthorize("hasAuthority('Create Home')")
    public ResponseEntity<String> create(@RequestBody HomeForm form) {
        return ResponseEntity.ok(modelService.create(form));
    }

    @RequestMapping(value = "createFile/{homeId}", method = POST)
    @PreAuthorize("hasAuthority('Create Home')")
    public ResponseEntity<String> createFile(@RequestParam MultipartFile[] files, @PathVariable Long homeId) {
        return ResponseEntity.ok(modelService.createFile(homeId, files));
    }

    @RequestMapping(value = "fileByHome/{homeId}", method = GET)
    @PreAuthorize("hasAuthority('List Home')")
    public ResponseEntity<List<HomeFileDto>> fileByHome(@PathVariable Long homeId) {
        return new ResponseEntity<>(modelService.fileByHome(homeId), OK);
    }

    @RequestMapping(value = "update", method = PUT)
    @PreAuthorize("hasAuthority('Update Home')")
    public ResponseEntity<String> update(@RequestBody HomeForm form) {
        return ResponseEntity.ok(modelService.update(form));
    }

    @RequestMapping(value = "delete/{id}", method = DELETE)
    @PreAuthorize("hasAuthority('Delete Home')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        modelService.delete(id);
        return ResponseEntity.ok("SUCCESSFULLY Deleted...");
    }
}
