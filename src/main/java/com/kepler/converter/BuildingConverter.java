package com.kepler.converter;

import com.kepler.dto.PageableDTO;
import com.kepler.dto.building.BuildingDTO;
import com.kepler.dto.building.BuildingSearchResponse;
import com.kepler.entity.BuildingEntity;
import com.kepler.entity.FileEntity;
import com.kepler.entity.RentAreaEntity;
import com.kepler.entity.UserEntity;
import com.kepler.enums.BuildingRentStatusEnum;
import com.kepler.enums.BuildingTypesEnum;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.kepler.constant.SystemConstant.File.STATIC_RESOURCE_URL;
import static com.kepler.enums.DistrictsEnum.districtNameOf;
import static com.kepler.utils.ConverterUtils.convertToDateTimeStr;
import static com.kepler.utils.ConverterUtils.getInfoFromUserEntity;
import static org.apache.commons.lang.StringUtils.isNotBlank;

@Component
@RequiredArgsConstructor
public class BuildingConverter {

	private final ModelMapper modelMapper;

	public BuildingSearchResponse convertToSearchResponseFromEntity(BuildingEntity buildingEntity) {
		
		BuildingSearchResponse buildingSearchResponse = modelMapper.map(buildingEntity, BuildingSearchResponse.class);

		// set building type
        String types = buildingEntity.getType();
        if (isNotBlank(types)) {
            List<String> listType = Arrays.asList(types.split(","));
            String typeName = listType.stream()
                                      .map(type -> BuildingTypesEnum.valueOf(type).getBuildingTypeValue())
                                      .collect(Collectors.joining(", "));
            buildingSearchResponse.setTypeName(typeName);
        }

		// set address
        String address = Stream.of(buildingEntity.getStreet(), buildingEntity.getWard(), districtNameOf(buildingEntity.getDistrict()))
                               .filter(StringUtils::isNotBlank).collect(Collectors.joining(", "));
        buildingSearchResponse.setAddress(address);
				
		// set manager info
		Set<UserEntity> staffs = buildingEntity.getStaffs();

        List<String> managersInfo = new ArrayList<>();
        for (UserEntity staff: staffs) {
            managersInfo.add(getInfoFromUserEntity(staff));
        }
        buildingSearchResponse.setManagersInfo(String.join(", ", managersInfo));
		
		// set rent areas
		Set<RentAreaEntity> rentAreas = buildingEntity.getRentAreas();
		String rentAreasResponse = rentAreas.stream()
                                            .map(rentArea -> rentArea.getValue().toString())
				                            .collect(Collectors.joining(", "));
		buildingSearchResponse.setRentAreas(rentAreasResponse);

        // set create date
        buildingSearchResponse.setCreateDate(convertToDateTimeStr(buildingEntity.getCreatedDate(), "dd/MM/yyyy"));

        // set status
        buildingSearchResponse.setStatus(BuildingRentStatusEnum.statusNameOf(buildingEntity.getStatus()));
		
		return buildingSearchResponse;
	}

    public BuildingDTO convertToDTOFromEntity(BuildingEntity buildingEntity) {

        BuildingDTO buildingDTO = modelMapper.map(buildingEntity, BuildingDTO.class);

        // set rent areas
        String rentAreasResponse = buildingEntity.getRentAreas()
                                                 .stream()
                                                 .map(rentArea -> rentArea.getValue().toString())
                                                 .collect(Collectors.joining(","));
        buildingDTO.setRentAreas(rentAreasResponse);

        // set image url
        FileEntity image =  buildingEntity.getImage();
        if (image != null) {
            buildingDTO.setImageUrl(
                    STATIC_RESOURCE_URL + image.getFilePath().replace(File.separator, "/") + "/" + image.getGeneratedName()
            );
        }

        return buildingDTO;
    }
	
	public BuildingEntity convertToEntityFromDTO(BuildingDTO buildingDTO) {
        return modelMapper.map(buildingDTO, BuildingEntity.class);
	}

    public void convertToExistingEntityFromDTO(BuildingDTO buildingDTO, BuildingEntity buildingEntity) {
        modelMapper.map(buildingDTO, buildingEntity);
    }

    public PageableDTO<BuildingSearchResponse> convertToPageableResponse(Page<BuildingEntity> page) {

        PageableDTO<BuildingSearchResponse> pageableDTO = new PageableDTO<>();

        pageableDTO.setItems(page.getContent().stream().map(this::convertToSearchResponseFromEntity).collect(Collectors.toList()));
        pageableDTO.setLimit(page.getSize());
        pageableDTO.setCurrentPage(page.getNumber() + 1);
        pageableDTO.setTotalItems(page.getTotalElements());

        int totalPages = page.getTotalPages();
        if (totalPages == 0) {
            totalPages++;
        }
        pageableDTO.setTotalPages(totalPages);

        return pageableDTO;
    }

}
