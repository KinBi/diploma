package com.monkeybusiness.diploma.core.service;

import com.monkeybusiness.diploma.core.dao.UserDao;
import com.monkeybusiness.diploma.core.user.Group;
import com.monkeybusiness.diploma.core.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserDao jdbcUserDao;

  @Override
  public User getUser(Long id) {
    Optional<User> optionalUser = jdbcUserDao.find(id);
    return optionalUser.orElseThrow(RuntimeException::new);
  }

  @Override
  public User getUserByLogin(String login) {
    Optional<User> optionalUser = jdbcUserDao.findByUsername(login);
    return optionalUser.orElseThrow(RuntimeException::new);
  }

  @Override
  public List<User> getUsersWithRoles(List<String> roles) {
    return getAllUsers().stream()
            .filter(user -> roles.contains(user.getRole()))
            .collect(Collectors.toList());
  }

  @Override
  public List<User> getUsersByGroup(Group group) {
    return jdbcUserDao.findByGroup(group);
  }

  @Override
  public List<User> getAllUsers() {
    List<User> users = jdbcUserDao.findAll();
    return users;
  }

  @Override
  public void save(User user) {
    jdbcUserDao.save(user);
  }

  @Override
  public void update(User user) {
    jdbcUserDao.update(user);
  }

  @Override
  public void updateRole(User user) {
    jdbcUserDao.updateRole(user);
  }

  @Override
  public void delete(Long id) {
    jdbcUserDao.delete(id);
  }

  @Override
  public void deleteByLogin(String login) {
    jdbcUserDao.delete(jdbcUserDao.findByUsername(login).get().getId());
  }
}
