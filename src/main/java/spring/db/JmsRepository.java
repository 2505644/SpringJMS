package spring.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import javax.jms.TextMessage;
import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class JmsRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(JmsRepository.class);

    private final JdbcTemplate jdbcTemplate;

    public JmsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void writeToDB(TextMessage textMessage) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sqlMessage = "INSERT INTO message(text) VALUES(?)";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlMessage, Statement.RETURN_GENERATED_KEYS);
            try {
                ps.setString(1, textMessage.getText());
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
            return ps;
        }, keyHolder);

        String sqlHeaders = "INSERT INTO headers(id, head) VALUES(?, ?)";
        try {
            jdbcTemplate.update(sqlHeaders, keyHolder.getKey().intValue(), textMessage.getJMSType());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    public void showDataInDB() {
        LOGGER.info("Show result in database");
        String sql = "SELECT mg.text, hs.head" +
                " FROM message mg" +
                " INNER JOIN headers hs" +
                " ON mg.id = hs.id;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);
        while (rowSet.next()) {
            LOGGER.info(rowSet.getString(1) + "  " + rowSet.getString(2));
        }
    }
}
