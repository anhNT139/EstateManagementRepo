package com.kepler.dto.building;

import com.kepler.validation.annotation.BuildingRentStatusConstraint;
import com.kepler.validation.annotation.BuildingTypeConstraint;
import com.kepler.validation.annotation.DistrictConstraint;
import com.kepler.validation.group.FirstValidation;
import com.kepler.validation.group.OnCreate;
import com.kepler.validation.group.OnUpdate;
import com.kepler.validation.group.SecondValidation;
import lombok.Getter;
import lombok.Setter;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Getter
@Setter
@GroupSequence({FirstValidation.class, SecondValidation.class, BuildingDTO.class})
public class BuildingDTO {

    @Null(message = "Id must be null", groups = OnCreate.class)
    @NotNull(message = "Id tòa nhà cập nhật không đuợc null", groups = OnUpdate.class)
    private Long id;

    @NotBlank(message = "Tên tòa nhà không được để trống", groups = FirstValidation.class)
    private String name;

    @NotBlank(message = "Đường không được để trống", groups = FirstValidation.class)
    private String street;

    @NotBlank(message = "Phường không được để trống", groups = FirstValidation.class)
    private String ward;

    @NotBlank(message = "Quận không được để trống", groups = FirstValidation.class)
    @DistrictConstraint(groups = SecondValidation.class)
    private String district;

    @Pattern(regexp ="((\\d+,)*\\d+)|s*", message = "Diện tích thuê không hợp lệ", groups = SecondValidation.class)
    private String rentAreas;

    @BuildingTypeConstraint
    private String type;

    @BuildingRentStatusConstraint
    private String status;

    private String structure;

    private Integer numberOfBasement;

    private Integer floorArea;

    private String direction;

    private String level;

    private Integer rentPrice;

    private String rentPriceDescription;

    private String serviceFee;

    private String carFee;

    private String motorbikeFee;

    private String overtimeFee;

    private String waterFee;

    private String electricityFee;

    private String deposit;

    private String payment;

    private String rentTime;

    private String decorationTime;

    private BigDecimal brokerageFee;

    private String note;

    private String imageUrl;
}
