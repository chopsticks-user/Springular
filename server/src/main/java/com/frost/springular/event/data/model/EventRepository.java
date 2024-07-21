package com.frost.springular.event.data.model;

import java.time.Instant;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.frost.springular.event.data.EventRepeat;
import com.frost.springular.user.data.model.UserModel;

@Repository
public interface EventRepository
    extends CrudRepository<EventModel, String> {
  List<EventModel> findByUserOrderByStart(UserModel user);

  List<EventModel> findByUserAndRepeatAndStartGreaterThanEqualAndStartLessThan(
      UserModel user,
      EventRepeat repeat,
      Instant startOfWeek,
      Instant endOfWeek);

  List<EventModel> findByUserAndRepeat(UserModel user, EventRepeat repeat);
}
