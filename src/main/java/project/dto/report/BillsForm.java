package project.dto.report;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillsForm {
    private Long id;
    private String meter;
    private String date;
    private String code;
    private Long homeId;
}
