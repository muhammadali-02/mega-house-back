package project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import project.dto.AuthenticationRequestDto;
import project.exception.RecordNotFoundException;
import project.model.User;
import project.security.jwt.JwtTokenProvider;
import project.service.RoleService;
import project.service.UserService;
import project.utility.UtilComponents;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST controller for authentication requests (login, logout, register, etc.)
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v.1")
public class AuthenticationController {
    private final RoleService roleService;
    private final UserService userService;
    private final UtilComponents utilComponents;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/auth-payload")
    public ResponseEntity<Map<String, Object>> login(@RequestBody AuthenticationRequestDto requestDto, HttpServletRequest request) {
        User user;
        try {
            String username = requestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            user = userService.findByUsername(username);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (user == null) throw new RecordNotFoundException("User does not exist!");
        List<String> roleNames = userService.findRoleNames(user.getId());
        String token = jwtTokenProvider.createToken(user.getUsername(), roleNames, request);
        List<String> permissionsDto = roleService.findPermissionNames(userService.findRoleIds(user.getId()));
        Map<String, Object> response = new HashMap<>();
        response.put("lastName", user.getLastName());
        response.put("firstName", user.getFirstName());
        response.put("parentName", user.getParentName());
        response.put("photoUploadPath", user.getUploadPath());
        response.put("id", user.getId());
        response.put("token", token);
        response.put("username", user.getUsername());
        response.put("role", roleNames);
        response.put("permissions", permissionsDto);
        return ResponseEntity.ok(response);
    }
}
