package com.rookie.bigdata.security;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @Class CustomUser
 * @Description 用户对象
 * @Author rookie
 * @Date 2024/7/12 17:46
 * @Version 1.0
 */
public class CustomUser {

    private final long id;

    private final String email;

    @JsonIgnore
    private final String password;

    @JsonIgnore
    private final String secret;

    @JsonIgnore
    private final String answer;

    @JsonCreator
    public CustomUser(long id, String email, String password, String secret, String answer) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.secret = secret;
        this.answer = answer;
    }

    public CustomUser(CustomUser user) {
        this(user.id, user.email, user.password, user.secret, user.answer);
    }

    public long getId() {
        return this.id;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public String getSecret() {
        return this.secret;
    }

    public String getAnswer() {
        return this.answer;
    }

}
