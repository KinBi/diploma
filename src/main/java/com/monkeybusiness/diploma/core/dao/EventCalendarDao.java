package com.monkeybusiness.diploma.core.dao;

import com.monkeybusiness.diploma.core.eventCalendar.EventCalendar;

import java.util.List;
import java.util.Optional;

public interface EventCalendarDao {
  Optional<EventCalendar> find(Long id);

  List<EventCalendar> findAll();

  List<EventCalendar> findByPracticeId(Long practiceId);

  void save(EventCalendar eventCalendar);

  void update(EventCalendar eventCalendar);

  void delete(Long id);
}
