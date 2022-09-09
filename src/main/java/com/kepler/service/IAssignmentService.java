package com.kepler.service;

import com.kepler.dto.staff.AssignmentDTO;
import com.kepler.dto.staff.StaffDTO;

import java.util.List;

public interface IAssignmentService {
    List<StaffDTO> assignBuildingToStaff(AssignmentDTO assignmentBuildingDTO);

    List<StaffDTO> assignCustomerToStaff(AssignmentDTO assignmentBuildingDTO);
}
