package project.dto.management;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import project.model.management.Home;

@Data
@Slf4j
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HomeDto {
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

    public HomeDto(Home home) {
        if (home.getId() != null) setId(home.getId());
        setName(home.getName());
        setPrice(home.getPrice());
        setDate(home.getDate());
        setAddress(home.getAddress());
        setLatitude(home.getLatitude());
        setLongitude(home.getLongitude());
        setHot(home.getHot());
        setCold(home.getCold());
        setGaz(home.getGaz());
        setElectronic(home.getElectronic());
        setGarbage(home.getGarbage());
        setCommunal(home.getCommunal());
        setTax(home.getTax());
        setOwn(home.getOwn());
    }
}
