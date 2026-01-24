package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "employees")
@Getter
@Setter
public class EmployeeEntity {
    @Id
    @UuidGenerator
    private UUID id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false)
    private String personnelNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DepartmentEnum department;

    @Column(nullable = false)
    private String jobDescription;

    @Column(nullable = false)
    private Integer annualIncome;
}