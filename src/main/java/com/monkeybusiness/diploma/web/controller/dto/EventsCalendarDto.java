package com.monkeybusiness.diploma.web.controller.dto;

import java.util.List;

public class EventsCalendarDto extends AbstractDto {
  private List<EventCalendarDto> eventCalendars;

  public List<EventCalendarDto> getEventCalendars() {
    return eventCalendars;
  }

  public void setEventCalendars(List<EventCalendarDto> eventCalendars) {
    this.eventCalendars = eventCalendars;
  }
}
