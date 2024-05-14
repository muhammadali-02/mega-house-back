package project.repository.references;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import project.model.Status;
import project.model.references.Region;

import java.util.List;

@Repository
public interface RegionRepository extends PagingAndSortingRepository<Region, Long>, CrudRepository<Region, Long> {
    @Query("select r from Region r where r.parent.id is null and r.status not in(?1)")
    List<Region> findAllByParentIsNull(Status status);

    List<Region> findAllByParentIdAndStatusNot(Long parentId, Status status);

    @Query("SELECT r FROM Region r WHERE r.soato = ?1 AND r.status not in(?2)")
    Region getRegionBySoato(Long regionSoato, Status status);

    @Query("select r from Region r where r.parent.id = ?1 order by r.id desc ")
    List<Region> findAllUzbRegions(Long uzb);
}
