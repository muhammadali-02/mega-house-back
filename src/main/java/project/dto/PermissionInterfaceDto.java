package project.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
public interface PermissionInterfaceDto {
    Long getId();

    String getName();

    String getType();
}
