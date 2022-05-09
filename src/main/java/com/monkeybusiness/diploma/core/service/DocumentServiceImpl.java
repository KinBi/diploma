package com.monkeybusiness.diploma.core.service;

import com.monkeybusiness.diploma.core.dao.DocumentDao;
import com.monkeybusiness.diploma.core.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentServiceImpl implements DocumentService {

  @Autowired
  private DocumentDao jdbcDocumentDao;

  @Override
  public Document getDocument(Long id) {
    Optional<Document> optionalDocument = jdbcDocumentDao.find(id);
    return optionalDocument.orElseThrow(RuntimeException::new);
  }

  @Override
  public List<Document> getAllDocuments() {
    List<Document> documents = jdbcDocumentDao.findAll();
    return documents;
  }

  @Override
  public void save(Document document) {
    jdbcDocumentDao.save(document);
  }

  @Override
  public void update(Document document) {
    jdbcDocumentDao.update(document);
  }

  @Override
  public void delete(Long id) {
    jdbcDocumentDao.delete(id);
  }
}
