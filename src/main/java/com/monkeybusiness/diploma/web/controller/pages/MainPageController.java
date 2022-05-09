package com.monkeybusiness.diploma.web.controller.pages;

import com.monkeybusiness.diploma.core.service.PracticeService;
import com.monkeybusiness.diploma.core.service.UserService;
import com.monkeybusiness.diploma.core.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@CrossOrigin
@Controller
@RequestMapping("/main")
public class MainPageController {
  public static final String MAIN_PAGE = "main";
  public static final String USER_ID_SESSION_ATTRIBUTE = "userId";
  public static final String PRACTICE_ATTRIBUTE = "practice";
  public static final String USER_ATTRIBUTE = "user";

  @Autowired
  private PracticeService practiceService;

  @Autowired
  private UserService userService;

  @GetMapping
  public String getInfo(HttpSession session, Model model) throws Exception {
    User user = userService.getUser((Long) session.getAttribute(USER_ID_SESSION_ATTRIBUTE));
    model.addAttribute(USER_ATTRIBUTE, user);
    model.addAttribute(PRACTICE_ATTRIBUTE, practiceService.getPractice(user.getPracticeId()));
    return MAIN_PAGE;
  }
}
