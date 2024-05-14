package project.model.management;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import project.model.Auditable;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "persons")
public class Person extends Auditable<String> {
    @Column(name = "f_i_o")
    private String f_i_o;

    @Column(name = "passport")
    private String passport;

    @Column(name = "pin_fl")
    private String pin_fl;

    @Column(name = "phone")
    private String phone;

    @Column(name = "work")
    private String work;

    @ManyToOne(fetch = LAZY)
    @JsonIgnore
    @JoinColumn(name = "home_id", referencedColumnName = "id")
    private Home home;
}
