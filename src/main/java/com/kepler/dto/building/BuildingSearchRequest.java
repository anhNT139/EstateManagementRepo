package com.kepler.dto.building;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class BuildingSearchRequest implements Serializable {
    private String name;
    private Integer numberOfBasement;
    private Integer floorArea;
    private String district;
    private String ward;
    private String street;
    private String direction;
    private String level;
    private Integer rentPriceFrom;
    private Integer rentPriceTo;
    private Integer rentAreaFrom;
    private Integer rentAreaTo;
    private String managerName;
    private String managerPhoneNumber;
    private Long staffId;
    private List<String> buildingTypes;
    private String rentStatus;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate createDateFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate createDateTo;

    @Min(value = 0, message = "Số trang phải lớn hơn 0")
    private Integer page = 1;

    private Integer limit = 5;
}
