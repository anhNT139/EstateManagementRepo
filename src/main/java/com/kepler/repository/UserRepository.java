package com.kepler.repository;

import com.kepler.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Page<UserEntity> findByUsernameContainingIgnoreCaseAndStatusOrFullNameContainsIgnoreCaseAndStatus(String userName, int status1, String fullName, int status2,
                                                                                                  Pageable pageable);
    Page<UserEntity> findByStatusNot(int status, Pageable pageable);
    Page<UserEntity> findByStatus(int status, Pageable pageable);
    long countByUsernameContainingIgnoreCaseOrFullNameContainingIgnoreCaseAndStatusNot(String userName, String fullName, int status);
    long countByStatusNot(int status);
    UserEntity findOneByUsername(String userName);
    List<UserEntity> findByStatusAndRoles_Code(Integer status, String roleCode);
    List<UserEntity> findStaffsByBuildings_Id(Long buildingId);
    @Query("SELECT b.id FROM UserEntity u JOIN u.buildings b WHERE u.id = :userId")
    List<Long> getBuildingsIdByUserId(Long userId);
    @Query("SELECT c.id FROM UserEntity u JOIN u.customers c WHERE u.id = :userId")
    List<Long> getCustomersIdByUserId(Long userId);
    boolean existsByUsername(String username);
    @Query("SELECT DISTINCT u.id FROM UserEntity u JOIN u.roles role WHERE role.code = 'manager'")
    List<Long> getAllManagersId();
}
