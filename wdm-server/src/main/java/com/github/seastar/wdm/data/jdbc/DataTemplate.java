package com.github.seastar.wdm.data.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.StringJoiner;

/**********************************
 * Date: 2023/1/4
 * Author: hchery
 * Home: https://github.com/hchery
 *********************************/
@Component
public class DataTemplate extends JdbcTemplate {

    public DataTemplate(DataSource dataSource) {
        super(dataSource);
    }

    public String buildInsertSQL(String tableName, String... columns) {
        var columnJoiner = new StringJoiner(",");
        var seizeJoiner = new StringJoiner(",");
        for (String column : columns) {
            columnJoiner.add("`%s`".formatted(column));
            seizeJoiner.add("?");
        }
        return "INSERT INTO %s(%s) VALUES(%s)".formatted(
                tableName,
                columnJoiner.toString(),
                seizeJoiner.toString()
        );
    }

    public <T> Ref<T> head(String sql, RowMapper<T> rowMapper, Object... args) {
        var r = query(sql, rowMapper, args);
        return new Ref<>(r.isEmpty() ? null : r.get(0));
    }
}
