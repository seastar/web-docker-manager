package com.github.seastar.wdm.model.repos;

import com.github.seastar.wdm.model.ValueModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;
import java.util.Date;

/**********************************
 * Date: 2023/1/3
 * Author: hchery
 * Home: https://github.com/hchery
 *********************************/
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@Entity
@Table(name = "wdm_repo_users", indexes = {
        @Index(columnList = "account", unique = true),
        @Index(columnList = "email", unique = true),
        @Index(columnList = "nickname"),
        @Index(columnList = "_state")
})
public class User extends ValueModel {
    @Serial
    private static final long serialVersionUID = -3497189501833991273L;

    @Column(length = 32, nullable = false)
    private String account;
    @Column(length = 512, nullable = false)
    private String password;
    @Column(length = 32)
    private String nickname;
    @Column(length = 128, nullable = false)
    private String email;
    @Column(name = "_state", nullable = false)
    private int state;
    @Column(name = "account_expire")
    private Date accountExpire;
    @Column(name = "password_expire")
    private Date passwordExpire;
}
