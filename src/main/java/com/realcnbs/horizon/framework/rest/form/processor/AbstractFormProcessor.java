package com.realcnbs.horizon.framework.rest.form.processor;

import com.realcnbs.horizon.framework.data.entity.AbstractEntity;
import com.realcnbs.horizon.framework.rest.exception.FormException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.validation.DataBinder;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractFormProcessor {

    protected Logger log = LoggerFactory.getLogger(AbstractFormProcessor.class);

    protected void bindNewData(AbstractEntity toUpdate, AbstractEntity newEntity, String... disallowedFields) {
        if (toUpdate.getClass() != newEntity.getClass()) {
            throw new FormException(
                    "Cannot bind " + toUpdate.getClass().getSimpleName() + " to" + newEntity.getClass().getSimpleName());
        }
        BeanWrapper wrapper = new BeanWrapperImpl(newEntity);
        MutablePropertyValues values = new MutablePropertyValues();
        for (PropertyDescriptor descriptor : wrapper.getPropertyDescriptors()) {
            if (wrapper.isReadableProperty(descriptor.getName())) {
                values.add(descriptor.getName(), wrapper.getPropertyValue(descriptor.getName()));
            }
        }
        DataBinder dataBinder = new DataBinder(toUpdate);
        if (disallowedFields != null) {
            List<String> strings = new ArrayList<>(Arrays.asList(disallowedFields));
            strings.add("id");
            strings.add("createdAt");
            dataBinder.setDisallowedFields(strings.toArray(new String[0]));
        } else {
            dataBinder.setDisallowedFields("id", "createdAt");
        }
        dataBinder.bind(values);
    }

    protected List<AbstractEntity> relationsToDelete(List<? extends AbstractEntity> existing, List<Long> formIds) {
        List<AbstractEntity> toDelete = new ArrayList<>();
        if (existing != null && formIds != null) {
            toDelete = existing.stream().filter(
                    x -> formIds.stream().noneMatch(y -> y.equals(x.getId()))).collect(
                    Collectors.toList());
        }
        return toDelete;
    }

    protected List<Long> relationIdsToAdd(List<? extends AbstractEntity> existing, List<Long> formIds) {
        List<Long> addIds = new ArrayList<>();
        if (existing != null && formIds != null) {
            addIds = formIds.stream().filter(
                    x -> existing.stream().noneMatch(y -> y.getId().equals(x))).collect(Collectors.toList());
        }
        if (existing == null && formIds != null) {
            addIds = formIds;
        }
        return addIds;
    }

    protected void processRelations(List<AbstractEntity> existing, List<Long> formIds) {
        if (existing != null && formIds != null) {
            // find relations to remove
            List<AbstractEntity> toDelete = existing.stream().filter(
                    x -> formIds.stream().noneMatch(y -> y.equals(x.getId()))).collect(
                    Collectors.toList());
            // find relations to add
            List<Long> addIds = formIds.stream().filter(
                    x -> existing.stream().noneMatch(y -> y.getId().equals(x))).collect(Collectors.toList());
        }
    }
}
