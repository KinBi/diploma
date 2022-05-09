package com.monkeybusiness.diploma.core.service;

import com.monkeybusiness.diploma.core.document.Document;

import java.util.List;

public interface DocumentService {
  Document getDocument(Long id);

  List<Document> getAllDocuments();

  void save(Document document);

  void update(Document document);

  void delete(Long id);
}
