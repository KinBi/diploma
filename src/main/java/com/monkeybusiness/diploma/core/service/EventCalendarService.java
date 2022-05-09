package com.monkeybusiness.diploma.core.service;

import com.monkeybusiness.diploma.core.eventCalendar.EventCalendar;

import java.util.List;

public interface EventCalendarService {
  EventCalendar getEventCalendar(Long id);

  List<EventCalendar> getAllEventCalendars();

  List<EventCalendar> getEventCalendarsByPracticeId(Long practiceId);

  void save(EventCalendar document);

  void update(EventCalendar document);

  void delete(Long id);
}
