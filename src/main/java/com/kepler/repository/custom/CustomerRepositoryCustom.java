package com.kepler.repository.custom;

import com.kepler.dto.customer.CustomerSearchRequest;
import com.kepler.entity.CustomerEntity;
import org.springframework.data.domain.Page;

public interface CustomerRepositoryCustom {
    Page<CustomerEntity> findByCondition(CustomerSearchRequest customerSearchRequest, Long staffId);
}
