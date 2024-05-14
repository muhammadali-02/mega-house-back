package project.service.management;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import project.dto.management.PersonDto;
import project.dto.management.PersonForm;
import project.exception.RecordNotFoundException;
import project.model.Pagination;
import project.model.management.Person;
import project.repository.management.HomeRepository;
import project.repository.management.PersonRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonService {
    private final HomeRepository homeRepository;
    private final PersonRepository modelRepository;

    public List<PersonDto> listAllDto() {
        return modelRepository.findAllModels();
    }

    public List<PersonDto> listByHomeId(Long homeId) {
        if (homeId == null || homeId.equals(0L)) throw new RecordNotFoundException("Invalid homeId...");
//        List<PersonDto> personDtos = modelRepository.findByHomeId(homeId);
//        List<Person> personList = new ArrayList<>();
//        for(PersonDto p : personDtos){
//            HomeDto homeDto = homeRepository.findById(p.getHomeId());
//            personDtos.add()
//        }
        return modelRepository.findByHomeId(homeId);
    }

    public Map<String, Object> list(String search, Pagination pagination) {
        Page<PersonDto> page;
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

    public void save(Person model) {
        modelRepository.save(model);
    }

    public Person findById(Long id) {
        return modelRepository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        modelRepository.deleteById(id);
    }

    public String create(PersonForm form) {
        save(convertToModel(form, new Person()));
        return "Successfully created";
    }

    public String update(PersonForm form) {
        if (form.getId() == null || form.getId().equals(0L)) throw new RecordNotFoundException("Invalid id...");
        if (form.getHomeId() == null || form.getHomeId().equals(0L)) throw new RecordNotFoundException("Invalid id...");
        save(convertToModel(form, findById(form.getId())));
        return "Successfully updated";
    }

    public Person convertToModel(PersonForm form, Person model) {
        model.setF_i_o(form.getF_i_o());
        model.setPassport(form.getPassport());
        model.setPin_fl(form.getPin_fl());
        model.setPhone(form.getPhone());
        model.setWork(form.getWork());
        return model;
    }
}
