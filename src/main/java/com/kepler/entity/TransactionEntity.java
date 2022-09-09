package com.kepler.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "transaction")
@Getter
@Setter
public class TransactionEntity extends BaseEntity {

    @Column(name = "code")
    private String code;

    @Column(name = "note")
    private String note;

    @ManyToOne
    @JoinColumn(name = "customerid")
    private CustomerEntity customer;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (! (obj instanceof TransactionEntity)) return false;
        return ((TransactionEntity) obj).getId().equals(getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
