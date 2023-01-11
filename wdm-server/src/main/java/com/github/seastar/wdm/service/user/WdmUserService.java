package com.github.seastar.wdm.service.user;

import com.github.seastar.wdm.dao.UserDao;
import com.github.seastar.wdm.model.repos.User;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**********************************
 * Date: 2023/1/11
 * Author: hchery
 * Home: https://github.com/hchery
 *********************************/
@Service
@Slf4j
class WdmUserService implements UserService, InitializingBean {

    @Resource
    private UserDao userDao;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void afterPropertiesSet() {
        if (!userDao.exists("admin")) {
            var adminUser = new User();
            adminUser.setAccount("admin");
            adminUser.setCredentials("123456789");
            adminUser.setNickname("Administrator");
            adminUser.setEmail("admin@example.com");
            userDao.add(adminUser);
            userDao.activate(adminUser.getId());
            log.info("Initialized administrator account with[account={}, credentials={}], id={}",
                    adminUser.getAccount(),
                    adminUser.getCredentials(),
                    adminUser.getId()
            );
        }
    }
}
