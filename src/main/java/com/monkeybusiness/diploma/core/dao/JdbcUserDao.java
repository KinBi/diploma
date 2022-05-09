package com.monkeybusiness.diploma.core.dao;

import com.monkeybusiness.diploma.core.user.Group;
import com.monkeybusiness.diploma.core.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class JdbcUserDao implements UserDao {
  public static final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
  public static final String SELECT_USER_BY_LOGIN = "SELECT * FROM users WHERE login = ?";
  public static final String SELECT_USER_BY_GROUP = "SELECT * FROM users WHERE groupId = ?";
  public static final String SELECT_ALL_USERS = "SELECT * FROM users";
  public static final String SELECT_USERS_ID_BY_LOGIN = "SELECT users.id FROM users WHERE login = ?";
  public static final String UPDATE_USER_ROLE = "UPDATE users SET login = ?, password = ?, role = ?, practiceId = ?, groupId = ? " +
          "WHERE id = ?";
  public static final String UPDATE_USER = "UPDATE users SET login = ?, password = ?, role = ?, practiceId = ?, groupId = ? " +
          "WHERE id = ?";
  public static final String SELECT_GROUP_ID_BY_USER_ID = "SELECT studentGroup.id FROM studentGroup " +
          "JOIN users ON users.groupId = studentGroup.id WHERE users.id = ?";
  public static final String DELETE_USER = "DELETE FROM users WHERE id = ?";
  public static final String USERS_TABLE = "users";
  public static final String ID_COLUMN = "id";

  @Autowired
  private JdbcTemplate jdbcTemplate;

  private SimpleJdbcInsert simpleJdbcInsert;

  @PostConstruct
  private void init() {
    simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName(USERS_TABLE)
            .usingGeneratedKeyColumns(ID_COLUMN);
  }

  @Override
  public Optional<User> find(Long id) {
    Optional<User> optionalUser;
    List<User> userList = jdbcTemplate.query(SELECT_USER_BY_ID, new BeanPropertyRowMapper<>(User.class), id);
    optionalUser = userList.isEmpty() ? Optional.empty() : Optional.ofNullable(userList.get(0));
    optionalUser.ifPresent(this::setGroup);
    return optionalUser;
  }

  @Override
  public Optional<User> findByUsername(String login) {
    Optional<User> optionalUser;
    List<User> userList = jdbcTemplate.query(SELECT_USER_BY_LOGIN, new BeanPropertyRowMapper<>(User.class), login);
    optionalUser = userList.isEmpty() ? Optional.empty() : Optional.ofNullable(userList.get(0));
    optionalUser.ifPresent(this::setGroup);
    return optionalUser;
  }

  @Override
  public List<User> findByGroup(Group group) {
    List<User> userList = jdbcTemplate.query(SELECT_USER_BY_GROUP, new BeanPropertyRowMapper<>(User.class), group.getId())
            .stream()
            .peek(user -> user.setGroup(group))
            .collect(Collectors.toList());
    return userList;
  }

  private void setGroup(User user) {
    Group group = new Group();
    List<Long> groupIds = jdbcTemplate.queryForList(SELECT_GROUP_ID_BY_USER_ID,
            Long.class, user.getId());
    group.setId(groupIds.get(0));
    user.setGroup(group);
  }

  @Override
  public List<User> findAll() {
    List<User> users;
    users = jdbcTemplate.query(SELECT_ALL_USERS, new BeanPropertyRowMapper<>(User.class));
    users.forEach(this::setGroup);
    return users;
  }

  @Override
  public void save(User user) {
    Optional<Long> optionalId = getUserId(user);
    if (optionalId.isPresent()) {
      update(user);
    } else {
      addUser(user);
    }
  }

  private void addUser(User user) {
    SqlParameterSource source = new BeanPropertySqlParameterSource(user);
    Long id = (Long) simpleJdbcInsert.executeAndReturnKey(source);
    user.setId(id);
  }

  private Optional<Long> getUserId(User user) {
    List<Long> idList = jdbcTemplate.queryForList(SELECT_USERS_ID_BY_LOGIN, Long.class, user.getLogin());
    return idList.isEmpty() ? Optional.empty() : Optional.ofNullable(idList.get(0));
  }

  @Override
  public void update(User user) {
    user.setId(getUserId(user).orElseThrow(RuntimeException::new)); // fixme
    jdbcTemplate.update(UPDATE_USER, user.getLogin(), user.getPassword(), user.getRole(),
            user.getPracticeId(), user.getGroup().getId(), user.getId());
  }

  @Override
  public void updateRole(User user) {
    user.setId(getUserId(user).orElseThrow(RuntimeException::new)); // fixme
    jdbcTemplate.update(UPDATE_USER_ROLE, user.getId());
  }

  @Override
  public void delete(Long id) {
    jdbcTemplate.update(DELETE_USER, id);
  }
}
