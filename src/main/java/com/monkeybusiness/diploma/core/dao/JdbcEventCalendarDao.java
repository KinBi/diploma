package com.monkeybusiness.diploma.core.dao;

import com.monkeybusiness.diploma.core.eventCalendar.EventCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Component
public class JdbcEventCalendarDao implements EventCalendarDao {
  public static final String SELECT_EVENT_CALENDAR_BY_ID = "SELECT * FROM eventCalendar WHERE id = ?";
  public static final String SELECT_ALL_EVENT_CALENDARS = "SELECT * FROM eventCalendar";
  public static final String SELECT_EVENT_CALENDARS_BY_PRACTICE_ID = "SELECT * FROM eventCalendar WHERE practiceId = ?";
  public static final String UPDATE_EVENT_CALENDAR = "UPDATE eventCalendar SET startAt = ?, endAt = ?, timezoneStartAt = ?, " +
          "summary = ?, color = ?, calendarId = ?, userId = ?, practiceId = ? WHERE id = ?";
  public static final String DELETE_EVENT_CALENDAR = "DELETE FROM eventCalendar WHERE id = ?";
  public static final String EVENT_CALENDAR_TABLE = "eventCalendar";
  public static final String ID_COLUMN = "id";

  @Autowired
  private JdbcTemplate jdbcTemplate;

  private SimpleJdbcInsert simpleJdbcInsert;

  @PostConstruct
  private void init() {
    simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName(EVENT_CALENDAR_TABLE)
            .usingGeneratedKeyColumns(ID_COLUMN);
  }

  @Override
  public Optional<EventCalendar> find(Long id) {
    Optional<EventCalendar> optionalEventCalendar;
    List<EventCalendar> eventCalendarList = jdbcTemplate.query(SELECT_EVENT_CALENDAR_BY_ID,
            new BeanPropertyRowMapper<>(EventCalendar.class), id);
    optionalEventCalendar = eventCalendarList.isEmpty() ? Optional.empty() : Optional.ofNullable(eventCalendarList.get(0));
    return optionalEventCalendar;
  }

  @Override
  public List<EventCalendar> findAll() {
    List<EventCalendar> eventCalendars;
    eventCalendars = jdbcTemplate.query(SELECT_EVENT_CALENDARS_BY_PRACTICE_ID, new BeanPropertyRowMapper<>(EventCalendar.class));
    return eventCalendars;
  }

  @Override
  public List<EventCalendar> findByPracticeId(Long practiceId) {
    List<EventCalendar> documentList = jdbcTemplate.query(SELECT_EVENT_CALENDARS_BY_PRACTICE_ID,
            new BeanPropertyRowMapper<>(EventCalendar.class), practiceId);
    return documentList;
  }

  @Override
  public void save(EventCalendar eventCalendar) {
    SqlParameterSource source = new BeanPropertySqlParameterSource(eventCalendar);
    Long id = (Long) simpleJdbcInsert.executeAndReturnKey(source);
    eventCalendar.setId(id);
  }

  @Override
  public void update(EventCalendar eventCalendar) {
    jdbcTemplate.update(UPDATE_EVENT_CALENDAR, eventCalendar.getStartAt(), eventCalendar.getEndAt(),
            eventCalendar.getTimezoneStartAt(), eventCalendar.getSummary(), eventCalendar.getColor(),
            eventCalendar.getCalendarId(), eventCalendar.getUserId(), eventCalendar.getPracticeId(), eventCalendar.getId());
  }

  @Override
  public void delete(Long id) {
    jdbcTemplate.update(DELETE_EVENT_CALENDAR, id);
  }
}
