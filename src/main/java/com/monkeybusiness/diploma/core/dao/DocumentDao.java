package com.monkeybusiness.diploma.core.dao;

import com.monkeybusiness.diploma.core.document.Document;

import java.util.List;
import java.util.Optional;

public interface DocumentDao {
  Optional<Document> find(Long id);

  List<Document> findAll();

  void save(Document document);

  void update(Document document);

  void delete(Long id);
}
