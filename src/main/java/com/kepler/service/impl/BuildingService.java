package com.kepler.service.impl;

import com.kepler.converter.BuildingConverter;
import com.kepler.converter.UserConverter;
import com.kepler.dto.PageableDTO;
import com.kepler.dto.building.BuildingDTO;
import com.kepler.dto.building.BuildingSearchRequest;
import com.kepler.dto.building.BuildingSearchResponse;
import com.kepler.dto.staff.StaffDTO;
import com.kepler.entity.BuildingEntity;
import com.kepler.entity.FileEntity;
import com.kepler.entity.RentAreaEntity;
import com.kepler.entity.UserEntity;
import com.kepler.enums.BuildingRentStatusEnum;
import com.kepler.enums.BuildingTypesEnum;
import com.kepler.exception.UnProcessableEntityException;
import com.kepler.repository.BuildingRepository;
import com.kepler.repository.RentAreaRepository;
import com.kepler.repository.UserRepository;
import com.kepler.security.IAuthenticationFacade;
import com.kepler.service.IBuildingService;
import com.kepler.service.IFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

import static com.kepler.constant.SystemConstant.User.STAFF;
import static com.kepler.constant.SystemConstant.User.STATUS_ACTIVE;
import static org.apache.commons.lang.StringUtils.isNotBlank;

@Service
@RequiredArgsConstructor
public class BuildingService implements IBuildingService {

    private final BuildingRepository buildingRepository;
    private final RentAreaRepository rentAreaRepository;
    private final UserRepository userRepository;
    private final BuildingConverter buildingConverter;
    private final UserConverter userConverter;
    private final IFileService fileService;
    private final IAuthenticationFacade authenticationFacade;


    @Override
    public PageableDTO<BuildingSearchResponse> searchBuildings(BuildingSearchRequest buildingSearchRequest) {

        Long currentLoggedInStaffId = authenticationFacade.getCurrentLoggedInStaffId();

        Page<BuildingEntity> buildingPage = buildingRepository.findByCondition(buildingSearchRequest, currentLoggedInStaffId);

        return buildingConverter.convertToPageableResponse(buildingPage);
    }

    @Override
    public List<BuildingSearchResponse> getAllBuildings() {
        List<BuildingEntity> buildingEntities = buildingRepository.findAll();
        return buildingEntities
                .stream()
                .map(buildingConverter::convertToSearchResponseFromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public BuildingDTO getBuildingById(Long id) {
        Optional<BuildingEntity> optionalBuildingEntity = buildingRepository.findById(id);
        return optionalBuildingEntity
                .map(buildingConverter::convertToDTOFromEntity)
                .orElse(null);
    }

    @Override
    @Transactional
    public Long saveBuilding(BuildingDTO buildingDTO, MultipartFile image) {
        BuildingEntity buildingEntity;
        if (buildingDTO.getId() != null) { // update building
            buildingEntity = buildingRepository.findById(buildingDTO.getId())
                                               .orElseThrow(() -> new UnProcessableEntityException("Id tòa nhà không tồn tại"));
            rentAreaRepository.deleteByBuilding_Id(buildingDTO.getId()); // or using orphanRemoval = true & buildingEntity.getRentAreas().clear() (to remove old rent areas)
            buildingConverter.convertToExistingEntityFromDTO(buildingDTO, buildingEntity);
        } else { // create new building
            buildingEntity = buildingConverter.convertToEntityFromDTO(buildingDTO);
            buildingRepository.save(buildingEntity);
        }

        createRentAreas(buildingEntity, buildingDTO.getRentAreas()); // create new rent areas
        updateImage(buildingEntity, image);

        return buildingEntity.getId();
    }

    private void updateImage(BuildingEntity buildingEntity, MultipartFile image) {
        if (image != null && !image.isEmpty()) {
            FileEntity currentFileEntity = buildingEntity.getImage();
            FileEntity newFileEntity = fileService.save(currentFileEntity, image);
            buildingEntity.setImage(newFileEntity);
        }
    }

    private void createRentAreas(BuildingEntity buildingEntity, String rentAreas) {
        List<RentAreaEntity> rentAreaEntities = new ArrayList<>();
        if (isNotBlank(rentAreas)) {
            for (String value: rentAreas.split(",")) {
                RentAreaEntity rentAreaEntity = new RentAreaEntity();
                rentAreaEntity.setValue(Integer.valueOf(value));
                rentAreaEntity.setBuilding(buildingEntity);
                rentAreaEntities.add(rentAreaEntity);
            }
            rentAreaRepository.saveAll(rentAreaEntities);
        }
    }

    @Override
    @Transactional
    public Long removeBuildings(List<Long> buildingIds) {
        if (buildingIds != null && ! buildingIds.isEmpty()) {
            List<FileEntity> imagesOfRemovedBuildings = buildingRepository.getImages(buildingIds);
            rentAreaRepository.deleteByBuilding_IdIn(buildingIds);
            buildingRepository.deleteInBatch(buildingRepository.findAllById(buildingIds));
            fileService.delete(imagesOfRemovedBuildings);
            return (long) buildingIds.size();
        }
        return 0L;
    }

    @Override
    public List<StaffDTO> getStaffs(Long buildingId) {

        List<UserEntity> userEntities = userRepository.findByStatusAndRoles_Code(STATUS_ACTIVE, STAFF); // get all active staff

        List<StaffDTO> staffDTOs = new ArrayList<>();
        userEntities.forEach(userEntity -> staffDTOs.add(userConverter.convertToStaffFromEntity(userEntity))); // convert all user entities -> staffDTOs

        /* Common */
//        Optional<BuildingEntity> buildingEntity = buildingRepository.findById(buildingId);
//        if (buildingEntity.isPresent()) {
//            Set<UserEntity> buildingManagers = buildingEntity.get().getStaffs();
//            staffDTOs.forEach(
//                    staff -> buildingManagers.parallelStream()
//                                                .filter(buildingManager -> buildingManager.getId().equals(staff.getId()))
//                                                .findAny()
//                                                .ifPresent(buildingManager -> staff.setChecked("checked"))
//            );
//            return staffDTOs;
//        }
//        return null;

        /* exists keyword */
//        staffDTOs.stream()
//                 .filter(staff -> buildingRepository.existsByIdAndStaffs_Id(buildingId, staff.getId()))
//                 .forEach(staff -> staff.setChecked("checked"));

        /* @Query */
        List<Long> staffsId = buildingRepository.getStaffsId(buildingId); // get all staffs manage building
        staffDTOs.stream()
                 .filter(staffDTO -> staffsId.contains(staffDTO.getId()))
                 .forEach(staffDTO -> staffDTO.setChecked("checked"));

        return staffDTOs;
    }

    @Override
    public Map<String, String> getRentStatus() {
        return Arrays.stream(BuildingRentStatusEnum.values())
                .collect(Collectors.toMap(BuildingRentStatusEnum::name, BuildingRentStatusEnum::getStatus));
    }

    @Override
    public Map<String, String> getBuildingTypes() {
        return Arrays.stream(BuildingTypesEnum.values())
                .collect(Collectors.toMap(BuildingTypesEnum::name, BuildingTypesEnum::getBuildingTypeValue));
    }
}
