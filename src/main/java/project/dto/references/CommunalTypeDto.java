package project.dto.references;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import project.model.references.CommunalType;
import project.model.references.CommunalType.Code;

@Data
@Slf4j
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunalTypeDto {
    private Long id;
    private String nameUz;
    private String nameRu;
    private String type;
    private Code code;

    public CommunalTypeDto(CommunalType model) {
        if (model.getId() != null) setId(model.getId());
        setNameUz(model.getNameUz());
        setNameRu(model.getNameRu());
        setType(model.getType());
        setCode(model.getCode());
    }
}
