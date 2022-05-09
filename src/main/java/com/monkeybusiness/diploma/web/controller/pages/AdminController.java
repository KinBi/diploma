package com.monkeybusiness.diploma.web.controller.pages;

import com.monkeybusiness.diploma.core.practice.Practice;
import com.monkeybusiness.diploma.core.service.GroupService;
import com.monkeybusiness.diploma.core.service.PracticeService;
import com.monkeybusiness.diploma.core.service.UserService;
import com.monkeybusiness.diploma.core.user.Group;
import com.monkeybusiness.diploma.core.user.Roles;
import com.monkeybusiness.diploma.core.user.User;
import com.monkeybusiness.diploma.web.controller.dto.AbstractDto;
import com.monkeybusiness.diploma.web.controller.dto.GroupDto;
import com.monkeybusiness.diploma.web.controller.dto.MessageDto;
import com.monkeybusiness.diploma.web.controller.dto.UserDto;
import com.monkeybusiness.diploma.web.controller.dto.UsersDto;
import com.monkeybusiness.diploma.web.controller.validation.GroupWrapper;
import com.monkeybusiness.diploma.web.controller.validation.LoginWrapper;
import com.monkeybusiness.diploma.web.controller.validation.UserFullWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
@RequestMapping("/admin")
public class AdminController {
  public static final String DELIMITER = "\n";
  public static final String ADD_SUCCESS_MESSAGE = "Added successfully";
  public static final String UPDATE_SUCCESS_MESSAGE = "Updated successfully";
  public static final String DELETE_SUCCESS_MESSAGE = "Deleted successfully";

  @Autowired
  private UserService userService;

  @Autowired
  private GroupService groupService;

  @Autowired
  private PracticeService practiceService;

  @GetMapping("/users")
  @ResponseBody
  public List<User> getUsers() {
    return userService.getAllUsers();
  }

  @GetMapping("/practices")
  @ResponseBody
  public List<Practice> getPractices() {
    return practiceService.getAllPractices();
  }

  @GetMapping("/getUsersByGroup")
  @ResponseBody
  public UsersDto getUsersByGroup(@RequestParam String code, HttpSession session) {
    Group group = groupService.getGroupByCode(code);
    List<User> users = userService.getUsersByGroup(group);
    UsersDto usersDto = new UsersDto();
    usersDto.setUsers(users.stream()
            .map(user -> {
              UserDto userDto = new UserDto();
              userDto.setLogin(user.getLogin());
              userDto.setName(user.getName());
              userDto.setMiddleName(user.getMiddleName());
              userDto.setSurname(user.getSurname());
              userDto.setPassword(user.getPassword());
              return userDto;
            }).collect(Collectors.toList()));
    addRoleAndId(usersDto, session);
    return usersDto;
  }

  @DeleteMapping("/deleteUser")
  @ResponseBody
  public MessageDto deleteUser(@RequestBody LoginWrapper loginWrapper, BindingResult bindingResult, HttpSession session) {
    boolean successful = true;
    if (bindingResult.hasErrors()) {
      successful = false;
    } else {
      userService.deleteByLogin(loginWrapper.getLogin());
    }
    return createUserAdminDto(successful, bindingResult, DELETE_SUCCESS_MESSAGE, session);
  }

  @PutMapping("/updateUser")
  @ResponseBody
  public MessageDto updateUser(@RequestBody UserFullWrapper userAdminWrapper, BindingResult bindingResult, HttpSession session) {
    boolean successful = true;
    if (bindingResult.hasErrors()) {
      successful = false;
    } else {
      User user = createUser(userAdminWrapper);
      userService.update(user);
    }
    return createUserAdminDto(successful, bindingResult, UPDATE_SUCCESS_MESSAGE, session);
  }

  @PostMapping("/addUser")
  @ResponseBody
  public UserDto addUser(@RequestBody UserFullWrapper userAdminWrapper, BindingResult bindingResult, HttpSession session) {
    boolean successful = true;
    Long id = null;
    if (bindingResult.hasErrors()) {
      successful = false;
    } else {
      User user = createUser(userAdminWrapper);
      userService.save(user);
      id = user.getId();
    }
    return createUserAddedDto(successful, bindingResult, ADD_SUCCESS_MESSAGE, session, id);
  }

  @PostMapping("/addGroup")
  @ResponseBody
  public GroupDto addGroup(@RequestBody GroupWrapper groupWrapper, BindingResult bindingResult, HttpSession session) {
    boolean successful = true;
    Long id = null;
    if (bindingResult.hasErrors()) {
      successful = false;
    } else {
      Group group = new Group();
      group.setCode(groupWrapper.getCode());
      groupService.save(group);
      id = group.getId();
    }
    return createGroupAddedDto(successful, bindingResult, ADD_SUCCESS_MESSAGE, session, id);
  }

  private User createUser(UserFullWrapper userAdminWrapper) {
    User user = new User();
    user.setName(userAdminWrapper.getName());
    user.setSurname(userAdminWrapper.getSurname());
    user.setMiddleName(userAdminWrapper.getMiddleName());
    user.setLogin(userAdminWrapper.getLogin());
    user.setPassword(userAdminWrapper.getPassword());
    user.setRole(Roles.STUDENT.name());
    user.setGroup(groupService.getGroupByCode(userAdminWrapper.getGroupCode()));
    return user;
  }

  private GroupDto createGroupAddedDto(boolean successful, BindingResult bindingResult, String successMessage, HttpSession session, Long id) {
    GroupDto userAdminDto = new GroupDto();
    userAdminDto.setSuccessful(successful);
    String message;
    if (!successful) {
      message = bindingResult.getAllErrors().stream()
              .map(DefaultMessageSourceResolvable::getDefaultMessage)
              .collect(Collectors.joining(DELIMITER));
    } else {
      message = successMessage;
    }
    userAdminDto.setMessage(message);
    userAdminDto.setId(id);
    addRoleAndId(userAdminDto, session);
    return userAdminDto;
  }

  private UserDto createUserAddedDto(boolean successful, BindingResult bindingResult, String successMessage, HttpSession session, Long id) {
    UserDto userAdminDto = new UserDto();
    userAdminDto.setSuccessful(successful);
    String message;
    if (!successful) {
      message = bindingResult.getAllErrors().stream()
              .map(DefaultMessageSourceResolvable::getDefaultMessage)
              .collect(Collectors.joining(DELIMITER));
    } else {
      message = successMessage;
    }
    userAdminDto.setMessage(message);
    userAdminDto.setId(id);
    addRoleAndId(userAdminDto, session);
    return userAdminDto;
  }

  private MessageDto createUserAdminDto(boolean successful, BindingResult bindingResult, String successMessage, HttpSession session) {
    MessageDto userAdminDto = new MessageDto();
    userAdminDto.setSuccessful(successful);
    String message;
    if (!successful) {
      message = bindingResult.getAllErrors().stream()
              .map(DefaultMessageSourceResolvable::getDefaultMessage)
              .collect(Collectors.joining(DELIMITER));
    } else {
      message = successMessage;
    }
    userAdminDto.setMessage(message);
    addRoleAndId(userAdminDto, session);
    return userAdminDto;
  }

  private void addRoleAndId(AbstractDto dto, HttpSession session) {
    dto.setUser_role((String) session.getAttribute("user_role"));
    dto.setUser_id((Long) session.getAttribute("user_id"));
  }
}
