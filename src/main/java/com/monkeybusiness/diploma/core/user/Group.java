package com.monkeybusiness.diploma.core.user;

import java.util.Objects;

public class Group {
  private Long id;
  private String code;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Group group = (Group) o;
    return Objects.equals(id, group.id) && Objects.equals(code, group.code);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, code);
  }
}
