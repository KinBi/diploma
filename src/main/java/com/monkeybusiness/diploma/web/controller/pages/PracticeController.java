package com.monkeybusiness.diploma.web.controller.pages;

import com.monkeybusiness.diploma.core.practice.Practice;
import com.monkeybusiness.diploma.core.service.PracticeService;
import com.monkeybusiness.diploma.web.controller.dto.AbstractDto;
import com.monkeybusiness.diploma.web.controller.dto.MessageDto;
import com.monkeybusiness.diploma.web.controller.dto.PracticeDto;
import com.monkeybusiness.diploma.web.controller.dto.PracticesDto;
import com.monkeybusiness.diploma.web.controller.validation.PracticeWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@Controller
@RequestMapping("/practice")
public class PracticeController {
  @Autowired
  private PracticeService practiceService;

  @GetMapping("/getPractices")
  @ResponseBody
  public PracticesDto getPractices(HttpSession session) {
    List<Practice> practices = practiceService.getAllPractices();
    PracticesDto practicesDto = new PracticesDto();
    practicesDto.setPracticeDtos(practices.stream()
            .map(practice -> {
              PracticeDto practiceDto = new PracticeDto();
              practiceDto.setLocation(practice.getLocation());
              practiceDto.setStatus(practice.getStatus());
              practiceDto.setId(practice.getId());
              return practiceDto;
            }).collect(Collectors.toList()));

    addRoleAndId(practicesDto, session);
    return practicesDto;
  }

  @DeleteMapping("/deletePractice")
  @ResponseBody
  public MessageDto deletePractice(@RequestBody PracticeWrapper practiceWrapper, HttpSession session) {
    practiceService.delete(practiceWrapper.getId());
    MessageDto messageDto = new MessageDto();
    messageDto.setSuccessful(true);

    addRoleAndId(messageDto, session);
    return messageDto;
  }

  @PutMapping("/updatePractice")
  @ResponseBody
  public MessageDto updatePractice(@RequestBody PracticeWrapper practiceWrapper, HttpSession session) {
    Practice practice = createPractice(practiceWrapper);
    practiceService.update(practice);
    MessageDto messageDto = new MessageDto();
    messageDto.setSuccessful(true);

    addRoleAndId(messageDto, session);
    return messageDto;
  }

  @PostMapping("/addPractice")
  @ResponseBody
  public PracticeDto addPractice(@RequestBody PracticeWrapper practiceWrapper, HttpSession session) {
    Practice practice = createPractice(practiceWrapper);;
    practiceService.save(practice);
    PracticeDto practiceDto = new PracticeDto();
    practiceDto.setSuccessful(true);
    practiceDto.setId(practice.getId());

    addRoleAndId(practiceDto, session);
    return practiceDto;
  }

  private Practice createPractice(PracticeWrapper practiceWrapper){
    Practice practice = new Practice();
    practice.setLocation(practiceWrapper.getLocation());
    practice.setStatus(practiceWrapper.getStatus());
    practice.setId(practiceWrapper.getId());
    return practice;
  }
  
  private void addRoleAndId(AbstractDto dto, HttpSession session) {
    dto.setUser_role((String) session.getAttribute("user_role"));
    dto.setUser_id((Long) session.getAttribute("user_id"));
  }
}
