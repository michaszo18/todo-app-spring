package com.example.todoapp.model;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@MappedSuperclass
abstract class BaseAuditableEntity {

    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    @PrePersist
    void prePersist() {
        this.createdOn = LocalDateTime.now();
    }

    @PreUpdate
    void updatePersist() {
        this.updatedOn = LocalDateTime.now();
    }
}
