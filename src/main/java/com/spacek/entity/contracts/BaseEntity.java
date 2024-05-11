package com.spacek.entity.contracts;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@MappedSuperclass
abstract public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    protected Long id;

    @Column(updatable = false)
    private LocalDate creationDate;
    private LocalDate ModificationDate;
    @Column(updatable = false)
    private String createUser;
    private String modificationUser;
}
