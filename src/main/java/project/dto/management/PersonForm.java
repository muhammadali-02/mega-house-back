package project.dto.management;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import project.model.management.Home;

@Slf4j
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonForm {
    private Long id;
    private String f_i_o;
    private String passport;
    private String pin_fl;
    private String phone;
    private String work;
    private Long homeId;
}
