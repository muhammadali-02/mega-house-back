package project.repository.management;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import project.dto.management.HomeFileDto;
import project.model.management.HomeFile;

import java.util.List;

@Repository
public interface HomeFileRepository extends PagingAndSortingRepository<HomeFile, Long>, CrudRepository<HomeFile, Long> {
    @Query("SELECT m FROM HomeFile m ORDER BY m.id DESC")
    List<HomeFile> findAllM();

    @Query("SELECT new project.dto.management.HomeFileDto(m) FROM HomeFile m WHERE m.home.id = ?1 ORDER BY m.id DESC")
    List<HomeFileDto> findAllByHome(Long homaId);
}
