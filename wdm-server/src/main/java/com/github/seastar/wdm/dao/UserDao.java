package com.github.seastar.wdm.dao;

import com.github.seastar.wdm.model.repos.User;

/**********************************
 * Date: 2023/1/9
 * Author: hchery
 * Home: https://github.com/hchery
 *********************************/
public interface UserDao {

    String TABLE_WDM_REPO_USERS = "wdm_repo_users";

    void add(User user);
    boolean exists(String account);
    void activate(Long id);
}
