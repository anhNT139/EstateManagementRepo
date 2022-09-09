package com.kepler.repository;

import com.kepler.entity.BuildingEntity;
import com.kepler.entity.RentAreaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface RentAreaRepository extends JpaRepository<RentAreaEntity, Long> {
    RentAreaRepository findByBuildingAndValue(BuildingEntity building, Integer value);
    void deleteByBuilding_Id(Long id);

    @Modifying
    @Query("DELETE FROM RentAreaEntity rae WHERE rae.building.id in (:ids)")
    void deleteByBuilding_IdIn(@Param("ids") List<Long> buildingsId);
}
