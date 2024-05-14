package project.service.management;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import project.dto.management.HomeDto;
import project.dto.management.HomeFileDto;
import project.dto.management.HomeForm;
import project.exception.RecordNotFoundException;
import project.model.Pagination;
import project.model.management.Home;
import project.model.management.HomeFile;
import project.repository.management.HomeFileRepository;
import project.repository.management.HomeRepository;
import project.service.UploadPathService;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class HomeService {
    private final HomeRepository modelRepository;
    private final HomeFileRepository fileRepository;
    private final UploadPathService uploadPathService;

    public List<HomeDto> listAllDto() {
        return modelRepository.findAllModels();
    }

    public Map<String, Object> list(String search, Pagination pagination) {
        Page<HomeDto> page;
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

    public void save(Home model) {
        modelRepository.save(model);
    }

    public Home findById(Long id) {
        return modelRepository.findById(id).orElse(null);
    }

    public Home findByName(String name) {
        return modelRepository.findByName(name).orElse(null);
    }

    public void delete(Long id) {
        modelRepository.deleteById(id);
    }

    public String create(HomeForm form) {
        save(convertToModel(form, new Home()));
        return "Successfully created";
    }

    public String update(HomeForm form) {
        if (form.getId() == null || form.getId().equals(0L)) throw new RecordNotFoundException("Invalid id...");
        save(convertToModel(form, findById(form.getId())));
        return "Successfully updated";
    }

    public Home convertToModel(HomeForm form, Home model) {
        model.setName(form.getName());
        model.setPrice(form.getPrice());
        model.setDate(form.getDate());
        model.setAddress(form.getAddress());
        model.setLatitude(form.getLatitude());
        model.setLongitude(form.getLongitude());
        model.setHot(form.getHot());
        model.setCold(form.getCold());
        model.setGaz(form.getGaz());
        model.setElectronic(form.getElectronic());
        model.setGarbage(form.getGarbage());
        model.setCommunal(form.getCommunal());
        model.setTax(form.getTax());
        model.setOwn(form.getOwn());
        return model;
    }

    public String createFile(Long homeId, MultipartFile[] files) {
        if (homeId == null || homeId.equals(0L)) throw new RecordNotFoundException("Invalid HomaID...");
        Home home = findById(homeId);
        if (home == null) throw new RecordNotFoundException("Invalid Homa...");
        for (MultipartFile file : files) {
            if (file != null && StringUtils.hasText(file.getOriginalFilename())) {
                String fileName = file.getOriginalFilename().replaceAll(" ", "");
                String fileExtension = FilenameUtils.getExtension(fileName);
                fileName = FilenameUtils.getBaseName(fileName);
                if (fileName.length() > 20) fileName = fileName.substring(0, 20);
                RandomStringGenerator pwdGenerator = new RandomStringGenerator.Builder().withinRange(64, 90).withinRange(97, 122).build();
                String currentMill = LocalDate.now().getYear() + "." + LocalDate.now().getMonthValue() + "." + LocalDate.now().getDayOfMonth();
                String modifiedFileName = fileName + "_" + pwdGenerator.generate(15) + System.currentTimeMillis() + "." + fileExtension;
                String url = currentMill + "/homeFile";
                File storeFile = uploadPathService.getFilePath(modifiedFileName, url);
                if (storeFile != null) {
                    try {
                        FileUtils.writeByteArrayToFile(storeFile, file.getBytes());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                HomeFile homeFile = new HomeFile();
                homeFile.setHome(home);
                homeFile.setFilename(fileName + "." + fileExtension);
                homeFile.setSize(file.getSize());
                homeFile.setExtension(fileExtension);
                homeFile.setModifiedName(modifiedFileName);
                homeFile.setUploadPath(url + "/" + modifiedFileName);
                assert storeFile != null;
                homeFile.setAbsolutePath(storeFile.getAbsolutePath());
                fileRepository.save(homeFile);
            }
        }
        return "SUCCESS";
    }

    public List<HomeFileDto> fileByHome(Long homeId) {
        return fileRepository.findAllByHome(homeId);
    }
}
