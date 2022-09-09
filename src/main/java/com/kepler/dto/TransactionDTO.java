package com.kepler.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class TransactionDTO {

    @NotBlank(message = "Ghi chú không được để trống")
    private String note;

    private Long customerId;

    @NotBlank(message = "Loại giao dịch không được để trống")
    private String code;

    private String codeName;

    private String staffUserName;

    private String createTime;
}
