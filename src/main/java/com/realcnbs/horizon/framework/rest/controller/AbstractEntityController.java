package com.realcnbs.horizon.framework.rest.controller;

import com.realcnbs.horizon.framework.data.dao.entity.AbstractEntityDao;
import com.realcnbs.horizon.framework.data.entity.AbstractEntity;
import com.realcnbs.horizon.framework.data.filter.FieldFilter;
import com.realcnbs.horizon.framework.data.filter.FilterDefinition;
import com.realcnbs.horizon.framework.rest.form.definition.AbstractForm;
import com.realcnbs.horizon.framework.rest.form.processor.FormProcessor;
import com.realcnbs.horizon.framework.rest.repr.PagedEntityRepr;
import com.realcnbs.horizon.framework.rest.repr.RepresentationFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import com.realcnbs.horizon.framework.rest.RestRequestFilter;
import com.realcnbs.horizon.framework.rest.exception.InvalidFilterException;
import com.realcnbs.horizon.framework.rest.exception.NotFoundException;
import com.realcnbs.horizon.framework.rest.repr.Representation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public abstract class AbstractEntityController<T> {

    @Autowired
    protected RepresentationFactory reprFactory;

    protected abstract AbstractEntityDao getDao();

    protected abstract FormProcessor getFormProcessor();

    protected Representation getEntityReprById(Long id) {
        AbstractEntity entity = getDao().findById(id);
        if (entity != null) {
            return reprFactory.build(entity);
        }
        throw new NotFoundException("Entity not found");
    }

    protected AbstractEntity getEntityById(Long id) {
        AbstractEntity entity = getDao().findById(id);
        if (entity != null) {
            return entity;
        }
        throw new NotFoundException("Entity not found");
    }

    protected Page<T> getAllEntities(RestRequestFilter requestFilter) {
        if (requestFilter.getPageNum() == 0) {
            requestFilter.setPageNum(1);
        }

        List<FieldFilter> fieldFilters = buildFieldFilters(requestFilter);
        List<FieldFilter> fieldJoinFilters = buildFieldJoinFilters(requestFilter);

        if (fieldJoinFilters.size() > 0) {
            List<Long> ids = getDao().findJoinedFilteredIds(fieldFilters, fieldJoinFilters);
            return getDao().findByIds(requestFilter.getPageable(), ids);
        } else {
            return getDao().findAll(requestFilter.getPageable(), fieldFilters);
        }
    }

    protected List<FieldFilter> buildFieldFilters(RestRequestFilter requestFilter) {
        List<FieldFilter> fieldFilters = new ArrayList<>();
        List<FilterDefinition> allowedFilters = getDao().getAllowedFilters();
        buildAllowedFieldFilters(requestFilter, fieldFilters, allowedFilters);
        return fieldFilters;
    }

    protected void buildAllowedFieldFilters(
            RestRequestFilter requestFilter, List<FieldFilter> fieldFilters, List<FilterDefinition> allowedFilters
    ) {
        if (allowedFilters == null) {
            return;
        }
        if (requestFilter.getFilterMap() != null) {
            for (Map.Entry<String, String> filterEntry : requestFilter.getFilterMap().entrySet()) {
                for (FilterDefinition allowedFilter : allowedFilters) {
                    if (allowedFilter.getName().equals(filterEntry.getKey())) {
                        buildFieldFilters(fieldFilters, filterEntry, allowedFilter);
                    }
                }
            }
        }
    }

    protected List<FieldFilter> buildFieldJoinFilters(RestRequestFilter requestFilter) {
        List<FieldFilter> fieldFilters = new ArrayList<>();
        List<FilterDefinition> allowedFilters = getDao().getAllowedJoinFilters();
        buildAllowedFieldFilters(requestFilter, fieldFilters, allowedFilters);
        return fieldFilters;
    }

    protected void buildFieldFilters(
            List<FieldFilter> fieldFilters, Map.Entry<String, String> filterEntry, FilterDefinition allowedFilter
    ) {
        FieldFilter fieldFilter = new FieldFilter();
        fieldFilter.setField(allowedFilter.getField());
        fieldFilter.setCheckType(allowedFilter.getCheckType());
        fieldFilter.setLogicType(FieldFilter.LogicType.AND);

        if (filterEntry.getValue().equalsIgnoreCase("!NULL")) {
            fieldFilter.setCheckType(FieldFilter.CheckType.NOT_EQUALS);
            fieldFilter.setValue(null);
            fieldFilters.add(fieldFilter);
            return;
        }

        switch (allowedFilter.getDataType()) {
            case BOOLEAN:
                fieldFilter.setValue(Boolean.parseBoolean(filterEntry.getValue()));
                fieldFilters.add(fieldFilter);
                break;
            case NUMBER:
                if (allowedFilter.getCheckType() == FieldFilter.CheckType.IN || allowedFilter.getCheckType() == FieldFilter.CheckType.ARRAY_ANY) {
                    // try to cast to number or sequence on numbers
                    List<Long> numbers = new ArrayList<>();
                    if (filterEntry.getValue().contains(",")) {
                        for (String s : filterEntry.getValue().split(",")) {
                            if (StringUtils.isNumeric(s)) {
                                numbers.add(Long.valueOf(s));
                            } else {
                                throw new InvalidFilterException("Non numeric value supplied for NUMBER filter: " + s);
                            }
                        }
                        if (numbers.size() > 0) {
                            fieldFilter.setValue(numbers);
                            fieldFilters.add(fieldFilter);
                        }
                    } else {
                        if (StringUtils.isNumeric(filterEntry.getValue())) {
                            numbers.add(Long.valueOf(filterEntry.getValue()));
                            fieldFilter.setValue(numbers);
                            fieldFilters.add(fieldFilter);
                        }else{
                            throw new InvalidFilterException("Non numeric value supplied for NUMBER filter: " + filterEntry.getValue());
                        }
                    }
                } else {
                    if (StringUtils.isNumeric(filterEntry.getValue())) {
                        fieldFilter.setValue(Long.valueOf(filterEntry.getValue()));
                        fieldFilters.add(fieldFilter);
                    }
                }
                break;
            case DATE:
                // try to parse date
                try {
                    LocalDateTime from = LocalDateTime.parse(
                            filterEntry.getValue(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
                    fieldFilter.setValue(from);
                    fieldFilter.setCheckType(allowedFilter.getCheckType());
                    fieldFilters.add(fieldFilter);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
                break;
            case STRING:
                if (allowedFilter.getCheckType() == FieldFilter.CheckType.IN || allowedFilter.getCheckType() == FieldFilter.CheckType.ARRAY_ANY) {
                    fieldFilter.setValue(filterEntry.getValue().split(","));
                } else {
                    fieldFilter.setValue(filterEntry.getValue());
                }
                fieldFilters.add(fieldFilter);
                break;
        }
    }

    protected Representation getAllEntitiesReprs(RestRequestFilter filter) {
        Page<T> entities = this.getAllEntities(filter);
        return wrapEntities(entities, filter);
    }

    protected Representation wrapEntities(Page<T> entities, RestRequestFilter filter) {
        List<FilterDefinition> allowedFilters = getDao().getAllowedFilters();

        PagedEntityRepr representation = reprFactory.buildPaged(entities);
        representation.setFilters(allowedFilters);
        representation.setSorts(getDao().getAllowedSorts());

        representation.setAppliedSorts(filter.getSortMap());
        representation.setAppliedFilters(filter.getFilterMap());

        return representation;
    }

    protected List<Representation> getAllEntitiesReprs() {
        List<Representation> reprs = new ArrayList<>();
        List<AbstractEntity> entities = getDao().findAll();

        for (AbstractEntity entity : entities) {
            reprs.add(reprFactory.build(entity));
        }

        return reprs;
    }

    protected Representation createAndWrapEntity(AbstractForm form) {
        return reprFactory.build(createEntity(form));
    }

    protected AbstractEntity createEntity(AbstractForm form) {
        AbstractEntity entity = getFormProcessor().buildEntity(form);
        getDao().persist(entity);
        getFormProcessor().processRelations(entity, form);

        return entity;
    }

    protected Representation updateAndWrapEntity(Long id, AbstractForm form) {
        AbstractEntity entity = getDao().findById(id);
        return updateAndWrapEntity(entity, form);
    }

    protected Representation updateAndWrapEntity(AbstractEntity entity, AbstractForm form) {
        this.updateEntity(entity, form);
        return reprFactory.build(getDao().findById(entity.getId()));
    }

    protected void updateEntity(AbstractEntity entity, AbstractForm form) {
        if (entity == null) {
            throw new NotFoundException("Entity not found");
        }

        form.setId(entity.getId());

        getFormProcessor().processRelations(entity, form);
        getFormProcessor().updateEntity(entity, form);
        getDao().persist(entity);
    }

    protected AbstractEntity updateEntity(Long id, AbstractForm form) {
        AbstractEntity entity = getDao().findById(id);
        updateEntity(entity, form);
        return entity;
    }
}
