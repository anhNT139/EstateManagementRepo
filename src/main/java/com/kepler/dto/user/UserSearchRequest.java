package com.kepler.dto.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter
@Setter
public class UserSearchRequest {

    private String searchValue;

    private Integer status;

    @Min(value = 1, message = "Số trang phải lớn hơn 0")
    private Integer page = 1;

    private Integer limit = 5;
}
