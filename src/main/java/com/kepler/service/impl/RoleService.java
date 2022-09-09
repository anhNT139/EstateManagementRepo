package com.kepler.service.impl;

import com.kepler.constant.SystemConstant;
import com.kepler.converter.RoleConverter;
import com.kepler.dto.RoleDTO;
import com.kepler.entity.RoleEntity;
import com.kepler.repository.RoleRepository;
import com.kepler.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService {

	private final RoleRepository roleRepository;

	private final RoleConverter roleConverter;

	public List<RoleDTO> findAll() {
		List<RoleEntity> roleEntities = roleRepository.findAll();
		List<RoleDTO> list = new ArrayList<>();
		roleEntities.forEach(item -> {
			RoleDTO roleDTO = roleConverter.convertToDto(item);
			list.add(roleDTO);
		});
		return list;
	}

	@Override
	public Map<String, String> getRoles() {
		Map<String,String> roleTerm = new HashMap<>();
		List<RoleEntity> roleEntities = roleRepository.findAllByCode(SystemConstant.User.STAFF);
		roleEntities.forEach(role -> roleTerm.put(role.getCode(), role.getName()));
		return roleTerm;
	}
}
