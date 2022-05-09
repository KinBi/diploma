package com.monkeybusiness.diploma.core.service;

import com.monkeybusiness.diploma.core.dao.PracticeDao;
import com.monkeybusiness.diploma.core.practice.Practice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PracticeServiceImpl implements PracticeService {

  @Autowired
  private PracticeDao jdbcPracticeDao;

  @Override
  public Practice getPractice(Long id) {
    Optional<Practice> optionalPractice = jdbcPracticeDao.find(id);
    return optionalPractice.orElseThrow(RuntimeException::new);
  }

  @Override
  public List<Practice> getAllPractices() {
    List<Practice> practices = jdbcPracticeDao.findAll();
    return practices;
  }

  @Override
  public void save(Practice practice) {
    jdbcPracticeDao.save(practice);
  }

  @Override
  public void update(Practice practice) {
    jdbcPracticeDao.update(practice);
  }

  @Override
  public void delete(Long id) {
    jdbcPracticeDao.delete(id);
  }
}
