package com.monkeybusiness.diploma.web.controller.pages;

import com.monkeybusiness.diploma.core.eventCalendar.EventCalendar;
import com.monkeybusiness.diploma.core.service.EventCalendarService;
import com.monkeybusiness.diploma.web.controller.dto.AbstractDto;
import com.monkeybusiness.diploma.web.controller.dto.EventCalendarDto;
import com.monkeybusiness.diploma.web.controller.dto.EventsCalendarDto;
import com.monkeybusiness.diploma.web.controller.dto.MessageDto;
import com.monkeybusiness.diploma.web.controller.validation.EventCalendarFullWrapper;
import com.monkeybusiness.diploma.web.controller.validation.EventCalendarWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@Controller
@RequestMapping("/eventCalendar")
public class EventCalendarController {
  @Autowired
  private EventCalendarService eventCalendarService;

  @GetMapping("/getEventsByPracticeIds")
  @ResponseBody
  public EventsCalendarDto getEventsByPracticeId(@RequestParam Long practiceId, HttpSession session) {
    List<EventCalendar> eventCalendars = eventCalendarService.getEventCalendarsByPracticeId(practiceId);
    EventsCalendarDto eventsCalendarDto = new EventsCalendarDto();
    eventsCalendarDto.setEventCalendars(eventCalendars.stream()
            .map(eventCalendar -> {
              EventCalendarDto eventCalendarDto = new EventCalendarDto();
              eventCalendarDto.setId(eventCalendar.getId());
              eventCalendarDto.setStartAt(eventCalendar.getStartAt());
              eventCalendarDto.setEndAt(eventCalendar.getEndAt());
              eventCalendarDto.setTimezoneStartAt(eventCalendar.getTimezoneStartAt());
              eventCalendarDto.setSummary(eventCalendar.getSummary());
              eventCalendarDto.setColor(eventCalendar.getColor());
              eventCalendarDto.setCalendarId(eventCalendar.getCalendarId());
              eventCalendarDto.setUserId(eventCalendar.getUserId());
              eventCalendarDto.setPracticeId(eventCalendar.getPracticeId());
              return eventCalendarDto;
            }).collect(Collectors.toList()));
    addRoleAndId(eventsCalendarDto, session);
    return eventsCalendarDto;
  }

  @DeleteMapping("/deleteEvent")
  @ResponseBody
  public MessageDto deleteEvent(@RequestBody EventCalendarWrapper eventCalendarWrapper, HttpSession session) {
    eventCalendarService.delete(eventCalendarWrapper.getEventCalendarId());
    MessageDto messageDto = new MessageDto();
    messageDto.setSuccessful(true);
    addRoleAndId(messageDto, session);
    return messageDto;
  }

  @PutMapping("/updateEvent")
  @ResponseBody
  public MessageDto updateEvent(@RequestBody EventCalendarFullWrapper eventCalendarFullWrapper, HttpSession session) {
    EventCalendar eventCalendar = createEventCalendar(eventCalendarFullWrapper);
    eventCalendarService.update(eventCalendar);
    MessageDto messageDto = new MessageDto();
    messageDto.setSuccessful(true);
    addRoleAndId(messageDto, session);
    return messageDto;
  }

  @PostMapping("/addEvent")
  @ResponseBody
  public EventCalendarDto addEvent(@RequestBody EventCalendarFullWrapper eventCalendarFullWrapper, HttpSession session) {
    EventCalendar eventCalendar = createEventCalendar(eventCalendarFullWrapper);;
    eventCalendarService.save(eventCalendar);
    EventCalendarDto eventCalendarDto = new EventCalendarDto();
    eventCalendarDto.setSuccessful(true);

    addRoleAndId(eventCalendarDto, session);
    return eventCalendarDto;
  }

  private EventCalendar createEventCalendar(EventCalendarFullWrapper eventCalendarFullWrapper){
    EventCalendar eventCalendar = new EventCalendar();
    eventCalendar.setId(eventCalendarFullWrapper.getId());
    eventCalendar.setStartAt(eventCalendarFullWrapper.getStartAt());
    eventCalendar.setEndAt(eventCalendarFullWrapper.getEndAt());
    eventCalendar.setTimezoneStartAt(eventCalendarFullWrapper.getTimezoneStartAt());
    eventCalendar.setSummary(eventCalendarFullWrapper.getSummary());
    eventCalendar.setColor(eventCalendarFullWrapper.getColor());
    eventCalendar.setCalendarId(eventCalendarFullWrapper.getCalendarId());
    eventCalendar.setUserId(eventCalendarFullWrapper.getUserId());
    eventCalendar.setPracticeId(eventCalendarFullWrapper.getPracticeId());
    return eventCalendar;
  }

  private void addRoleAndId(AbstractDto dto, HttpSession session) {
    dto.setUser_role((String) session.getAttribute("user_role"));
    dto.setUser_id((Long) session.getAttribute("user_id"));
  }
}
