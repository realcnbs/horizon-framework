package com.realcnbs.horizon.framework.rest.repr;

import com.realcnbs.horizon.framework.data.entity.AbstractEntity;
import com.realcnbs.horizon.framework.rest.exception.NoSuchReprException;
import org.springframework.data.domain.Page;

public interface RepresentationFactory {

    PagedEntityRepr buildPaged(Page page) throws NoSuchReprException;

    Representation build(AbstractEntity entity) throws NoSuchReprException;
}
