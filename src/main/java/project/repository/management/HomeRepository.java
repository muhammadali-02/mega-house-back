package project.repository.management;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import project.dto.management.HomeDto;
import project.model.management.Home;

import java.util.List;
import java.util.Optional;

@Repository
public interface HomeRepository extends PagingAndSortingRepository<Home, Long>, CrudRepository<Home, Long> {
    @Query("SELECT m FROM Home m ORDER BY m.id DESC")
    List<Home> findAllM();

    @Query("SELECT new project.dto.management.HomeDto(m) FROM Home m ORDER BY m.id DESC")
    List<HomeDto> findAllModels();

    @Query("SELECT new project.dto.management.HomeDto(m) FROM Home m ORDER BY m.id DESC")
    Page<HomeDto> findAllModel(Pageable paging);

    @Query("SELECT new project.dto.management.HomeDto(m) FROM Home m WHERE (LOWER(m.name) LIKE %?1%) ORDER BY m.id DESC")
    Page<HomeDto> findAllModelWithSearch(String search, Pageable paging);

    Optional<Home> findByName(String name);
}
