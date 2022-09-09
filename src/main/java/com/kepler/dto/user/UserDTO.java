package com.kepler.dto.user;

import com.kepler.validation.annotation.AccountStatusConstraint;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserDTO extends UserProfileDTO {

    @NotNull(message = "Trạng thái không được để trống")
    @AccountStatusConstraint
    private Integer status;

    @NotBlank(message = "Vai trò không được để trống")
    private String roleCode;
}
