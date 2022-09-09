package com.kepler.service;

import com.kepler.dto.customer.CustomerDTO;
import com.kepler.dto.PageableDTO;
import com.kepler.dto.customer.CustomerSearchRequest;
import com.kepler.dto.customer.CustomerSearchResponse;
import com.kepler.dto.staff.StaffDTO;

import java.util.List;
import java.util.Map;

public interface ICustomerService {

    CustomerDTO findById(Long id);

    Long saveCustomer(CustomerDTO customerDTO);

    Long removeCustomers(List<Long> customersId);

    Map<String, String> getCustomerStatusMap();

    PageableDTO<CustomerSearchResponse> searchCustomers(CustomerSearchRequest customerSearchRequest);

    List<StaffDTO> getStaffs(Long customerId);
}
