package com.kepler.dto.customer;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import java.time.LocalDate;

@Getter
@Setter
public class CustomerSearchRequest {
    private String fullName;
    private String email;
    private String phone;
    private String note;
    private Long staffId;
    private String status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate createDateFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate createDateTo;

    @Min(value = 1, message = "Số trang phải lớn hơn 0")
    private Integer page = 1;
    private Integer limit = 5;

}
