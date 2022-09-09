package com.kepler.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "rentarea")
@Getter
@Setter
public class RentAreaEntity extends BaseEntity {
	
	@Column(name = "value")
	private Integer value;
	
	@ManyToOne()
	@JoinColumn(name = "buildingid")
	private BuildingEntity building;

	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
		if (! (o instanceof RentAreaEntity)) return false;
		return ((RentAreaEntity) o).getId().equals(getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}


}
