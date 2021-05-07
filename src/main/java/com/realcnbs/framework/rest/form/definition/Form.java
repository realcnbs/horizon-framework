package com.realcnbs.framework.rest.form.definition;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public interface Form {

    Long getId();

    void setId(Long id);
}
