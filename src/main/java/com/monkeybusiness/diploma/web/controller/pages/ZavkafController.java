package com.monkeybusiness.diploma.web.controller.pages;

import com.monkeybusiness.diploma.core.practice.Practice;
import com.monkeybusiness.diploma.core.service.PracticeService;
import com.monkeybusiness.diploma.core.service.UserService;
import com.monkeybusiness.diploma.core.user.Roles;
import com.monkeybusiness.diploma.core.user.User;
import com.monkeybusiness.diploma.web.controller.dto.MessageDto;
import com.monkeybusiness.diploma.web.controller.validation.UserFullWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@Controller
@RequestMapping("/zavkaf")
public class ZavkafController {
  public static final String DELIMITER = "\n";
  public static final String ADD_SUCCESS_MESSAGE = "Added successfully";
  public static final String UPDATE_SUCCESS_MESSAGE = "Updated successfully";
  public static final String DELETE_SUCCESS_MESSAGE = "Deleted successfully";

  @Autowired
  private PracticeService practiceService;

  @Autowired
  private UserService userService;

  @GetMapping("/practices")
  @ResponseBody
  public List<Practice> getPractices() {
    return practiceService.getAllPractices();
  }



//  @DeleteMapping
//  @ResponseBody
//  public MessageDto deletePractice(@RequestBody @Valid IdWrapper idWrapper, BindingResult bindingResult) {
//    boolean successful = true;
//    if (bindingResult.hasErrors()) {
//      successful = false;
//    } else {
//      practiceService.delete(idWrapper.getId());
//    }
//    return createHeadDto(successful, bindingResult, DELETE_SUCCESS_MESSAGE);
//  }

//  @PutMapping
//  @ResponseBody
//  public MessageDto updatePractice(@RequestBody @Valid PracticeWrapper practiceWrapper, BindingResult bindingResult) {
//    boolean successful = true;
//    if (bindingResult.hasErrors()) {
//      successful = false;
//    } else {
//      Practice practice = new Practice();
//      practice.setId(practiceWrapper.getId());
//      practice.setPracticeDateStart(practiceWrapper.getPracticeDateStart());
//      practice.setPracticeDateEnd(practiceWrapper.getPracticeDateEnd());
//      practice.setLocation(practiceWrapper.getLocation());
//      practice.setStatus(practiceWrapper.getStatus());
//      practiceService.update(practice);
//    }
//    return createHeadDto(successful, bindingResult, UPDATE_SUCCESS_MESSAGE);
//  }

//  @PostMapping
//  @ResponseBody
//  public MessageDto addPractice(@RequestBody @Valid PracticeWrapper practiceWrapper, BindingResult bindingResult) {
//    boolean successful = true;
//    if (bindingResult.hasErrors()) {
//      successful = false;
//    } else {
//      Practice practice = new Practice();
//      practice.setId(practiceWrapper.getId());
//      practice.setPracticeDateStart(practiceWrapper.getPracticeDateStart());
//      practice.setPracticeDateEnd(practiceWrapper.getPracticeDateEnd());
//      practice.setLocation(practiceWrapper.getLocation());
//      practice.setStatus(practiceWrapper.getStatus());
//      practiceService.update(practice);
//    }
//    return createHeadDto(successful, bindingResult, ADD_SUCCESS_MESSAGE);
//  }

  @PutMapping
  @ResponseBody
  public MessageDto updateUser(@RequestBody UserFullWrapper userFullWrapper, BindingResult bindingResult) {
    boolean successful = true;
    if (bindingResult.hasErrors()) {
      successful = false;
    } else {
      User user = new User();
      user.setName(userFullWrapper.getName());
      user.setSurname(userFullWrapper.getSurname());
      user.setMiddleName(userFullWrapper.getMiddleName());
      user.setLogin(userFullWrapper.getLogin());
      user.setPassword(userFullWrapper.getPassword());
      user.setRole(Roles.STUDENT.name());
      userService.update(user);
    }
    return createHeadDto(successful, bindingResult, UPDATE_SUCCESS_MESSAGE);
  }

  private MessageDto createHeadDto(boolean successful, BindingResult bindingResult, String successMessage) {
    MessageDto MessageDto = new MessageDto();
    MessageDto.setSuccessful(successful);
    String message;
    if (!successful) {
      message = bindingResult.getAllErrors().stream()
              .map(DefaultMessageSourceResolvable::getDefaultMessage)
              .collect(Collectors.joining(DELIMITER));
    } else {
      message = successMessage;
    }
    MessageDto.setMessage(message);
    return MessageDto;
  }
}
