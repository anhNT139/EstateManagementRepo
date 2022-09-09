package com.kepler.enums;

public enum UserStatusEnum {
    STATUS_ACTIVE(1, "Hoạt động"),
    STATUS_NOT_ACTIVE(0, "Bị khóa");

    private final Integer statusCode;
    private final String statusName;

    UserStatusEnum(int statusCode, String statusName) {
        this.statusCode = statusCode;
        this.statusName = statusName;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getStatusName() {
        return statusName;
    }

    public static UserStatusEnum of(Integer statusCode) {
        if (statusCode == 1) {
            return STATUS_ACTIVE;
        }
        return STATUS_NOT_ACTIVE;
    }
}
