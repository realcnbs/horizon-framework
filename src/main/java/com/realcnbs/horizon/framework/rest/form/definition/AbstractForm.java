package com.realcnbs.horizon.framework.rest.form.definition;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.realcnbs.horizon.framework.rest.controller.view.HorizonPublic;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AbstractForm {

    @JsonProperty
    @JsonView(HorizonPublic.class)
    protected Long id;

}
