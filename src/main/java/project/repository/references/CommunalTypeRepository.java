package project.repository.references;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import project.dto.references.CommunalTypeDto;
import project.model.references.CommunalType;

import java.util.List;

@Repository
public interface CommunalTypeRepository extends PagingAndSortingRepository<CommunalType, Long>, CrudRepository<CommunalType, Long> {
    @Query("SELECT m FROM CommunalType m ORDER BY m.id DESC")
    List<CommunalType> findAllM();

    @Query("SELECT new project.dto.references.CommunalTypeDto(m) FROM CommunalType m ORDER BY m.id DESC")
    List<CommunalTypeDto> findAllModels();

    @Query("SELECT new project.dto.references.CommunalTypeDto(m) FROM CommunalType m ORDER BY m.id DESC")
    Page<CommunalTypeDto> findAllModel(Pageable paging);

    @Query("SELECT new project.dto.references.CommunalTypeDto(m) FROM CommunalType m WHERE (LOWER(m.nameUz) LIKE %?1%) ORDER BY m.id DESC")
    Page<CommunalTypeDto> findAllModelWithSearch(String search, Pageable paging);
}
