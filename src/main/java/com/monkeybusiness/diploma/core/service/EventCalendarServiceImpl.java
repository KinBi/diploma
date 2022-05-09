package com.monkeybusiness.diploma.core.service;

import com.monkeybusiness.diploma.core.dao.EventCalendarDao;
import com.monkeybusiness.diploma.core.eventCalendar.EventCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventCalendarServiceImpl implements EventCalendarService {

  @Autowired
  private EventCalendarDao jdbcEventCalendarDao;

  @Override
  public EventCalendar getEventCalendar(Long id) {
    Optional<EventCalendar> optionalEventCalendar = jdbcEventCalendarDao.find(id);
    return optionalEventCalendar.orElseThrow(RuntimeException::new);
  }

  @Override
  public List<EventCalendar> getAllEventCalendars() {
    List<EventCalendar> eventCalendars = jdbcEventCalendarDao.findAll();
    return eventCalendars;
  }

  @Override
  public List<EventCalendar> getEventCalendarsByPracticeId(Long practiceId) {
    List<EventCalendar> eventCalendars = jdbcEventCalendarDao.findByPracticeId(practiceId);
    return eventCalendars;
  }

  @Override
  public void save(EventCalendar eventCalendar) {
    jdbcEventCalendarDao.save(eventCalendar);
  }

  @Override
  public void update(EventCalendar eventCalendar) {
    jdbcEventCalendarDao.update(eventCalendar);
  }

  @Override
  public void delete(Long id) {
    jdbcEventCalendarDao.delete(id);
  }
}
