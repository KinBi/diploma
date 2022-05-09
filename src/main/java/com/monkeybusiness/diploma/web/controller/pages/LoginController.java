package com.monkeybusiness.diploma.web.controller.pages;

import com.monkeybusiness.diploma.core.service.UserService;
import com.monkeybusiness.diploma.core.user.User;
import com.monkeybusiness.diploma.web.controller.dto.AbstractDto;
import com.monkeybusiness.diploma.web.controller.dto.MessageDto;
import com.monkeybusiness.diploma.web.controller.validation.UserWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.stream.Collectors;

@CrossOrigin
@Controller
@RequestMapping("/login")
public class LoginController {
  public static final String DELIMITER = "\n";
  public static final String BAD_PASSWORD_MESSAGE = "Bad password";
  public static final String USER_ID_SESSION_ATTRIBUTE = "user_id";
  public static final String USER_ROLE_SESSION_ATTRIBUTE = "user_role";

  @Autowired
  private UserService userService;

  @GetMapping
  public void la() {
    // todo
  }

  @PostMapping
  @ResponseBody
  public MessageDto login(@RequestBody UserWrapper userWrapper, BindingResult bindingResult, HttpSession session) {
    boolean successful = true;
    if (bindingResult.hasErrors()) {
      successful = false;
    } else {
      User validUser = userService.getUserByLogin(userWrapper.getLogin());
      if (!validUser.getPassword().equals(userWrapper.getPassword())) {
        successful = false;
        bindingResult.addError(new ObjectError(User.class.toString(), BAD_PASSWORD_MESSAGE));
      } else {
        session.setAttribute(USER_ID_SESSION_ATTRIBUTE, validUser.getId());
        session.setAttribute(USER_ROLE_SESSION_ATTRIBUTE, validUser.getRole());
      }
    }
    return createLoginDto(successful, bindingResult, session);
  }

  private MessageDto createLoginDto(boolean successful, BindingResult bindingResult, HttpSession session) {
    MessageDto messageDto = new MessageDto();
    messageDto.setSuccessful(successful);
    if (!successful) {
      messageDto.setMessage(bindingResult.getAllErrors().stream()
              .map(DefaultMessageSourceResolvable::getDefaultMessage)
              .collect(Collectors.joining(DELIMITER)));
    }

    addRoleAndId(messageDto, session);
    return messageDto;
  }

  private void addRoleAndId(AbstractDto dto, HttpSession session) {
    dto.setUser_role((String) session.getAttribute("user_role"));
    dto.setUser_id((Long) session.getAttribute("user_id"));
  }
}
