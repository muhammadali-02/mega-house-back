package project.model.references;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import project.model.Auditable;
import project.model.Status;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "region")
public class Region extends Auditable<String> {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    @JsonIgnore
    private Region parent;

    @Column(name = "name_ru", nullable = false, length = 250, unique = true)
    private String nameRu;

    @Column(name = "name_uz", nullable = false, length = 250, unique = true)
    private String nameUz;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "name_lt")
    private String nameLt;

    @Column(name = "hierarchy", length = 250)
    private String hierarchy;

    @Column(name = "order_region")
    private Integer order;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status = Status.ACTIVE;

    @Column(name = "soato")
    private Long soato;
}
