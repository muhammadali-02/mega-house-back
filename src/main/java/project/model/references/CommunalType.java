package project.model.references;

import lombok.*;
import project.model.Auditable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import static javax.persistence.EnumType.STRING;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "communal_type")
public class CommunalType extends Auditable<String> {
    public enum Code {HOT, COLD, GAZ, ELECTRONIC, GARBAGE, COMMUNAL, TAX}

    @Column(name = "name_uz")
    private String nameUz;

    @Column(name = "name_ru")
    private String nameRu;

    @Column(name = "type")
    private String type;

    @Column(name = "code", length = 30)
    @Enumerated(STRING)
    private Code code;
}
