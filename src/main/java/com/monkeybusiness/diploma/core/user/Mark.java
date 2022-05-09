package com.monkeybusiness.diploma.core.user;

import java.util.Objects;

public class Mark {
  private Long id;
  private Integer mark;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getMark() {
    return mark;
  }

  public void setMark(Integer mark) {
    this.mark = mark;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Mark mark = (Mark) o;
    return Objects.equals(id, mark.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
