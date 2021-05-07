package com.realcnbs.horizon.framework.rest.form.processor;

import com.realcnbs.horizon.framework.FrameworkException;
import com.realcnbs.horizon.framework.data.entity.AbstractEntity;

public interface FormProcessor<T extends AbstractEntity, F> {

    T buildEntity(F form) throws FrameworkException;

    void updateEntity(T toUpdate, F form) throws FrameworkException;

    void processRelations(T entity, F form);
}
