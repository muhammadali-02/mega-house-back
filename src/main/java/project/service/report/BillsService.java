package project.service.report;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import project.dto.report.BillsDto;
import project.dto.report.BillsForm;
import project.exception.RecordNotFoundException;
import project.model.Pagination;
import project.model.report.Bills;
import project.repository.report.BillsRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class BillsService {
    private final BillsRepository modelRepository;

    public List<BillsDto> listAllDto() {
        return modelRepository.findAllModels();
    }

    public List<BillsDto> listByHomeId(Long homeId) {
        if (homeId == null || homeId.equals(0L)) throw new RecordNotFoundException("Invalid homeId...");
        return modelRepository.findByHomeId(homeId);
    }

    public Map<String, Object> list(String search, Pagination pagination) {
        Page<BillsDto> page;
        if (search != null && !search.equals(""))
            page = modelRepository.findAllModelWithSearch(search.toLowerCase(), PageRequest.of(pagination.getPage(), pagination.getLimit()));
        else page = modelRepository.findAllModel(PageRequest.of(pagination.getPage(), pagination.getLimit()));
        Map<String, Object> map = new HashMap<>();
        if (page != null && page.hasContent()) {
            map.put("list", page.getContent());
            map.put("total", page.getTotalElements());
        } else {
            map.put("list", new ArrayList<>());
            map.put("total", 0);
        }
        return map;
    }

    public void save(Bills model) {
        modelRepository.save(model);
    }

    public Bills findById(Long id) {
        return modelRepository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        modelRepository.deleteById(id);
    }

    public String create(BillsForm form) {
        save(convertToModel(form, new Bills()));
        return "Successfully created";
    }

    public String update(BillsForm form) {
        if (form.getId() == null || form.getId().equals(0L)) throw new RecordNotFoundException("Invalid id...");
        if (form.getHomeId() == null || form.getHomeId().equals(0L)) throw new RecordNotFoundException("Invalid id...");
        save(convertToModel(form, findById(form.getId())));
        return "Successfully updated";
    }

    public Bills convertToModel(BillsForm form, Bills model) {
        model.setMeter(form.getMeter());
        model.setDate(form.getDate());
        model.setCode(form.getCode());
        return model;
    }
}
