package com.kepler.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "file")
@Getter
@Setter
public class FileEntity extends BaseEntity {

    @Column(name = "originalname")
    private String originalName;

    @Column(name = "path")
    private String filePath;

    @Column(name = "generatedname", unique = true)
    private String generatedName;
}
