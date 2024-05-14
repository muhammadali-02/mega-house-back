package project.dto.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import project.model.report.Bills;

@Data
@Slf4j
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillsDto {
    private Long id;
    private String meter;
    private String date;
    private String code;
    private Long homeId;

    public BillsDto(Bills bills) {
        if (bills.getId() != null) setId(bills.getId());
        if (bills.getHome() != null) setHomeId(bills.getHome().getId());
        setMeter(bills.getMeter());
        setDate(bills.getDate());
        setCode(bills.getCode());
    }
}
