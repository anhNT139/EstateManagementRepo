package com.kepler.dto.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class UserProfileDTO {

    private Long id;

    @NotBlank(message = "Username không được để trống")
    private String username;

    @NotBlank(message = "Tên không được để trống")
    private String fullName;

    @Pattern(regexp = "\\d+|s*", message = "Số điện thoại không hợp lệ")
    private String phone;

    @Email(message = "Email không đúng định dạng")
    private String email;
}
