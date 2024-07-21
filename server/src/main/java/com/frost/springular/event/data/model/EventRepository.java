package com.frost.springular.event.data.model;

import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.frost.springular.event.data.EventRepeat;
import com.frost.springular.user.data.model.UserModel;

@Repository
public interface EventRepository
    extends CrudRepository<EventModel, String> {
  List<EventModel> findByUserEntity(UserModel userEntity);

  @Query(value = """
      SELECT e FROM EventModel e
        WHERE e.userEntity = :user
        AND e.repeat = none
        AND e.start >= :startOfWeek
        AND e.start < :endOfWeek
      """)
  List<EventModel> filterOneTimeEventsBetween(
      @Param("user") UserModel userEntity,
      @Param("startOfWeek") Instant startOfWeek,
      @Param("endOfWeek") Instant endOfWeek);

  @Query(value = """
      SELECT e FROM EventModel e
        WHERE e.userEntity = :user
        AND e.repeat = :repeat
      """)
  List<EventModel> filterEventsByRepeatType(
      @Param("user") UserModel userEntity,
      @Param("repeat") EventRepeat repeat);
}
