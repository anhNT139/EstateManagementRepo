package com.kepler.repository.custom.impl;

import com.kepler.dto.customer.CustomerSearchRequest;
import com.kepler.entity.CustomerEntity;
import com.kepler.enums.CustomerStatusEnum;
import com.kepler.repository.custom.CustomerRepositoryCustom;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang.StringUtils.isNotBlank;

@Repository
public class CustomerRepositoryImpl implements CustomerRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Page<CustomerEntity> findByCondition(CustomerSearchRequest searchRequest, Long currentLoggedInStaffId) {

        // build search query
        Map<String, Object> queryParameters = new HashMap<>();
        StringBuilder joinQuery = new StringBuilder();
        StringBuilder whereQuery = new StringBuilder();

        whereQuery.append("\nWHERE 1 = 1");

        prepareSearchQuery(searchRequest, whereQuery, joinQuery, queryParameters);

        if (currentLoggedInStaffId != null) {
            joinQuery.append("\nLEFT JOIN c.staffs user");
            whereQuery.append("\nAND user.id = ").append(currentLoggedInStaffId);
        }

        String whereQueryStr = whereQuery.toString();
        String joinQueryStr = joinQuery.toString();

        long numberOfCustomers = countTotalCustomersByCondition(whereQueryStr, joinQueryStr, queryParameters);

        int currentPage = searchRequest.getPage();
        int limit = searchRequest.getLimit();
        Pageable pageable = PageRequest.of(currentPage - 1, limit);

        if (numberOfCustomers > 0) {
            List<Long> customersId = getCustomersIdByCondition(whereQueryStr, joinQueryStr, queryParameters, pageable);
            List<CustomerEntity> customers = getCustomersByIds(customersId);
            return new PageImpl<>(customers, pageable, numberOfCustomers);
        }

        return new PageImpl<>(new ArrayList<>(), pageable, 0);
    }

    @SuppressWarnings("JpaQlInspection")
    private long countTotalCustomersByCondition(String whereQuery, String joinQuery, Map<String, Object> queryParameters) {

        String jpql = "SELECT COUNT(DISTINCT c) FROM CustomerEntity c" + joinQuery + whereQuery;

        Query query = entityManager.createQuery(jpql);
        queryParameters.forEach(query::setParameter);

        return (long) query.getSingleResult();
    }

    private List<CustomerEntity> getCustomersByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }

        String jpql = "SELECT DISTINCT c FROM CustomerEntity c" +
                      "\nLEFT JOIN FETCH c.staffs s" +
                      "\nWHERE c.id in (:ids) ORDER BY c.createdDate DESC";

        return entityManager.createQuery(jpql, CustomerEntity.class)
                            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
                            .setParameter("ids", ids)
                            .getResultList();
    }

    @SuppressWarnings("JpaQlInspection")
    private List<Long> getCustomersIdByCondition(String whereQuery, String joinQuery, Map<String, Object> queryParameters, Pageable pageable) {
        String jpql = "SELECT DISTINCT(c.id) FROM CustomerEntity c" +
                      joinQuery +
                      whereQuery +
                      "\n ORDER BY c.createdDate DESC";

        TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
        queryParameters.forEach(query::setParameter);

        return query
                    .setFirstResult((int) pageable.getOffset())
                    .setMaxResults(pageable.getPageSize())
                    .getResultList();
    }

    private void prepareSearchQuery(CustomerSearchRequest customerSearchRequest, StringBuilder whereQuery, StringBuilder joinQuery, Map<String, Object> parameters) {
        String searchName = customerSearchRequest.getFullName();
        if (isNotBlank(searchName)) {
            whereQuery.append("\nAND c.fullName LIKE CONCAT('%', :name, '%')");
            parameters.put("name", searchName);
        }

        String searchPhone = customerSearchRequest.getPhone();
        if (isNotBlank(searchPhone)) {
            whereQuery.append("\nAND c.phone LIKE CONCAT('%', :phone, '%')");
            parameters.put("phone", searchPhone);
        }

        String searchEmail = customerSearchRequest.getEmail();
        if (isNotBlank(searchEmail)) {
            whereQuery.append("\nAND c.email LIKE CONCAT('%', :email, '%')");
            parameters.put("email", searchEmail);
        }

        String searchStatus = customerSearchRequest.getStatus();
        if (isNotBlank(searchStatus)) {
            whereQuery.append("\nAND c.status = :status");
            parameters.put("status", CustomerStatusEnum.valueOf(searchStatus).name());
        }

        LocalDate searchCreatedDateFrom = customerSearchRequest.getCreateDateFrom();
        if (searchCreatedDateFrom != null) {
            whereQuery.append("\nAND c.createdDate >= :searchCreatedDateFrom");
            parameters.put("searchCreatedDateFrom", LocalDateTime.of(searchCreatedDateFrom, LocalTime.MIN));
        }

        LocalDate searchCreatedDateTo = customerSearchRequest.getCreateDateTo();
        if (searchCreatedDateTo != null) {
            whereQuery.append("\nAND c.createdDate <= :searchCreatedDateTo");
            parameters.put("searchCreatedDateTo", LocalDateTime.of(searchCreatedDateTo, LocalTime.MAX));
        }

        Long searchStaffId = customerSearchRequest.getStaffId();
        if (searchStaffId != null) {
            joinQuery.append("\nLEFT JOIN c.staffs s");
            whereQuery.append("\nAND s.id = :searchStaffId");
            parameters.put("searchStaffId", searchStaffId);
        }

    }

}
