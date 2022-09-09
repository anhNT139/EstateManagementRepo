package com.kepler.converter;

import com.kepler.dto.RoleDTO;
import com.kepler.entity.RoleEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleConverter {

	private final ModelMapper modelMapper;
	
	public RoleDTO convertToDto(RoleEntity entity) {
        return modelMapper.map(entity, RoleDTO.class);
    }

    public RoleEntity convertToEntity(RoleDTO dto) {
        return modelMapper.map(dto, RoleEntity.class);
    }
}
