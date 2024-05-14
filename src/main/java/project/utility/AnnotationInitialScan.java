package project.utility;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import project.anatation.View;
import project.model.Permission;
import project.model.Role;
import project.model.User;
import project.service.PermissionService;
import project.service.RoleService;
import project.service.UserService;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.Set;

import static project.model.Status.ACTIVE;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class AnnotationInitialScan {
    @Value("${initial.username}")
    String username;
    @Value("${initial.password}")
    String password;
    @Value("${initial.roleName}")
    String roleName;
    @Value("${initial.roleCode}")
    String roleCode;
    private final RoleService roleService;
    private final UserService userService;
    private final PermissionService permissionService;
    private final BCryptPasswordEncoder passwordEncoder;

    @PostConstruct
    public void postConstruct() {
        this.startView();
        this.startPerm();
        this.startRole();
        this.startUser();
    }

    public void startView() {
        log.info("---------------------------------------");
        log.info("-------Scan for View annotations-------");
        log.info("---------------------------------------");
        Reflections reflections = new Reflections("project.controller", new MethodAnnotationsScanner());
        Set<Method> methods = reflections.getMethodsAnnotatedWith(View.class);
        methods.forEach(method -> {
            View perm = method.getAnnotation(View.class);
            for (String code : perm.value()) {
                try {
                    String methodType = method.getDeclaringClass().getSimpleName();
                    String type = null;
                    if (methodType.contains("Controller"))
                        type = methodType.replace("Controller", "");
                    if (type != null) {
                        Permission permissionOpt = permissionService.getPermissionByName(code);
                        if (permissionOpt == null) {
                            Permission permission = new Permission();
                            permission.setName(code);
                            permission.setType(type);
                            permissionService.savePermission(permission);
                            log.info("Found a permission '" + code + "'");
                        }
                    }
                } catch (Throwable e) {
                    log.error(e.getMessage(), e);
                }
            }
        });
    }

    public void startPerm() {
        log.info("---------------------------------------");
        log.info("----Scan for Permission annotations----");
        log.info("---------------------------------------");
        Reflections reflections = new Reflections("project.controller", new MethodAnnotationsScanner());
        Set<Method> methods = reflections.getMethodsAnnotatedWith(PreAuthorize.class);
        methods.forEach(method -> {
            PreAuthorize perm = method.getAnnotation(PreAuthorize.class);
            String name = perm.value();
            if (name.contains("hasAuthority")) {
                String code = name.replace("hasAuthority('", "").replace("')", "");
                try {
                    String methodType = method.getDeclaringClass().getSimpleName();
                    String type = null;
                    if (methodType.contains("Controller"))
                        type = methodType.replace("Controller", "");
                    if (type != null) {
                        Permission permissionOpt = permissionService.getPermissionByName(code);
                        if (permissionOpt == null) {
                            Permission permission = new Permission();
                            if (code.equals("View Reference")) {
                                permission.setName(code);
                                permission.setType("References");
                            } else {
                                permission.setName(code);
                                permission.setType(type);
                            }
                            permissionService.savePermission(permission);
                            log.info("Found a permission '" + code + "'");
                        }
                    }
                } catch (Throwable e) {
                    log.error(e.getMessage(), e);
                }
            }
        });
    }

    public void startRole() {
        Role role = roleService.findByCode(roleCode);
        if (role == null) {
            role = new Role();
            role.setName(roleName);
            role.setCode(roleCode);
            roleService.createRole(role);
        }
        role.getPermissions().addAll(permissionService.findNotInRole(role.getId()));
        roleService.updateRole(role);
    }

    public void startUser() {
        User user = userService.findByUsername(username);
        if (user == null) {
            user = new User();
            user.setUsername(username);
            user.setLastName("Murodov");
            user.setFirstName("Muhammad");
            user.setParentName("Anvar o'g'li");
            user.setStatus(ACTIVE);
            user.setPassword(passwordEncoder.encode(password));
            user.getRoles().add(roleService.findByCode(roleCode));
            userService.saveUser(user);
        }
    }
}
