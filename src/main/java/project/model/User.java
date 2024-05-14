package project.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.FetchType.LAZY;

/**
 * Simple domain object that represents application user.
 */
@Data
@Entity
@Table(name = "users")
@EqualsAndHashCode(callSuper = true)
public class User extends Auditable<String> {
    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name", length = 100)
    private String firstName;

    @Column(name = "last_name", length = 100)
    private String lastName;

    @Column(name = "parent_name", length = 100)
    private String parentName;

    @Column(name = "chat_id")
    private String chatId;

    @Column(name = "phone")
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @ManyToMany(fetch = LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Set<Role> roles = new HashSet<>();

    @Column(name = "file_name")
    private String filename;

    @Column(name = "file_size")
    private Long size;

    @Column(name = "file_extension")
    private String extension;

    @Column(name = "modified_name")
    private String modifiedName;

    @Column(name = "upload_path")
    private String uploadPath;

    @Column(name = "absolute_path")
    private String absolutePath;

    public void setUsername(String username) {
        this.username = username.trim();
    }
}
