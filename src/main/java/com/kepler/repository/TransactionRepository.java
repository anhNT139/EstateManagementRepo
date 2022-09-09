package com.kepler.repository;

import com.kepler.entity.TransactionEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    List<TransactionEntity> findByCustomer_Id(Long customerId, Sort sort);

    @Modifying
    @Query("DELETE FROM TransactionEntity t WHERE t.customer.id in (:ids)")
    void deleteByCustomer_IdIn(@Param("ids") List<Long> customersId);

}
