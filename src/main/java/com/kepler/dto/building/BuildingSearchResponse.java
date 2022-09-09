package com.kepler.dto.building;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuildingSearchResponse {
	private String id;
	private String createDate;
	private String name;
	private String address;
	private String managersInfo;
	private Integer floorArea;
    private String rentAreas;
	private Integer rentPrice;
	private String typeName;
	private String status;

}
