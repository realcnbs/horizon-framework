package com.realcnbs.framework.rest.form.processor;

import com.realcnbs.framework.FrameworkException;
import com.realcnbs.framework.data.entity.AbstractEntity;

public interface FormProcessor<T extends AbstractEntity, F> {

    T buildEntity(F form) throws FrameworkException;

    void updateEntity(T toUpdate, F form) throws FrameworkException;

    void processRelations(T entity, F form);
}
