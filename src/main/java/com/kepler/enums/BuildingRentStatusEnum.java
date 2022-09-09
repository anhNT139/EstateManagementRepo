package com.kepler.enums;

public enum BuildingRentStatusEnum {

    CHUA_DUOC_THUE("Chưa được thuê"),
    DA_DUOC_THUE("Đã được thuê");

    private final String status;

    BuildingRentStatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static String statusNameOf(String status) {
        return BuildingRentStatusEnum.valueOf(status).status;
    }

}
