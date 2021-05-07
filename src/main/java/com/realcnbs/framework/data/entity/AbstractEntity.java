package com.realcnbs.framework.data.entity;

import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@ToString
public abstract class AbstractEntity implements Serializable {

    protected LocalDateTime createdAt;

    protected Long id;

    protected LocalDateTime updatedAt;

    protected AbstractEntity() {
        createdAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
