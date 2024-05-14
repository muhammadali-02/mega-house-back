package project.model.report;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import project.model.Auditable;
import project.model.management.Home;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bills")
public class Bills extends Auditable<String> {
    @Column(name = "meter")
    private String meter;

    @Column(name = "date")
    private String date;

    @Column(name = "code")
    private String code;

    @ManyToOne(fetch = LAZY)
    @JsonIgnore
    @JoinColumn(name = "home_id", referencedColumnName = "id")
    private Home home;
}
