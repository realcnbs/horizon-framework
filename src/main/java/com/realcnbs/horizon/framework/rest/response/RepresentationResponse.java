package com.realcnbs.horizon.framework.rest.response;

import com.realcnbs.horizon.framework.rest.repr.Representation;

public class RepresentationResponse extends GenericResponse {

    private Representation representation;

    public RepresentationResponse(Representation representation) {
        this.representation = representation;
    }

    public Representation getRepresentation() {
        return representation;
    }

    public void setRepresentation(Representation representation) {
        this.representation = representation;
    }
}
