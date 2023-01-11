package com.github.seastar.wdm.data.jdbc.mapper;

import com.github.seastar.wdm.model.ValueModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**********************************
 * Date: 2023/1/7
 * Author: hchery
 * Home: https://github.com/hchery
 *********************************/
public non-sealed abstract class ValueRowMapper<T extends ValueModel> extends BaseRowMapper<T> {

    @Override
    public final T mapRow(ResultSet rs, int rowNum) throws SQLException {
        var t = Objects.requireNonNull(super.mapRow(rs, rowNum));
        t.setCreateAt(rs.getDate("create_time"));
        t.setUpdateAt(rs.getDate("update_time"));
        return t;
    }
}
