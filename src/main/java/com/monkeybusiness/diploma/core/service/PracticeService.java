package com.monkeybusiness.diploma.core.service;

import com.monkeybusiness.diploma.core.practice.Practice;

import java.util.List;

public interface PracticeService {
  Practice getPractice(Long id);

  List<Practice> getAllPractices();

  void save(Practice practice);

  void update(Practice practice);

  void delete(Long id);
}
