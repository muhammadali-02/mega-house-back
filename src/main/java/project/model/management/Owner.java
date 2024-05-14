package project.model.management;

import lombok.*;
import project.model.Auditable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "owners")
public class Owner extends Auditable<String> {
    @Column(name = "f_i_o")
    private String fio;

    @Column(name = "phone")
    private String phone;
}
