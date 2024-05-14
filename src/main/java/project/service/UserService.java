package project.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import project.dto.UserDto;
import project.exception.RecordNotFoundException;
import project.model.Pagination;
import project.model.Status;
import project.model.User;
import project.repository.UserRepository;

import java.util.*;

import static project.model.Status.ACTIVE;
import static project.model.Status.DELETED;

/**
 * Implementation of {@link UserService} interface.
 * Wrapper for {@link UserRepository} + business logic.
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Map<String, Object> findAll(String search, Pagination pagination) {
        Pageable paging;
        if (pagination.getOrder() == null || pagination.getOrder().equals(""))
            paging = PageRequest.of(pagination.getPage(), pagination.getLimit(), Sort.by("id").descending());
        else {
            if (pagination.getType().equalsIgnoreCase("ASC"))
                paging = PageRequest.of(pagination.getPage(), pagination.getLimit(), Sort.by(pagination.getOrder()).ascending());
            else
                paging = PageRequest.of(pagination.getPage(), pagination.getLimit(), Sort.by(pagination.getOrder()).descending());
        }
        Page<UserDto> page;
        if (search == null || search.isEmpty()) page = userRepository.findAllM(paging);
        else page = userRepository.findAllSearch(search.toLowerCase(), paging);
        Map<String, Object> map = new HashMap<>();
        if ((page != null && page.hasContent())) {
            List<UserDto> list = new ArrayList<>();
            page.getContent().forEach(user -> {
                user.setRoleName(findRoleNames(user.getId()).toString());
                list.add(user);
            });
            map.put("list", list);
            map.put("total", page.getTotalElements());
        } else {
            map.put("list", new ArrayList<>());
            map.put("total", 0);
        }
        return map;
    }

    public User findByUsername(String username) {
        User result = userRepository.findByUsername(username);
        log.info("IN findByUsername - user: {} found by username: {}", result, username);
        return result;
    }

    public User findById(Long id) {
        User result = userRepository.findById(id).orElse(null);

        if (result == null) {
            log.warn("IN findById - no user found by id: {}", id);
            return null;
        }

        log.info("IN findById - user: {} found by id: {}", result, id);
        return result;
    }

    public User saveUser(User user) {
        User result = findByUsername(user.getUsername());
        if (result == null) {
            userRepository.save(user);
            return user;
        } else throw new RecordNotFoundException("Such username: " + user.getUsername() + " exist...");
    }

    public void delete(Long id) {
        User user = findById(id);
        user.setStatus(DELETED);
        saveUser(user);
        log.info("IN delete - user with id: {} successfully deleted", id);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public List<String> findRoleNames(Long userId) {
        return userRepository.findRoleNames(userId);
    }

    public List<Long> findRoleIds(Long userId) {
        return userRepository.findRoleIds(userId);
    }

    public User findByChatId(String chatId) {
        User result = userRepository.findByChatId(chatId);

        if (result == null) {
            log.warn("IN findByChatId - no user found by id: {}", chatId);
            return null;
        }

        log.info("IN findByChatId - user: {} found by id: {}", result, chatId);
        return result;
    }
}
