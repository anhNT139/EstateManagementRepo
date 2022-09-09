package com.kepler.repository;

import com.kepler.entity.CustomerEntity;
import com.kepler.repository.custom.CustomerRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long>, CustomerRepositoryCustom {

    Long deleteByIdIn(List<Long> customersId);
    @Query("SELECT s.id FROM CustomerEntity c JOIN c.staffs s WHERE c.id = :customerId")
    List<Long> getStaffsId(Long customerId);

}
