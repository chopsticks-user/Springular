package com.frost.springular.object.repository;

import com.frost.springular.object.model.CalendarEventModel;
import com.frost.springular.object.model.RefreshTokenModel;
import com.frost.springular.object.model.UserModel;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarEventRepository
    extends CrudRepository<CalendarEventModel, String> {
  Optional<List<CalendarEventModel>> findByUserEntity(UserModel userEntity);

  @Query(value ="""
          SELECT e FROM CalendarEventModel e 
            WHERE e.repeat = none
            AND e.start >= :startOfWeek 
            AND e.start < :endOfWeek
          """)
  List<CalendarEventModel>
  filterOneTimeEventsBetween(
    @Param("startOfWeek") Instant startOfWeek, 
    @Param("endOfWeek") Instant endOfWeek
  );
}
