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
@Table(name = "homes")
public class Home extends Auditable<String> {
    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private String price;

    @Column(name = "date")
    private String date;

    @Column(name = "address")
    private String address;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "hot")
    private String hot;

    @Column(name = "cold")
    private String cold;

    @Column(name = "gaz")
    private String gaz;

    @Column(name = "electronic")
    private String electronic;

    @Column(name = "garbage")
    private String garbage;

    @Column(name = "communal")
    private String communal;

    @Column(name = "tax")
    private String tax;

    @Column(name = "own")
    private Boolean own;
}
