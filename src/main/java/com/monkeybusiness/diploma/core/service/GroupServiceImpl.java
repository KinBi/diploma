package com.monkeybusiness.diploma.core.service;

import com.monkeybusiness.diploma.core.dao.GroupDao;
import com.monkeybusiness.diploma.core.user.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupServiceImpl implements GroupService {

  @Autowired
  private GroupDao jdbcGroupDao;

  @Override
  public Group getGroup(Long id) {
    Optional<Group> optionalGroup = jdbcGroupDao.find(id);
    return optionalGroup.orElseThrow(RuntimeException::new);
  }

  @Override
  public Group getGroupByCode(String code) {
    Optional<Group> optionalGroup = jdbcGroupDao.findByCode(code);
    return optionalGroup.orElseThrow(RuntimeException::new);
  }

  @Override
  public List<Group> getAllGroups() {
    List<Group> groups = jdbcGroupDao.findAll();
    return groups;
  }

  @Override
  public void save(Group group) {
    jdbcGroupDao.save(group);
  }

  @Override
  public void update(Group group) {
    jdbcGroupDao.update(group);
  }

  @Override
  public void delete(Long id) {
    jdbcGroupDao.delete(id);
  }
}
