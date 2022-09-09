package com.kepler.api.admin;

import com.kepler.dto.staff.AssignmentDTO;
import com.kepler.dto.building.BuildingDTO;
import com.kepler.dto.staff.StaffDTO;
import com.kepler.service.IAssignmentService;
import com.kepler.service.IBuildingService;
import com.kepler.validation.group.OnCreate;
import com.kepler.validation.group.OnUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.groups.Default;
import java.util.List;

@RestController
@RequestMapping("/api/building")
@Validated
@RequiredArgsConstructor
public class BuildingAPI {

    private final IBuildingService buildingService;
    private final IAssignmentService assignmentService;

    @GetMapping("/{id}")
    public BuildingDTO getBuildingDetail(@PathVariable("id") Long buildingId) {
        return this.buildingService.getBuildingById(buildingId);
    }

    @PostMapping
    @Validated(OnCreate.class)
    public Long createBuilding(@RequestPart("building") @Valid BuildingDTO buildingDTO,
                               @RequestParam(value = "image", required = false) final MultipartFile image) {
        return this.buildingService.saveBuilding(buildingDTO, image);
    }

    @PostMapping("/assignment")
    public List<StaffDTO> assignBuilding(@RequestBody @Valid AssignmentDTO assignmentBuildingDTO) {
        return this.assignmentService.assignBuildingToStaff(assignmentBuildingDTO);
    }

    @PutMapping
    public Long updateBuilding(@RequestPart("building") @Validated({OnUpdate.class, Default.class}) BuildingDTO buildingDTO,
                               @RequestPart(value = "image", required = false) final MultipartFile image) {
        return this.buildingService.saveBuilding(buildingDTO, image);
    }

    @DeleteMapping
    public Long deleteBuildings(@RequestBody  List<Long> buildingIds) {
        return this.buildingService.removeBuildings(buildingIds);
    }

}
