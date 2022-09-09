package com.kepler.dto.staff;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StaffDTO {
    private Long id;
    private String info;
    private String checked = "";
}
