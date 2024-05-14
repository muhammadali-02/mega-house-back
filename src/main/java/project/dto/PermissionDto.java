package project.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.model.Permission;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PermissionDto {
    private Long id;
    private String name;
    private String type;

    public PermissionDto(Permission permission) {
        if (permission.getId() != null)
            setId(permission.getId());
        setName(permission.getName());
        setType(permission.getType());
    }

    public Permission convertToPermission() {
        Permission permission = new Permission();
        return convertToPermission(permission);
    }

    public Permission convertToPermission(Permission permission) {
        if (id != null) permission.setId(id);
        permission.setName(name);
        permission.setType(type);
        return permission;
    }
}
