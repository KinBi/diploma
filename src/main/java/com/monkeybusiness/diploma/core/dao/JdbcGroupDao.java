package com.monkeybusiness.diploma.core.dao;

import com.monkeybusiness.diploma.core.user.Group;
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

@Component
public class JdbcGroupDao implements GroupDao {
  public static final String SELECT_GROUP_BY_ID = "SELECT * FROM studentGroup WHERE id = ?";
  public static final String SELECT_GROUP_BY_CODE = "SELECT * FROM studentGroup WHERE code = ?";
  public static final String SELECT_ALL_GROUPS = "SELECT * FROM studentGroup";
  public static final String SELECT_GROUP_ID_BY_CODE = "SELECT studentGroup.id FROM studentGroup WHERE code = ?";
  public static final String UPDATE_USER = "UPDATE studentGroup SET code = ? WHERE id = ?";
  public static final String DELETE_GROUP = "DELETE FROM studentGroup WHERE id = ?";
  public static final String GROUP_TABLE = "studentGroup";
  public static final String ID_COLUMN = "id";

  @Autowired
  private JdbcTemplate jdbcTemplate;

  private SimpleJdbcInsert simpleJdbcInsert;

  @PostConstruct
  private void init() {
    simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName(GROUP_TABLE)
            .usingGeneratedKeyColumns(ID_COLUMN);
  }

  @Override
  public Optional<Group> find(Long id) {
    Optional<Group> optionalGroup;
    List<Group> groupList = jdbcTemplate.query(SELECT_GROUP_BY_ID, new BeanPropertyRowMapper<>(Group.class), id);
    optionalGroup = groupList.isEmpty() ? Optional.empty() : Optional.ofNullable(groupList.get(0));
    return optionalGroup;
  }

  @Override
  public Optional<Group> findByCode(String code) {
    Optional<Group> optionalGroup;
    List<Group> groupList = jdbcTemplate.query(SELECT_GROUP_BY_CODE, new BeanPropertyRowMapper<>(Group.class), code);
    optionalGroup = groupList.isEmpty() ? Optional.empty() : Optional.ofNullable(groupList.get(0));
    return optionalGroup;
  }

  @Override
  public List<Group> findAll() {
    List<Group> groups;
    groups = jdbcTemplate.query(SELECT_ALL_GROUPS, new BeanPropertyRowMapper<>(Group.class));
    return groups;
  }

  @Override
  public void save(Group group) {
    Optional<Long> optionalId = getGroupId(group);
    if (optionalId.isPresent()) {
      update(group);
    } else {
      addGroup(group);
    }
  }

  private void addGroup(Group group) {
    SqlParameterSource source = new BeanPropertySqlParameterSource(group);
    Long id = (Long) simpleJdbcInsert.executeAndReturnKey(source);
    group.setId(id);
  }

  private Optional<Long> getGroupId(Group group) {
    List<Long> idList = jdbcTemplate.queryForList(SELECT_GROUP_ID_BY_CODE, Long.class, group.getCode());
    return idList.isEmpty() ? Optional.empty() : Optional.ofNullable(idList.get(0));
  }

  @Override
  public void update(Group group) {
    group.setId(getGroupId(group).orElseThrow(RuntimeException::new)); // fixme
    jdbcTemplate.update(UPDATE_USER, group.getCode());
  }

  @Override
  public void delete(Long id) {
    jdbcTemplate.update(DELETE_GROUP, id);
  }
}
