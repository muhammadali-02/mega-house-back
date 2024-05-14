package project.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.model.Role;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleDto {

    private Long id;
    private String name;
    private String code;

    public RoleDto(Role role) {
        if (role.getId() != null)
            setId(role.getId());
        setName(role.getName());
        setCode(role.getCode());
    }

    public Role convertToRole() {
        Role role = new Role();
        return convertToRole(role);
    }

    public Role convertToRole(Role role) {
        if (id != null)
            role.setId(id);
        role.setName(name);
        role.setCode(code);
        return role;
    }
}
