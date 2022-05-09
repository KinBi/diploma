package com.monkeybusiness.diploma.web.controller.validation;

import com.monkeybusiness.diploma.core.document.DocumentStatus;

public class DocumentWrapper {
  private Long id;
  private String path;
  private Long userId;
  private DocumentStatus status;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public DocumentStatus getStatus() {
    return status;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public void setStatus(DocumentStatus status) {
    this.status = status;
  }
}
