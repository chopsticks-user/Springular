package com.frost.springular.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.frost.springular.entity.CalendarEventEntity;
import com.frost.springular.entity.JwtRefreshTokenEntity;
import com.frost.springular.entity.UserEntity;

@Repository
public interface CalendarEventRepository
        extends CrudRepository<CalendarEventEntity, String> {
    Optional<JwtRefreshTokenEntity> findByUserEntity(UserEntity userEntity);
}
