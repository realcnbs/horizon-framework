package com.realcnbs.framework.rest.repr;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonView;
import com.realcnbs.framework.data.entity.AbstractEntity;
import com.realcnbs.framework.rest.controller.view.Public;

import java.time.format.DateTimeFormatter;

@JsonPropertyOrder({"id", "createdAt"})
public abstract class AbstractEntityRepr implements Representation {

    @JsonIgnore
    protected AbstractEntity entity;

    public AbstractEntityRepr(AbstractEntity entity) {
        this.entity = entity;
    }

    public AbstractEntity getEntity() {
        return entity;
    }

    public void setEntity(AbstractEntity entity) {
        this.entity = entity;
    }

    @JsonProperty("createdAt")
    @JsonView(Public.class)
    public String getCreatedAt() {
        if (entity == null) return null;
        return entity.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
    }

    @JsonProperty("updatedAt")
    public String getUpdatedAt() {
        if (entity == null || entity.getUpdatedAt() == null) return null;
        return entity.getUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
    }

    @JsonProperty
    @JsonView(Public.class)
    public Long getId() {
        return entity != null ? entity.getId() : null;
    }
}
