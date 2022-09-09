package com.kepler.converter;

import com.kepler.dto.PageableDTO;
import com.kepler.dto.customer.CustomerDTO;
import com.kepler.dto.customer.CustomerSearchResponse;
import com.kepler.entity.CustomerEntity;
import com.kepler.entity.UserEntity;
import com.kepler.enums.CustomerStatusEnum;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.kepler.utils.ConverterUtils.convertToDateTimeStr;
import static com.kepler.utils.ConverterUtils.getInfoFromUserEntity;
import static org.apache.commons.lang.StringUtils.isNotBlank;

@Component
@RequiredArgsConstructor
public class CustomerConverter {

    private final ModelMapper modelMapper;

    public CustomerSearchResponse convertToSearchResponseFromEntity(CustomerEntity customerEntity) {
        CustomerSearchResponse response = modelMapper.map(customerEntity, CustomerSearchResponse.class);
        if (isNotBlank(customerEntity.getStatus())) {
            response.setStatusName(CustomerStatusEnum.valueOf(customerEntity.getStatus()).getStatusName());
        }

        List<String> managerInfo = new ArrayList<>();
        for (UserEntity staff: customerEntity.getStaffs()) {
            managerInfo.add(getInfoFromUserEntity(staff));
        }
        response.setManagersInfo(String.join(", ", managerInfo));

        response.setCreateDate(convertToDateTimeStr(customerEntity.getCreatedDate(), "dd/MM/yyyy"));

        return response;
    }

    public CustomerEntity convertToEntityFromDTO(CustomerDTO source) {
        return modelMapper.map(source, CustomerEntity.class);
    }

    public void convertToExistingEntityFromDTO(CustomerEntity destination, CustomerDTO source) {
        modelMapper.map(source, destination);
    }

    public CustomerDTO convertToDTOFromEntity(CustomerEntity customerEntity) {
        return modelMapper.map(customerEntity, CustomerDTO.class);
    }

    public PageableDTO<CustomerSearchResponse> convertToPageableResponse(Page<CustomerEntity> page) {
        PageableDTO<CustomerSearchResponse> pageableDTO = new PageableDTO<>();
        pageableDTO.setItems(page.getContent().stream().map(this::convertToSearchResponseFromEntity).collect(Collectors.toList()));
        pageableDTO.setTotalItems(page.getTotalElements());
        pageableDTO.setCurrentPage(page.getNumber() + 1);
        int totalPages = page.getTotalPages();
        if (totalPages == 0) {
            totalPages++;
        }
        pageableDTO.setTotalPages(totalPages);
        pageableDTO.setLimit(page.getSize());

        return pageableDTO;
    }
}
