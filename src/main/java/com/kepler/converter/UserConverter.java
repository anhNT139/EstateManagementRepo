package com.kepler.converter;

import com.kepler.dto.PageableDTO;
import com.kepler.dto.user.UserDTO;
import com.kepler.dto.user.UserProfileDTO;
import com.kepler.dto.user.UserSearchResponse;
import com.kepler.dto.staff.StaffDTO;
import com.kepler.entity.UserEntity;
import com.kepler.enums.UserStatusEnum;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import static com.kepler.utils.ConverterUtils.getInfoFromUserEntity;
import static com.kepler.utils.ConverterUtils.convertToDateTimeStr;

@Component
@RequiredArgsConstructor
public class UserConverter {

    private final ModelMapper modelMapper;

    public UserDTO convertToDto (UserEntity entity) {
        UserDTO userDTO = modelMapper.map(entity, UserDTO.class);
        if (! entity.getRoles().isEmpty()) {
            userDTO.setRoleCode(entity.getRoles().iterator().next().getCode());
        }
        return userDTO;
    }

    public UserEntity convertToEntity(UserDTO dto) {
        return modelMapper.map(dto, UserEntity.class);
    }

    public void convertToExistingEntity(UserProfileDTO dto, UserEntity entity) {
        modelMapper.map(dto, entity);
    }

    public UserSearchResponse convertToSearchResponse(UserEntity entity) {
        UserSearchResponse searchResponse = new UserSearchResponse();
        searchResponse.setId(entity.getId());
        searchResponse.setStatus(UserStatusEnum.of(entity.getStatus()).getStatusName());
        searchResponse.setUsername(entity.getUsername());
        searchResponse.setFullName(entity.getFullName());
        searchResponse.setPhone(entity.getPhone());
        searchResponse.setCreateDate(convertToDateTimeStr(entity.getCreatedDate(),"dd/MM/yyyy"));
        if (! entity.getRoles().isEmpty()) {
            searchResponse.setRole(entity.getRoles().iterator().next().getCode());
        }
        return searchResponse;
    }

    public StaffDTO convertToStaffFromEntity(UserEntity userEntity) {
        StaffDTO staffDTO = new StaffDTO();

        staffDTO.setId(userEntity.getId());
        staffDTO.setInfo(getInfoFromUserEntity(userEntity));

        return staffDTO;
    }

    public PageableDTO<UserSearchResponse> convertToPageableResponseDTO(Page<UserEntity> page) {

        PageableDTO<UserSearchResponse> pageableDTO = new PageableDTO<>();

        int totalPages = page.getTotalPages();
        if (totalPages == 0) {
            totalPages++;
        }
        pageableDTO.setTotalPages(totalPages);

        pageableDTO.setItems(page.getContent().stream().map(this::convertToSearchResponse).collect(Collectors.toList()));

        pageableDTO.setLimit(page.getSize());
        pageableDTO.setTotalItems(page.getTotalElements());
        pageableDTO.setCurrentPage(page.getNumber() + 1);

        return pageableDTO;
    }
}
