package com.github.seastar.wdm.dao.jdbc;

import com.github.seastar.wdm.dao.UserDao;
import com.github.seastar.wdm.data.id.IDGenerator;
import com.github.seastar.wdm.data.jdbc.DataTemplate;
import com.github.seastar.wdm.model.repos.User;
import jakarta.annotation.Resource;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

/**********************************
 * Date: 2023/1/9
 * Author: hchery
 * Home: https://github.com/hchery
 *********************************/
@Repository
public class JdbcUserDao implements UserDao {

    @Resource
    private DataTemplate dataTemplate;

    @Resource
    private IDGenerator idGenerator;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public void add(User user) {
        var sql = dataTemplate.buildInsertSQL(
                TABLE_WDM_REPO_USERS,
                "id",
                "account",
                "credentials",
                "nickname",
                "email",
                "account_expire",
                "credentials_expire"
        );
        user.setId(idGenerator.next());
        dataTemplate.update(
                sql,
                user.getId(),
                user.getAccount(),
                passwordEncoder.encode(user.getCredentials()),
                user.getNickname(),
                user.getEmail(),
                user.getAccountExpire(),
                user.getCredentialsExpire()
        );
    }

    @Override
    public boolean exists(String account) {
        var sql = """
                 SELECT 1 FROM %s
                 WHERE `account` = ?
                """.formatted(TABLE_WDM_REPO_USERS);
        return !dataTemplate.query(sql, new SingleColumnRowMapper<>(), account)
                .isEmpty();
    }

    @Override
    public void activate(Long id) {
        var sql = """
                 UPDATE %s SET `activate` = 1
                 WHERE `id` = ?
                """.formatted(TABLE_WDM_REPO_USERS);
        dataTemplate.update(sql, id);
    }
}
