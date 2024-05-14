package project.dto.management;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import project.model.management.Person;

@Data
@Slf4j
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto {
    private Long id;
    private String f_i_o;
    private String passport;
    private String pin_fl;
    private String phone;
    private String work;
    private Long homeId;

    public PersonDto(Person person) {
        if (person.getId() != null) setId(person.getId());
        if (person.getHome() != null) setHomeId(person.getHome().getId());
        setF_i_o(person.getF_i_o());
        setPassport(person.getPassport());
        setPin_fl(person.getPin_fl());
        setPhone(person.getPhone());
        setWork(person.getWork());
    }
}
