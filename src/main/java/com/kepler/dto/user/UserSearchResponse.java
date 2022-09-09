package com.kepler.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSearchResponse {

    private Long id;

    private String username;

    private String fullName;

    private String phone;

    private String status;

    private String role;

    private String createDate;
}
