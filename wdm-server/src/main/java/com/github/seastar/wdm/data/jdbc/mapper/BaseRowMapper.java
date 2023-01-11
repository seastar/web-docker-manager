package com.github.seastar.wdm.data.jdbc.mapper;

import com.github.seastar.wdm.model.BaseModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**********************************
 * Date: 2023/1/7
 * Author: hchery
 * Home: https://github.com/hchery
 *********************************/
public sealed abstract class BaseRowMapper<T extends BaseModel> implements RowMapper<T>
        permits RecordRowMapper, ValueRowMapper {

    protected abstract T createInstance();
    protected abstract void map(ResultSet rs, T t) throws SQLException;

    @Override
    public T mapRow(ResultSet rs, int rowNum) throws SQLException {
        var t = createInstance();
        t.setId(rs.getLong("id"));
        map(rs, t);
        return t;
    }
}
