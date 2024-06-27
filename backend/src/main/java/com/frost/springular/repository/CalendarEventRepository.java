package com.frost.springular.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.frost.springular.entity.CalendarEventEntity;

@Repository
public interface CalendarEventRepository
        extends CrudRepository<CalendarEventEntity, String> {
}
