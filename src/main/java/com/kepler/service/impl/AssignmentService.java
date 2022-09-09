package com.kepler.service.impl;

import com.kepler.converter.UserConverter;
import com.kepler.dto.staff.AssignmentDTO;
import com.kepler.dto.staff.StaffDTO;
import com.kepler.entity.BuildingEntity;
import com.kepler.entity.CustomerEntity;
import com.kepler.entity.UserEntity;
import com.kepler.repository.BuildingRepository;
import com.kepler.repository.CustomerRepository;
import com.kepler.repository.UserRepository;
import com.kepler.service.IAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssignmentService implements IAssignmentService {
    private final BuildingRepository buildingRepository;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final UserConverter userConverter;

    @Override
    @Transactional
    public List<StaffDTO> assignBuildingToStaff(AssignmentDTO assignmentBuildingDTO) {

        Long buildingId = assignmentBuildingDTO.getId();
        BuildingEntity buildingEntity = buildingRepository.getOne(buildingId);

        Set<UserEntity> currentStaffs =  buildingEntity.getStaffs();

        List<UserEntity> newStaffs = userRepository.findAllById(assignmentBuildingDTO.getStaffsId());

        currentStaffs.removeIf(oldStaff -> !newStaffs.contains(oldStaff));
        currentStaffs.addAll(newStaffs);

        return newStaffs.stream()
                        .map(userConverter::convertToStaffFromEntity)
                        .collect(Collectors.toList());

    }

    @Override
    @Transactional
    public List<StaffDTO> assignCustomerToStaff(AssignmentDTO assignmentCustomerDTO) {

        Long customerId = assignmentCustomerDTO.getId();
        CustomerEntity customerEntity = customerRepository.getOne(customerId);

        Set<UserEntity> currentStaffs = customerEntity.getStaffs();

        List<UserEntity> newStaffs = userRepository.findAllById(assignmentCustomerDTO.getStaffsId());

        currentStaffs.removeIf(oldStaff -> !newStaffs.contains(oldStaff));
        currentStaffs.addAll(newStaffs);

        return newStaffs.stream()
                        .map(userConverter::convertToStaffFromEntity)
                        .collect(Collectors.toList());
    }

}
