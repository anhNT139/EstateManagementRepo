package com.kepler.enums;

public enum DistrictsEnum {
    QUAN_1("Đống Đa"),
    QUAN_2("Hai Bà Trưng"),
    QUAN_3("Hoàn kiếm"),
    QUAN_4("Hoàng Mai"),
    QUAN_5("Long Biên"),
    QUAN_6("Thanh Xuân");

    private final String districtValue;

    DistrictsEnum(String districtValue) {
        this.districtValue = districtValue;
    }

	public String getDistrictValue() {
		return districtValue;
	}

    public static String districtNameOf(String code) {
        return DistrictsEnum.valueOf(code).getDistrictValue();
    }
}
