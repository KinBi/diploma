package com.monkeybusiness.diploma.web.controller.dto;

import java.util.List;

public class UsersDto extends AbstractDto {
  private List<UserDto> users;

  public List<UserDto> getUsers() {
    return users;
  }

  public void setUsers(List<UserDto> users) {
    this.users = users;
  }
}
