package com.kepler.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "user")
@Getter
@Setter
public class UserEntity extends BaseEntity {

    private static final long serialVersionUID = -4988455421375043688L;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "fullname", nullable = false)
    private String fullName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "userid", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "roleid", nullable = false))
    private Set<RoleEntity> roles = new HashSet<>();

    @ManyToMany(mappedBy = "staffs")
    private Set<BuildingEntity> buildings = new HashSet<>();

    @ManyToMany(mappedBy = "staffs")
    private Set<CustomerEntity> customers = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (! (o instanceof UserEntity)) return false;
        return ((UserEntity) o).getId().equals(getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
