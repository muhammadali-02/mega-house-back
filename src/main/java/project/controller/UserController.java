package project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import project.config.SpringSecurityAuditorAware;
import project.dto.UserDto;
import project.exception.RecordNotFoundException;
import project.model.Pagination;
import project.model.Status;
import project.model.User;
import project.repository.UserRepository;
import project.security.jwt.JwtUser;
import project.service.RoleService;
import project.service.UserService;
import project.utility.UtilComponents;

import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;
import static project.model.Status.DELETED;

/**
 * REST controller user connected requestst.
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v.1/user")
public class UserController {
    private final UserService userService;
    private final RoleService roleService;
    private final UtilComponents utilComponents;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @RequestMapping(value = "/list", method = POST)
    @PreAuthorize("hasAuthority('List Users')")
    public ResponseEntity<Map<String, Object>> listUsers(@RequestParam(required = false) String search, @RequestBody Pagination pagination) {
        return new ResponseEntity<>(userService.findAll(search, pagination), OK);
    }

    @RequestMapping(value = "/get-by-id/{id}", method = GET)
    @PreAuthorize("hasAuthority('Get User')")
    public ResponseEntity<UserDto> userById(@PathVariable(name = "id") Long id) {
        User user = userService.findById(id);
        if (user == null) return new ResponseEntity<>(NO_CONTENT);
        return new ResponseEntity<>(new UserDto(user), OK);
    }

    @RequestMapping(value = "/get-by-code/{chatId}", method = GET)
    public ResponseEntity<UserDto> userByCode(@PathVariable(name = "chatId") String chatId) {
        User user = userService.findByChatId(chatId);
        if (user == null) return new ResponseEntity<>(NO_CONTENT);
        return new ResponseEntity<>(new UserDto(user), OK);
    }

    @RequestMapping(value = "/register", method = POST)
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto) {
        if (userDto == null || userDto.getPassword() == null || userDto.getPassword().isEmpty())
            throw new RecordNotFoundException("Invalid password...");
        User user = userDto.convertToUser();
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setStatus(Status.ACTIVE);
        user.getRoles().add(roleService.findByCode("ROLE_USER"));
        userService.saveUser(user);
        return new ResponseEntity<>(new UserDto(user), OK);
    }

    @RequestMapping(value = "/currentUser", method = GET)
//    @PreAuthorize("hasAuthority('Get User')")
    public ResponseEntity<UserDto> currentUser() {
        return new ResponseEntity<>(new UserDto(utilComponents.currentUser(userService)), OK);
    }

    @RequestMapping(value = "/create", method = POST)
    @PreAuthorize("hasAuthority('Create User')")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        if (userDto == null || userDto.getPassword() == null || userDto.getPassword().isEmpty())
            throw new RecordNotFoundException("Invalid password...");
        User user = userDto.convertToUser();
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        if (userDto.getRoleIds() != null && !userDto.getRoleIds().isEmpty())
            user.getRoles().addAll(roleService.findAllByIds(userDto.getRoleIds()));
        userService.saveUser(user);
        return ResponseEntity.ok(new UserDto(user));
    }

    @RequestMapping(value = "/update", method = POST)
    @PreAuthorize("hasAuthority('Update User')")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {
        if (userDto.getId() == null) return new ResponseEntity<>(BAD_REQUEST);
        User user = userService.findById(userDto.getId());
        if (user == null) return new ResponseEntity<>(NOT_FOUND);
        userDto.convertToUser(user);
        if (userDto.getRoleIds() != null && !userDto.getRoleIds().isEmpty())
            user.getRoles().addAll(roleService.findAllByIds(userDto.getRoleIds()));
        userService.updateUser(user);
        return ResponseEntity.ok(new UserDto(user));
    }

    @RequestMapping(value = "/update-password", method = POST)
    @PreAuthorize("hasAuthority('Update User')")
    public ResponseEntity<String> updateUserPass(@RequestBody Map<String, String> map) {
        String currPass = map.get("currentPassword");
        String newPass = map.get("newPassword");
        Optional<JwtUser> jwtUser = new SpringSecurityAuditorAware().getCurrentAuditor();
        if (jwtUser.isEmpty()) return new ResponseEntity<>(NOT_FOUND);
        User user = userService.findById(jwtUser.get().getId());
        if (passwordEncoder.matches(currPass, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPass));
            userService.updateUser(user);
        } else return new ResponseEntity<>("Invalid Current Password...", CONFLICT);
        return new ResponseEntity<>("Success", OK);
    }

    @RequestMapping(value = "/delete/{id}", method = DELETE)
    @PreAuthorize("hasAuthority('Delete User')")
    public ResponseEntity<String> deleteUser(@PathVariable(name = "id") Long id) {
        User user = userService.findById(id);
//        user.setStatus(DELETED);
//        userService.updateUser(user);
        userRepository.delete(user);
        return ResponseEntity.ok("SUCCESSFUL Deleted...");
    }
}
