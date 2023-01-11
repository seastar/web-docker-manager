package com.github.seastar.wdm.dao.mapper;

import com.github.seastar.wdm.data.jdbc.mapper.ValueRowMapper;
import com.github.seastar.wdm.model.repos.User;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**********************************
 * Date: 2023/1/7
 * Author: hchery
 * Home: https://github.com/hchery
 *********************************/
@Component
public class UserRowMapper extends ValueRowMapper<User> {

    @Override
    protected User createInstance() {
        return new User();
    }

    @Override
    protected void map(ResultSet rs, User user) throws SQLException {
        user.setAccount(rs.getString("account"));
        user.setCredentials(rs.getString("credentials"));
        user.setNickname(rs.getString("nickname"));
        user.setEmail(rs.getString("email"));
        user.setAccountExpire(rs.getDate("account_expire"));
        user.setCredentialsExpire(rs.getDate("credentials_expire"));
        user.setEnabled(rs.getInt("enabled") == 1);
        user.setLocked(rs.getInt("locked") == 1);
        user.setActivate(rs.getInt("activate") == 1);
        user.setArchive(rs.getInt("archive") == 1);
    }
}
