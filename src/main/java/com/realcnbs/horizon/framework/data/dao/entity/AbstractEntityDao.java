package com.realcnbs.horizon.framework.data.dao.entity;

import com.realcnbs.horizon.framework.data.entity.AbstractEntity;
import com.realcnbs.horizon.framework.data.filter.*;
import com.realcnbs.horizon.framework.data.mapper.EntityMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractEntityDao<T extends AbstractEntity> {

    protected abstract EntityMapper<T> getMapper();

    public void persist(T entity) {
        if (entity.getId() != null) {
            entity.setUpdatedAt(LocalDateTime.now());
            getMapper().update(entity);
        } else {
            if (entity.getCreatedAt() == null) {
                entity.setCreatedAt(LocalDateTime.now());
            }
            getMapper().insert(entity);
        }
    }

    public void persist(List<T> entities) {
        List<T> toUpdate = new ArrayList<>();
        List<T> toInsert = new ArrayList<>();

        for (T entity : entities) {
            if (entity.getId() != null) {
                entity.setUpdatedAt(LocalDateTime.now());
                toUpdate.add(entity);
            } else {
                if (entity.getCreatedAt() == null) {
                    entity.setCreatedAt(LocalDateTime.now());
                }
                toInsert.add(entity);
            }
        }

        if (toUpdate.size() > 0) {
            for (T t : toUpdate) {
                getMapper().update(t);
            }
        }

        if (toInsert.size() > 0) {
            for (T t : toInsert) {
                getMapper().insert(t);
            }
        }
    }

    public void remove(T entity) {
        getMapper().delete(entity);
    }

    public T findById(Long id) {
        FilterBuilder builder = new FilterBuilder();
        builder.and("id").eq(id);
        return getMapper().findOneFiltered(builder);
    }

    public T findByIdFiltered(Long id, List<FieldFilter> filters) {
        FilterBuilder builder = new FilterBuilder();
        builder.setFieldFilters(filters);
        builder.and("id").eq(id);
        return getMapper().findOneFiltered(builder);
    }

    public List<T> findByIds(List<Long> ids) {
        if (ids != null && ids.size() > 0) {
            FilterBuilder builder = new FilterBuilder();
            builder.and("id").in(ids);
            return getMapper().findAllFiltered(builder);
        }
        return new ArrayList<>();
    }

    public List<T> findByIdsFiltered(List<Long> ids, List<FieldFilter> filters) {
        if (ids != null && ids.size() > 0) {
            FilterBuilder builder = new FilterBuilder();
            builder.setFieldFilters(filters);
            builder.and("id").in(ids);
            return getMapper().findAllFiltered(builder);
        }
        return new ArrayList<>();
    }

    public Page<T> findByIds(Pageable pageable, List<Long> ids) {
        EntityMapper<T> mapper = getMapper();
        int total = ids.size();

        if (total == 0) {
            return new PageImpl<>(Collections.emptyList());
        }
        if (total < pageable.getOffset()) {
            throw new PageOutOfBoundsException("Page " + pageable.getPageNumber() + " doesn't exist");
        }

        FilterBuilder builder = new FilterBuilder();
        builder.limit(Math.toIntExact(pageable.getOffset()), pageable.getPageSize());
        builder.and("id").in(ids);
        applySort(pageable, builder);
        List<T> content = mapper.findAllFiltered(builder);

        return new PageImpl<>(content, pageable, total);
    }

    public List<T> findAll() {
        return getMapper().findAll();
    }

    public Page<T> findAll(Pageable pageable) throws PageOutOfBoundsException {
        EntityMapper<T> mapper = getMapper();
        Integer total = mapper.countAll();

        if (total == 0) {
            return new PageImpl<>(Collections.emptyList());
        }

        if (total < pageable.getOffset()) {
            throw new PageOutOfBoundsException("Page " + pageable.getPageNumber() + " doesn't exist");
        }

        FilterBuilder builder = new FilterBuilder();
        builder.limit(Math.toIntExact(pageable.getOffset()), pageable.getPageSize());
        applySort(pageable, builder);
        List<T> content = mapper.findAllFiltered(builder);

        return new PageImpl<>(content, pageable, total);
    }

    public void applySort(Pageable pageable, FilterBuilder builder) {
        if (pageable.getSort() != null) {
            if (pageable.getSort().isUnsorted()) {
                builder.sort("id").asc();
            } else {
                for (Sort.Order order : pageable.getSort()) {
                    for (SortDefinition sortDefinition : getAllowedSorts()) {
                        if (sortDefinition.getName().equals(order.getProperty())) {
                            switch (order.getDirection()) {
                                case ASC:
                                    builder.sort(sortDefinition.getField()).asc();
                                case DESC:
                                    builder.sort(sortDefinition.getField()).desc();
                            }
                        }
                    }
                }
            }
        }
    }

    public Page<T> findAll(Pageable pageable, List<FieldFilter> filters) throws PageOutOfBoundsException {
        FilterBuilder builder = new FilterBuilder();
        builder.setFieldFilters(filters);

        EntityMapper<T> mapper = getMapper();
        Integer total = mapper.countAllFiltered(builder);

        if (total == 0) {
            return new PageImpl<>(Collections.emptyList());
        }

        if (total < pageable.getOffset()) {
            throw new PageOutOfBoundsException("Page " + pageable.getPageNumber() + " doesn't exist");
        }

        builder.limit(Math.toIntExact(pageable.getOffset()), pageable.getPageSize());
        applySort(pageable, builder);
        List<T> content = mapper.findAllFiltered(builder);

        return new PageImpl<>(content, pageable, total);
    }

    public Page<T> findAll(Pageable pageable, FieldFilter filter) throws PageOutOfBoundsException {
        return findAll(pageable, Collections.singletonList(filter));
    }

    public int countAll() {
        return getMapper().countAll();
    }

    public int countAllFiltered(List<FieldFilter> filters) {
        FilterBuilder builder = new FilterBuilder();
        builder.setFieldFilters(filters);
        return getMapper().countAllFiltered(builder);
    }

    public List<FilterDefinition> getAllowedFilters() {
        List<FilterDefinition> filters = new ArrayList<>();
        filters.add(new FilterDefinition("id", FieldFilter.CheckType.EQUALS, FilterDefinition.DataType.NUMBER));
        filters.add(new FilterDefinition("createdAt", FieldFilter.CheckType.EQUALS, FilterDefinition.DataType.DATE));
        return filters;
    }

    public List<FilterDefinition> getAllowedJoinFilters() {
        return null;
    }

    public List<SortDefinition> getAllowedSorts() {
        List<SortDefinition> sorts = new ArrayList<>();
        sorts.add(new SortDefinition("id"));
        sorts.add(new SortDefinition("createdAt"));
        return sorts;
    }

    public List<Long> findJoinedFilteredIds(List<FieldFilter> filters, List<FieldFilter> joinFilters) {
        FilterBuilder builder = new FilterBuilder();
        builder.setFieldFilters(filters);
        builder.setJoinFieldFilters(joinFilters);
        EntityMapper<T> mapper = getMapper();
        return mapper.findJoinedFilteredIds(builder);
    }

    public List<T> findAllFiltered(FilterBuilder builder) {
        EntityMapper<T> mapper = getMapper();
        return mapper.findAllFiltered(builder);
    }

}
