package com.kepler.dto.customer;

import com.kepler.validation.group.FirstValidation;
import com.kepler.validation.group.SecondValidation;
import lombok.Getter;
import lombok.Setter;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@GroupSequence({FirstValidation.class, SecondValidation.class, CustomerDTO.class})
public class CustomerDTO {
    private Long id;

    @NotBlank(message = "Tên không được để trống", groups = FirstValidation.class)
    private String fullName;

    @NotBlank(message = "Số điện thoại không được để trống", groups = FirstValidation.class)
    @Pattern(regexp = "\\d+", message = "Số điện thoại không hợp lệ", groups = SecondValidation.class)
    private String phone;

    private String email;

    private String note;

    private String status;
}
