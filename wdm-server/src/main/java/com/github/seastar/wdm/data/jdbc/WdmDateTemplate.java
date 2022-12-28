package com.github.seastar.wdm.data.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * Date: 2022/12/27
 * Author: hchery
 * home: https://github.com/hchery
 */
@Component
public class WdmDateTemplate extends JdbcTemplate {

    public WdmDateTemplate(DataSource dataSource) {
        super(dataSource);
    }
}