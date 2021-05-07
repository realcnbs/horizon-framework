package com.realcnbs.framework.rest.repr;

import com.realcnbs.framework.data.entity.AbstractEntity;
import com.realcnbs.framework.rest.exception.NoSuchReprException;
import org.springframework.data.domain.Page;

public interface RepresentationFactory {

    PagedEntityRepr buildPaged(Page page) throws NoSuchReprException;

    Representation build(AbstractEntity entity) throws NoSuchReprException;
}
