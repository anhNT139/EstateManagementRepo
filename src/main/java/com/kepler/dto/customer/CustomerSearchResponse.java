package com.kepler.dto.customer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerSearchResponse {
    private Long id;
    private String createDate;
    private String fullName;
    private String managersInfo;
    private String phone;
    private String email;
    private String note;
    private String statusName;
}
