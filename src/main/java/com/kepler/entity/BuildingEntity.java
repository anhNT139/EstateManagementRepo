package com.kepler.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "building")
@Getter
@Setter
public class BuildingEntity extends BaseEntity {

	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "street")
	private String street;
	
	@Column(name = "ward")
	private String ward;
	
	@Column(name = "district")
	private String district;
	
	@Column(name = "structure")
	private String structure;
	
	@Column(name = "numberofbasement")
	private Integer numberOfBasement;
	
	@Column(name = "floorarea")
	private Integer floorArea;
	
	@Column(name = "direction")
	private String direction;
	
	@Column(name = "level")
	private String level;
	
	@Column(name = "rentprice")
	private Integer rentPrice;

	@Column(name = "rentpricedescription")
	private String rentPriceDescription;
	
	@Column(name = "servicefee")
	private String serviceFee;
	
	@Column(name = "carfee")
	private String carFee;
	
	@Column(name = "motofee")
	private String motorbikeFee;
	
	@Column(name = "overtimefee")
	private String overtimeFee;
	
	@Column(name = "waterfee")
	private String waterFee;
	
	@Column(name = "electricityfee")
	private String electricityFee;
	
	@Column(name = "deposit")
	private String deposit;
	
	@Column(name = "payment")
	private String payment;
	
	@Column(name = "renttime")
	private String rentTime;
	
	@Column(name = "decorationtime")
	private String decorationTime;
	
	@Column(name = "brokeragetee")
	private BigDecimal brokerageFee;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "note")
	private String note;
	
	@Column(name = "linkofbuilding")
	private String linkOfBuilding;
	
	@Column(name = "map")
	private String map;
	
	@Column(name = "avatar")
	private String avatar;

	@Column(name = "status")
	private String status;
	
	@OneToMany(mappedBy = "building", fetch = LAZY, cascade = CascadeType.ALL)
	private Set<RentAreaEntity> rentAreas = new HashSet<>();

    @ManyToMany()
    @JoinTable(name = "assignmentbuilding",
            joinColumns = @JoinColumn(name = "buildingid", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "staffid", nullable = false))
    private Set<UserEntity> staffs = new HashSet<>();

	@OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = LAZY)
	@JoinColumn(name = "fileid")
	private	FileEntity image;

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (! (o instanceof BuildingEntity)) return false;
        return ((BuildingEntity) o).getId().equals(getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
