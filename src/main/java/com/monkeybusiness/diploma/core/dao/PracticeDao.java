package com.monkeybusiness.diploma.core.dao;

import com.monkeybusiness.diploma.core.practice.Practice;

import java.util.List;
import java.util.Optional;

public interface PracticeDao {
  Optional<Practice> find(Long id);

  List<Practice> findAll();

  void save(Practice practice);

  void update(Practice practice);

  void delete(Long id);
}
