package com.kepler.dto.staff;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class AssignmentDTO {
    @NotNull(message = "id không được null")
    private Long id; //buildingId or customerId

    private List<Long> staffsId;

}
