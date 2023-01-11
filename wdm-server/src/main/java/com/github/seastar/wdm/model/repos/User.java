package com.github.seastar.wdm.model.repos;

import com.github.seastar.wdm.dao.UserDao;
import com.github.seastar.wdm.model.ValueModel;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Date;
import java.util.List;

/**********************************
 * Date: 2023/1/7
 * Author: hchery
 * Home: https://github.com/hchery
 *********************************/
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@Entity
@Table(name = UserDao.TABLE_WDM_REPO_USERS, indexes = {
        @Index(columnList = "account", unique = true),
        @Index(columnList = "email", unique = true),
        @Index(columnList = "nickname"),
        @Index(columnList = "enabled"),
        @Index(columnList = "locked"),
        @Index(columnList = "activate"),
        @Index(columnList = "archive")
})
public class User extends ValueModel implements UserDetails {
    @Serial
    private static final long serialVersionUID = -3497189501833991273L;

    @Column(name = "account", length = 32, nullable = false)
    private String account;
    @Column(name = "credentials", length = 512, nullable = false)
    private String credentials;
    @Column(name = "nickname", length = 32)
    private String nickname;
    @Column(name = "email", length = 128, nullable = false)
    private String email;
    @Column(name = "account_expire", columnDefinition = "TIMESTAMP")
    private Date accountExpire;
    @Column(name = "credentials_expire", columnDefinition = "TIMESTAMP")
    private Date credentialsExpire;
    @Column(name = "enabled", nullable = false, columnDefinition = "INT DEFAULT 1")
    private boolean enabled;
    @Column(name = "locked", nullable = false, columnDefinition = "INT DEFAULT 0")
    private boolean locked;
    @Column(name = "activate", nullable = false, columnDefinition = "INT DEFAULT 0")
    private boolean activate;
    @Column(name = "archive", nullable = false, columnDefinition = "INT DEFAULT 0")
    private boolean archive;
    @Transient
    private List<UserAuthority> authorities;

    @Override
    public String getUsername() {
        return account;
    }

    @Override
    public String getPassword() {
        return credentials;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountExpire == null ||
                accountExpire.after(new Date());
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsExpire == null ||
                credentialsExpire.after(new Date());
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isLocked();
    }

    public record UserAuthority(@Getter String authority) implements GrantedAuthority {}
}
