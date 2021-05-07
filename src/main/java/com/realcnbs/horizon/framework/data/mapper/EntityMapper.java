package com.realcnbs.horizon.framework.data.mapper;

import com.realcnbs.horizon.framework.data.filter.FilterBuilder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EntityMapper<T> {

    void insert(T entity);

    void delete(T entity);

    void update(T entity);

    List<T> findAll();

    Integer countAll();

    T findOneFiltered(@Param("filter") FilterBuilder filter);

    List<T> findAllFiltered(@Param("filter") FilterBuilder filter);

    Integer countAllFiltered(@Param("filter") FilterBuilder filter);

    List<Long> findJoinedFilteredIds(@Param("filter") FilterBuilder filter);
}
