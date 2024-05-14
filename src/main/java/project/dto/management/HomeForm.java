package project.dto.management;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HomeForm {
    private Long id;
    private String name;
    private String price;
    private String date;
    private String address;
    private String latitude;
    private String longitude;
    private String hot;
    private String cold;
    private String gaz;
    private String electronic;
    private String garbage;
    private String communal;
    private String tax;
    private Boolean own;
}
