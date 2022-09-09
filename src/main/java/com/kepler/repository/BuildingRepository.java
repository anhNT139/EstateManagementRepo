package com.kepler.repository;

import com.kepler.entity.BuildingEntity;
import com.kepler.entity.FileEntity;
import com.kepler.repository.custom.BuildingRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BuildingRepository extends JpaRepository<BuildingEntity, Long>, BuildingRepositoryCustom {
    Long deleteByIdIn(List<Long> ids);

    List<BuildingEntity> findByIdIn(List<Long> ids);

    boolean existsByIdAndStaffs_Id(Long buildingId, Long staffId);

    @Query("SELECT s.id FROM BuildingEntity b JOIN b.staffs s WHERE b.id = :buildingId")
    List<Long> getStaffsId(@Param("buildingId") Long buildingId);

    @Query("SELECT b.image FROM BuildingEntity b WHERE b.id in (:ids)")
    List<FileEntity> getImages(@Param("ids") List<Long> buildingsId);

}
