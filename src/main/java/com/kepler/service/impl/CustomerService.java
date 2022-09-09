package com.kepler.service.impl;

import com.kepler.converter.CustomerConverter;
import com.kepler.converter.UserConverter;
import com.kepler.dto.customer.CustomerDTO;
import com.kepler.dto.PageableDTO;
import com.kepler.dto.customer.CustomerSearchRequest;
import com.kepler.dto.customer.CustomerSearchResponse;
import com.kepler.dto.staff.StaffDTO;
import com.kepler.entity.CustomerEntity;
import com.kepler.entity.UserEntity;
import com.kepler.enums.CustomerStatusEnum;
import com.kepler.exception.UnProcessableEntityException;
import com.kepler.repository.CustomerRepository;
import com.kepler.repository.TransactionRepository;
import com.kepler.repository.UserRepository;
import com.kepler.security.IAuthenticationFacade;
import com.kepler.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.kepler.constant.SystemConstant.User.*;
import static com.kepler.enums.CustomerStatusEnum.WAITING_FOR_SUPPORT;

@Service
@RequiredArgsConstructor
public class CustomerService implements ICustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final CustomerConverter customerConverter;
    private final UserConverter userConverter;

    private final TransactionRepository transactionRepository;
    private final IAuthenticationFacade authenticationFacade;


    @Override
    public PageableDTO<CustomerSearchResponse> searchCustomers(CustomerSearchRequest customerSearchRequest) {

        Long currentLoggedInStaffId = authenticationFacade.getCurrentLoggedInStaffId();

        Page<CustomerEntity> customerPage = customerRepository.findByCondition(customerSearchRequest, currentLoggedInStaffId);

        return customerConverter.convertToPageableResponse(customerPage);
    }

    @Override
    public CustomerDTO findById(Long id) {
        CustomerEntity customerEntity = customerRepository.findById(id).orElse(null);
        if (customerEntity != null) {
            return customerConverter.convertToDTOFromEntity(customerEntity);
        }
        return null;
    }

    @Override
    @Transactional
    public Long saveCustomer(CustomerDTO customerDTO) {

        CustomerEntity customerEntity;

        if (customerDTO.getId() != null) { // update customer
           customerEntity = customerRepository.findById(customerDTO.getId())
                                              .orElseThrow(() -> new UnProcessableEntityException("Id khách hàng không tồn tại"));
           customerConverter.convertToExistingEntityFromDTO(customerEntity, customerDTO);

        } else { // create new customer
            customerEntity = customerConverter.convertToEntityFromDTO(customerDTO);
            customerEntity.setStatus(WAITING_FOR_SUPPORT.name());
            customerRepository.save(customerEntity);
        }

        return customerEntity.getId();
    }

    @Override
    @Transactional
    public Long removeCustomers(List<Long> customersId) {
        transactionRepository.deleteByCustomer_IdIn(customersId);
        customerRepository.deleteInBatch(customerRepository.findAllById(customersId));
        return (long) customersId.size();
    }

    @Override
    public List<StaffDTO> getStaffs(Long customerId) {
        List<UserEntity> userEntities = userRepository.findByStatusAndRoles_Code(STATUS_ACTIVE, STAFF);

        List<StaffDTO> staffDTOs = new ArrayList<>();
        userEntities.forEach(entity -> staffDTOs.add(userConverter.convertToStaffFromEntity(entity)));

        List<Long> staffsId = customerRepository.getStaffsId(customerId);
        staffDTOs.stream()
                 .filter(user -> staffsId.contains(user.getId()))
                 .forEach(staff -> staff.setChecked("checked"));

        return staffDTOs;
    }

    @Override
    public Map<String, String> getCustomerStatusMap() {
        Map<String, String> map = new HashMap<>();
        Arrays.stream(CustomerStatusEnum.values()).forEach(customerStatusEnum -> map.put(customerStatusEnum.name(), customerStatusEnum.getStatusName()));
        return map;
    }

}
