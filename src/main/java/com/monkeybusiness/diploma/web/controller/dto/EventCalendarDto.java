package com.monkeybusiness.diploma.web.controller.dto;

public class EventCalendarDto extends MessageDto {
  private Long id;
  private String startAt;
  private String endAt;
  private String timezoneStartAt;
  private String summary;
  private String color;
  private String calendarId;
  private Long userId;
  private Long practiceId;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getStartAt() {
    return startAt;
  }

  public void setStartAt(String startAt) {
    this.startAt = startAt;
  }

  public String getEndAt() {
    return endAt;
  }

  public void setEndAt(String endAt) {
    this.endAt = endAt;
  }

  public String getTimezoneStartAt() {
    return timezoneStartAt;
  }

  public void setTimezoneStartAt(String timezoneStartAt) {
    this.timezoneStartAt = timezoneStartAt;
  }

  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public String getCalendarId() {
    return calendarId;
  }

  public void setCalendarId(String calendarId) {
    this.calendarId = calendarId;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getPracticeId() {
    return practiceId;
  }

  public void setPracticeId(Long practiceId) {
    this.practiceId = practiceId;
  }
}
