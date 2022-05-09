package com.monkeybusiness.diploma.core.dao;

import com.monkeybusiness.diploma.core.practice.Practice;
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
public class JdbcPracticeDao implements PracticeDao {
  public static final String SELECT_PRACTICE_BY_ID = "SELECT * FROM practices WHERE id = ?";
  public static final String SELECT_ALL_PRACTICES = "SELECT * FROM practices";
  public static final String UPDATE_PRACTICE = "UPDATE practices SET location = ?, status = ? WHERE id = ?";
  public static final String DELETE_PRACTICE = "DELETE FROM practices WHERE id = ?";
  public static final String PRACTICES_TABLE = "practices";
  public static final String ID_COLUMN = "id";

  @Autowired
  private JdbcTemplate jdbcTemplate;

  private SimpleJdbcInsert simpleJdbcInsert;

  @PostConstruct
  private void init() {
    simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName(PRACTICES_TABLE)
            .usingGeneratedKeyColumns(ID_COLUMN);
  }

  @Override
  public Optional<Practice> find(Long id) {
    Optional<Practice> optionalPractice;
    List<Practice> practiceList = jdbcTemplate.query(SELECT_PRACTICE_BY_ID,
            new BeanPropertyRowMapper<>(Practice.class), id);
    optionalPractice = practiceList.isEmpty() ? Optional.empty() : Optional.ofNullable(practiceList.get(0));
    return optionalPractice;
  }

  @Override
  public List<Practice> findAll() {
    List<Practice> practices;
    practices = jdbcTemplate.query(SELECT_ALL_PRACTICES, new BeanPropertyRowMapper<>(Practice.class));
    return practices;
  }

  @Override
  public void save(Practice practice) {
    addPractice(practice);
  }

  private void addPractice(Practice practice) {
    SqlParameterSource source = new BeanPropertySqlParameterSource(practice);
    Long id = (Long) simpleJdbcInsert.executeAndReturnKey(source);
    practice.setId(id);
  }

  @Override
  public void update(Practice practice) {
    jdbcTemplate.update(UPDATE_PRACTICE, practice.getLocation(), practice.getStatus(), practice.getId());
  }

  @Override
  public void delete(Long id) {
    jdbcTemplate.update(DELETE_PRACTICE, id);
  }
}
