package com.kepler.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "customer")
@Getter
@Setter
public class CustomerEntity extends BaseEntity {

    @Column(name = "fullname")
    private String fullName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "note")
    private String note;

    @Column(name = "status")
    private String status;

    @ManyToMany
    @JoinTable(name = "assignmentcustomer",
            joinColumns = @JoinColumn(name = "customerid", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "staffid", nullable = false))
    private Set<UserEntity> staffs = new HashSet<>();

    @OneToMany(mappedBy = "customer", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    Set<TransactionEntity> transactions = new HashSet<>();
}
