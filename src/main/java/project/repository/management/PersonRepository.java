package project.repository.management;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import project.dto.management.PersonDto;
import project.model.management.Person;

import java.util.List;

@Repository
public interface PersonRepository extends PagingAndSortingRepository<Person, Long>, CrudRepository<Person, Long> {
    @Query("SELECT m FROM Person m ORDER BY m.id DESC")
    List<Person> findAllM();

    @Query("SELECT new project.dto.management.PersonDto(m) FROM Person m ORDER BY m.id DESC")
    List<PersonDto> findAllModels();

    @Query("SELECT new project.dto.management.PersonDto(m) FROM Person m ORDER BY m.id DESC")
    Page<PersonDto> findAllModel(Pageable paging);

    @Query("SELECT new project.dto.management.PersonDto(m) FROM Person m WHERE (LOWER(m.f_i_o) LIKE %?1%) ORDER BY m.id DESC")
    Page<PersonDto> findAllModelWithSearch(String search, Pageable paging);

    @Query("SELECT new project.dto.management.PersonDto(m) FROM Person m WHERE m.home.id = ?1 ORDER BY m.id DESC")
    List<PersonDto> findByHomeId(Long homeId);
}
