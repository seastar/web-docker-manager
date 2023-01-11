package com.github.seastar.wdm.data.jdbc.mapper;

import com.github.seastar.wdm.model.RecordModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**********************************
 * Date: 2023/1/7
 * Author: hchery
 * Home: https://github.com/hchery
 *********************************/
public non-sealed abstract class RecordRowMapper<T extends RecordModel> extends BaseRowMapper<T> {

    @Override
    public final T mapRow(ResultSet rs, int rowNum) throws SQLException {
        var t = Objects.requireNonNull(super.mapRow(rs, rowNum));
        t.setRecordAt(rs.getDate("record_time"));
        return t;
    }
}
