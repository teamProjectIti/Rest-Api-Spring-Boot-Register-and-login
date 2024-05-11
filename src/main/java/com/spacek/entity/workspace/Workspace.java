package com.spacek.entity.workspace;

import com.spacek.entity.contracts.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Workspace extends BaseEntity {
    private String name;
    private String description;

    @OneToMany(mappedBy = "workspace")
    private List<WorkspaceAvailability> availability;
}
