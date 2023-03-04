package com.example.webapp.login;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class DbUserDetails extends User {
    //ユーザ情報。
    private final LoginForm form;

    public DbUserDetails(LoginForm form,
            Collection<GrantedAuthority> authorities) {

        super(form.getUserId(), form.getPass(),
                true, true, true, true, authorities);

        this.form = form;
    }

    public LoginForm getAccount() {
        return form;
    }

}
