package com.kepler.dto.staff;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AssignmentBuildingDTO {
    private Long buildingId;

    private List<Long> staffIds;

}
