package com.kepler.repository.custom.impl;

import com.kepler.dto.building.BuildingSearchRequest;
import com.kepler.entity.BuildingEntity;
import com.kepler.repository.custom.BuildingRepositoryCustom;
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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.hibernate.annotations.QueryHints.PASS_DISTINCT_THROUGH;

@Repository
public class BuildingRepositoryImpl implements BuildingRepositoryCustom {
	
	@PersistenceContext 
	EntityManager entityManager;
	
	@Override
	public Page<BuildingEntity> findByCondition(BuildingSearchRequest searchRequest, Long currentLoggedInStaffId) {

		// build search query
		StringBuilder whereQuery = new StringBuilder();
		StringBuilder joinQuery = new StringBuilder();

		whereQuery.append("\nWHERE 1 = 1");

		Map<String, Object> queryParameters = new HashMap<>();
		prepareSearchQueryWithoutJoin(searchRequest, whereQuery, queryParameters);
		prepareSearchQueryWithJoin(searchRequest, whereQuery, joinQuery, queryParameters);

		if (currentLoggedInStaffId != null) { // if current logged-in user is staff
			joinQuery.append("\nLEFT JOIN b.staffs user");
			whereQuery.append("\nAND user.id = ").append(currentLoggedInStaffId);
		}

		String whereQueryStr = whereQuery.toString();
		String joinQueryStr = joinQuery.toString();

		// get list building for this page and count total number of buildings
		long numberOfBuildings = countTotalBuildingsByCondition(whereQueryStr, joinQueryStr, queryParameters);

		int currentPage = searchRequest.getPage();
		int limit = searchRequest.getLimit();
		Pageable pageable = PageRequest.of(currentPage - 1, limit);

		if (numberOfBuildings > 0) {
			List<Long> buildingsId = getBuildingsIdByCondition(whereQueryStr, joinQueryStr, queryParameters, pageable);
			List<BuildingEntity> buildings = getBuildingsByIds(buildingsId); // get building detail
			return new PageImpl<>(buildings, pageable, numberOfBuildings);
		}

		return new PageImpl<>(new ArrayList<>(), pageable, 0);
	}

	@SuppressWarnings("JpaQlInspection")
	private long countTotalBuildingsByCondition(String whereQuery, String joinQuery, Map<String, Object> queryParameters) {

		String jpql = "SELECT COUNT(DISTINCT b) FROM BuildingEntity b" +
				joinQuery + whereQuery;

		Query countQuery = entityManager.createQuery(jpql);
		queryParameters.forEach(countQuery::setParameter);

		return (long) countQuery.getSingleResult();
	}

	@SuppressWarnings("JpaQlInspection")
	private List<Long> getBuildingsIdByCondition(String whereQuery, String joinQuery,
												 Map<String, Object> queryParameters, Pageable pageable) {

		String jpql = "SELECT DISTINCT b.id FROM BuildingEntity b" +
				       joinQuery +
				       whereQuery +
				      "\nORDER BY b.createdDate DESC";

		TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
		queryParameters.forEach(query::setParameter);

		return  query
					.setFirstResult((int) pageable.getOffset())
					.setMaxResults(pageable.getPageSize())
					.getResultList();
	}

	private List<BuildingEntity> getBuildingsByIds(List<Long> ids) {
		if (ids == null || ids.isEmpty()) {
			return new ArrayList<>();
		}

		String jpql = "SELECT DISTINCT b FROM BuildingEntity b" +
					  "\nLEFT JOIN FETCH b.rentAreas" +
					  "\nLEFT JOIN FETCH b.staffs" +
					  "\nWHERE b.id IN (:ids) ORDER BY b.createdDate DESC";

		return entityManager.createQuery(jpql, BuildingEntity.class)
							.setParameter("ids", ids)
							.setHint(PASS_DISTINCT_THROUGH, false)
						    .getResultList();
	}

	private void prepareSearchQueryWithoutJoin(BuildingSearchRequest searchRequest, StringBuilder whereQuery,
											   Map<String, Object> parameters) {
		String searchName = searchRequest.getName();
		if (isNotBlank(searchName)) {
			whereQuery.append("\nAND b.name LIKE CONCAT('%', :name, '%')");
			parameters.put("name", searchName);
		}

		Integer searchNumberOfBasement = searchRequest.getNumberOfBasement();
		if (searchNumberOfBasement != null) {
			whereQuery.append("\nAND b.numberOfBasement = ").append(":numberOfBasement");
            parameters.put("numberOfBasement", searchNumberOfBasement);
		}
		
		Integer searchFloorArea = searchRequest.getFloorArea();
		if (searchFloorArea != null) {
			whereQuery.append("\nAND b.floorArea = ").append(":floorArea");
            parameters.put("floorArea", searchFloorArea);
		}
		
		String searchWard = searchRequest.getWard();
		if (isNotBlank(searchWard)) {
			whereQuery.append("\nAND b.ward LIKE CONCAT('%', :ward, '%')");
            parameters.put("ward", searchWard);
		}

		String searchStreet = searchRequest.getStreet();
		if (isNotBlank(searchStreet)) {
			whereQuery.append("\nAND b.street LIKE CONCAT('%', :street, '%')");
            parameters.put("street", searchStreet);
		}

		String searchDirection = searchRequest.getDirection();
		if (isNotBlank(searchDirection)) {
			whereQuery.append("\nAND b.direction LIKE CONCAT('%', :direction, '%')");
            parameters.put("direction", searchDirection);
		}
	
		String searchLevel = searchRequest.getLevel();
		if(isNotBlank(searchLevel)) {
			whereQuery.append("\nAND b.level LIKE CONCAT('%', :level, '%')");
            parameters.put("level", searchLevel);
		}
	
		Integer searchRentPriceFrom = searchRequest.getRentPriceFrom();
		if (searchRentPriceFrom != null) {
			whereQuery.append("\nAND b.rentPrice >= :rentPriceFrom");
            parameters.put("rentPriceFrom", searchRentPriceFrom);
		}
		
		Integer searchRentPriceTo = searchRequest.getRentPriceTo();
		if (searchRentPriceTo != null) {
            whereQuery.append("\nAND b.rentPrice <= :rentPriceTo");
            parameters.put("rentPriceTo", searchRentPriceTo);
		}
		
		String searchDistrictCode = searchRequest.getDistrict();
		if (isNotBlank(searchDistrictCode)) {
			whereQuery.append("\nAND b.district = :districtCode");
            parameters.put("districtCode", searchDistrictCode);
		}

        List<String> searchRentTypes = searchRequest.getBuildingTypes();
        if (searchRentTypes != null && !searchRentTypes.isEmpty()) {
            whereQuery.append("\nAND (");
            whereQuery.append(IntStream.range(0, searchRentTypes.size())
                                       .mapToObj(index -> "b.type LIKE CONCAT('%', :rentType" + index + ", '%')")
                                       .collect(Collectors.joining(" OR ")));
            whereQuery.append(")");

            IntStream.range(0, searchRentTypes.size())
                     .forEach(index -> parameters.put("rentType" + index, searchRentTypes.get(index)));
        }

		String searchRentStatus = searchRequest.getRentStatus();
		if (isNotBlank(searchRentStatus)) {
			whereQuery.append("\nAND b.status = :searchRentStatus");
			parameters.put("searchRentStatus", searchRentStatus);
		}

		LocalDate searchDateFrom = searchRequest.getCreateDateFrom();
		if (searchDateFrom != null) {
			whereQuery.append("\nAND b.createdDate >= :createDateFrom");
			parameters.put("createDateFrom", LocalDateTime.of(searchDateFrom, LocalTime.MIN));
		}

		LocalDate searchDateTo = searchRequest.getCreateDateTo();
		if (searchDateTo != null) {
			whereQuery.append("\nAND b.createdDate <= :createDateTo");
			parameters.put("createDateTo", LocalDateTime.of(searchDateTo, LocalTime.MAX));
		}
	}
	
	private void prepareSearchQueryWithJoin(BuildingSearchRequest searchRequest, StringBuilder whereQuery, StringBuilder joinQuery,
											Map<String, Object> parameters) {

		Integer searchRentAreaFrom = searchRequest.getRentAreaFrom();
		Integer searchRentAreaTo = searchRequest.getRentAreaTo();
		if (searchRentAreaFrom != null || searchRentAreaTo != null) {
			joinQuery.append("\nLEFT JOIN b.rentAreas ra");
			if (searchRentAreaFrom != null) {
				whereQuery.append("\nAND ra.value >= :rentAreaFrom");
                parameters.put("rentAreaFrom", searchRentAreaFrom);
			}
			if (searchRentAreaTo != null) {
				whereQuery.append("\nAND ra.value <= :rentAreaTo");
                parameters.put("rentAreaTo", searchRentAreaTo);
			}
		}

		Long searchStaffId = searchRequest.getStaffId();
		if (searchStaffId != null) {
			joinQuery.append("\nLEFT JOIN b.staffs staff");
			whereQuery.append("\nAND staff.id = :searchStaffId");
            parameters.put("searchStaffId", searchStaffId);
		}
		
		String searchManagerName = searchRequest.getManagerName();
		String searchManagerPhone = searchRequest.getManagerPhoneNumber();
		if (isNotBlank(searchManagerName) || isNotBlank(searchManagerPhone)) {
			if (searchStaffId == null) {
				joinQuery.append("\nLEFT JOIN b.staffs staff");
			}
			if (isNotBlank(searchManagerName)) {
				whereQuery.append("\nAND staff.fullName LIKE CONCAT('%', :managerName, '%')");
                parameters.put("managerName", searchManagerName.trim());
			}
			if (isNotBlank(searchManagerPhone)) {
				whereQuery.append("\nAND staff.phone LIKE CONCAT('%', :managerPhone, '%')");
                parameters.put("managerPhone", searchManagerPhone.trim());
			}
		}
	}
	
}
