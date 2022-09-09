package com.kepler.service.impl;

import com.kepler.enums.DistrictsEnum;
import com.kepler.service.IDistrictService;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class DistrictService implements IDistrictService {
    @Override
    public Map<String, String> getDistrictsMap() {
        Map<String, String> result = new LinkedHashMap<>();
        for (DistrictsEnum districtsEnum: DistrictsEnum.values()) {
            result.put(districtsEnum.name(), districtsEnum.getDistrictValue());
        }

        return result;
    }
}
