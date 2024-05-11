package com.spacek.entity.workspace;

import com.spacek.entity.contracts.BaseEntity;
import jakarta.persistence.*;

import java.sql.Time;
import java.time.DayOfWeek;

@Entity
public class WorkspaceAvailability extends BaseEntity {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DayOfWeek day;
    @Column(nullable = false)
    private Time fromTime;
    @Column(nullable = false)
    private Time toTime;
    @Column(nullable = false, columnDefinition = "BOOLEAN")
    private Boolean isAvailable;
    @ManyToOne
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;
}
