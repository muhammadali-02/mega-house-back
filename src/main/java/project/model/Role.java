package project.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.FetchType.LAZY;

/**
 * Simple domain object that represents application user's role - ADMIN, USER, etc.
 */

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role extends Auditable<String> {
    @Column(name = "name")
    private String name;

    @Column(name = "code", unique = true)
    private String code;

    @ManyToMany(fetch = LAZY)
    @JoinTable(name = "role_permissions",
            joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "permission_id", referencedColumnName = "id")})
    private Set<Permission> permissions = new HashSet<>();

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    @Override
    public String toString() {
        return "Role{" +
                "id: " + super.getId() + ", " +
                "name: " + name + ", " +
                "code: " + code + "}";
    }
}
