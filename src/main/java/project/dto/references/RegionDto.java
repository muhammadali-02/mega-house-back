package project.dto.references;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.model.references.Region;
import project.service.references.RegionService;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class RegionDto {
    private Long id;
    private Long parentId;
    private String parentNameRu;
    private String parentNameUz;
    private String parentNameEn;
    @NotEmpty(message = "nameRu не может быть пустым!")
    private String nameRu;
    @NotEmpty(message = "nameUz не может быть пустым!")
    private String nameUz;
    @NotEmpty(message = "nameEn не может быть пустым!")
    private String nameEn;
    private Integer order;
    private Long soato;


    private List<RegionDto> children;

    public RegionDto(Region region) {
        if (region.getId() != null) setId(region.getId());
        if (region.getParent() != null) {
            setParentId(region.getParent().getId());
            setParentNameRu(region.getParent().getNameRu());
            setParentNameUz(region.getParent().getNameUz());
            setParentNameEn(region.getParent().getNameEn());
            setOrder(region.getOrder());
            setSoato(region.getSoato());
        }
        setNameRu(region.getNameRu());
        setNameUz(region.getNameUz());
        setNameEn(region.getNameEn());
        setOrder(region.getOrder());
        setSoato(region.getSoato());
    }

    public RegionDto(Region region, RegionService regionService, boolean loadChildren) {
        this(region);
        if (loadChildren && id != null) {
            try {
                children = new ArrayList<>();
                for (Region child : regionService.findAllByParentId(id)) {
                    children.add(new RegionDto(child, regionService, true));
                }
            } catch (Throwable throwable) {
                children = null;
            }
        } else {
            children = null;
        }
    }

    public Region convertToDirectoryRegion() {
        Region region = new Region();
        return convertToDirectoryRegion(region);
    }

    public Region convertToDirectoryRegion(Region region) {
        if (id != null) region.setId(id);
        region.setNameRu(nameRu);
        region.setNameUz(nameUz);
        region.setNameEn(nameEn);
        region.setSoato(soato);
        region.setOrder(order);
        return region;
    }
}
