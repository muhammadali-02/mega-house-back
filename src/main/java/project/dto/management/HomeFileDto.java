package project.dto.management;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import project.model.management.HomeFile;

@Data
@Slf4j
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HomeFileDto {
    private Long id;
    private Long homeId;
    private String filename;
    private Long size;
    private String extension;
    private String modifiedName;
    private String uploadPath;
    private String absolutePath;

    public HomeFileDto(HomeFile file) {
        if (file.getId() != null) setId(file.getId());
        if (file.getHome() != null) setHomeId(file.getHome().getId());
        setFilename(file.getFilename());
        setSize(file.getSize());
        setExtension(file.getExtension());
        setModifiedName(file.getModifiedName());
        setUploadPath(file.getUploadPath());
        setAbsolutePath(file.getAbsolutePath());
    }
}
