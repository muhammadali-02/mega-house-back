package project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import project.dto.UserDto;
import project.model.Status;
import project.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface that extends {@link JpaRepository} for class {@link User}.
 */

public interface UserRepository extends JpaRepository<User, Long>, PagingAndSortingRepository<User, Long>, QueryByExampleExecutor<User> {
    User findByUsername(String name);

    @Query(value = "SELECT u FROM User u WHERE u.status = 'ACTIVE'")
    List<User> findAllActives();

    @Query("SELECT new project.dto.UserDto(u) FROM User u ORDER BY u.status, u.username")
    Page<UserDto> findAllM(Pageable paging);

    @Query("SELECT new project.dto.UserDto(u) FROM User u WHERE concat(u.username,u.lastName,' ',u.firstName,' ',u.parentName,' ',u.chatId) LIKE %?1% ORDER BY u.status, u.username")
    Page<UserDto> findAllSearch(String search, Pageable paging);

//    @Query(value = "SELECT u FROM User u WHERE u.employee.id = ?1 AND u.status = ?2")
//    User findByEmployeeIdAndStatus(Long id, Status active);

    @Query(value = "SELECT u.username FROM users u WHERE u.username LIKE ?1 ORDER BY u.username DESC LIMIT 1", nativeQuery = true)
    Optional<String> byPrefix(String index);

    @Query("SELECT r.name FROM User u LEFT JOIN u.roles r WHERE u.id = ?1 ORDER BY r.name")
    List<String> findRoleNames(Long userId);

    @Query("SELECT r.id FROM User u LEFT JOIN u.roles r WHERE u.id = ?1 ORDER BY r.name")
    List<Long> findRoleIds(Long userId);

    @Query(value = "SELECT u FROM User u WHERE u.chatId = ?1")
    User findByChatId(String chatId);
}
