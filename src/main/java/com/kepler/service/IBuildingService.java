package com.kepler.service;

import com.kepler.dto.building.BuildingDTO;
import com.kepler.dto.PageableDTO;
import com.kepler.dto.building.BuildingSearchRequest;
import com.kepler.dto.building.BuildingSearchResponse;
import com.kepler.dto.staff.StaffDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface IBuildingService {

	PageableDTO<BuildingSearchResponse> searchBuildings(BuildingSearchRequest buildingSearchRequest);

	Long saveBuilding(BuildingDTO buildingDTO, MultipartFile image);

	Long removeBuildings(List<Long> buildingId);

	BuildingDTO getBuildingById(Long id);

	List<BuildingSearchResponse> getAllBuildings();

	Map<String, String> getBuildingTypes();

    List<StaffDTO> getStaffs(Long id);

	Map<String, String> getRentStatus();
}
