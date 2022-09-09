package com.kepler.repository.custom;

import com.kepler.dto.building.BuildingSearchRequest;
import com.kepler.entity.BuildingEntity;
import org.springframework.data.domain.Page;

public interface BuildingRepositoryCustom {
	Page<BuildingEntity> findByCondition(BuildingSearchRequest searchRequest, Long currentLoggedInStaffId);
}
