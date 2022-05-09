package com.monkeybusiness.diploma.core.practice;

import java.util.Objects;

public class Practice {
  private Long id;
  private String location;
  private String status;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Practice practice = (Practice) o;
    return Objects.equals(id, practice.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
