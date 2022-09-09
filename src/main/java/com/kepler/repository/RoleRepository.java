package com.kepler.repository;

import com.kepler.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
	RoleEntity findOneByCode(String code);
	List<RoleEntity> findAllByCode(String code);
}
