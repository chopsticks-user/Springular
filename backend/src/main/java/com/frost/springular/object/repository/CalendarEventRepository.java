package com.frost.springular.object.repository;

import com.frost.springular.object.model.CalendarEventModel;
import com.frost.springular.object.model.RefreshTokenModel;
import com.frost.springular.object.model.UserModel;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarEventRepository
    extends CrudRepository<CalendarEventModel, String> {
  Optional<RefreshTokenModel> findByUserEntity(UserModel userEntity);
}
