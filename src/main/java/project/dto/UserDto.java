package project.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.model.Status;
import project.model.User;

import java.util.List;

/**
 * DTO class for user requests by ROLE_USER
 */

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String parentName;
    private List<Long> roleIds;
    private String roleName;
    private String chatId;
    private String phone;
    private Status status;

    public UserDto(User user) {
        if (user.getId() != null) setId(user.getId());
        setUsername(user.getUsername());
        setFirstName(user.getFirstName());
        setLastName(user.getLastName());
        setParentName(user.getParentName());
        setPhone(user.getPhone());
        setChatId(user.getChatId());
        setStatus(user.getStatus());
    }

    public User convertToUser() {
        User user = new User();
        return convertToUser(user);
    }

    public User convertToUser(User user) {
        if (id != null) user.setId(id);
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setParentName(parentName);
        user.setPhone(phone);
        user.setChatId(chatId);
        user.setStatus(status);
        return user;
    }
}
