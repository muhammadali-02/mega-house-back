package project.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "permissions")
public class Permission extends Auditable<String> {
    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles = new HashSet<>();

    @Override
    public String toString() {
        return "Permission{" +
                "id= " + super.getId() + ", " +
                "name= " + name + ", " +
                "type= " + type + "}";
    }
}
