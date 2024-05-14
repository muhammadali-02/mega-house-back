package project.repository.report;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import project.dto.report.BillsDto;
import project.model.report.Bills;

import java.util.List;

@Repository
public interface BillsRepository extends PagingAndSortingRepository<Bills, Long>, CrudRepository<Bills, Long> {
    @Query("SELECT m FROM Bills m ORDER BY m.id DESC")
    List<Bills> findAllM();

    @Query("SELECT new project.dto.report.BillsDto(m) FROM Bills m ORDER BY m.id DESC")
    List<BillsDto> findAllModels();

    @Query("SELECT new project.dto.report.BillsDto(m) FROM Bills m ORDER BY m.id DESC")
    Page<BillsDto> findAllModel(Pageable paging);

    @Query("SELECT new project.dto.report.BillsDto(m) FROM Bills m WHERE (LOWER(m.meter) LIKE %?1%) ORDER BY m.id DESC")
    Page<BillsDto> findAllModelWithSearch(String search, Pageable paging);

    @Query("SELECT new project.dto.report.BillsDto(m) FROM Bills m WHERE m.home.id = ?1 ORDER BY m.id DESC")
    List<BillsDto> findByHomeId(Long homeId);
}
