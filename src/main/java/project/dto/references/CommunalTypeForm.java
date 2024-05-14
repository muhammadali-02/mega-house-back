package project.dto.references;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import project.model.references.CommunalType.Code;

@Slf4j
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunalTypeForm {
    private Long id;
    private String nameUz;
    private String nameRu;
    private String type;
    private Code code;
}
