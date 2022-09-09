package com.kepler.enums;

public enum CustomerStatusEnum {
    BEING_SUPPORTED("Đang hỗ trợ"),
    RENTED("Đã thuê"),
    WAITING_FOR_SUPPORT("Chờ hỗ trợ"),
    WAITING_FOR_RESPONSE("Đợi phản hồi"),
    STOP_SUPPORT("Ngừng hỗ trợ");
    private final String statusName;

    CustomerStatusEnum(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusName() {
        return statusName;
    }
}
