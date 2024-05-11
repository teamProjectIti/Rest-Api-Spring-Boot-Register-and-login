package com.spacek.entity.security;

import com.spacek.entity.contracts.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="Role")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Role extends BaseEntity
{
    private String name;
}
