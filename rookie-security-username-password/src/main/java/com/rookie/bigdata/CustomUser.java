package com.rookie.bigdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @Class CustomUser
 * @Description
 * @Author rookie
 * @Date 2024/6/26 17:21
 * @Version 1.0
 */
public class CustomUser {

    private final long id;

    private final String email;

    @JsonIgnore
    private final String password;

    @JsonCreator
    public CustomUser(@JsonProperty("id") long id, @JsonProperty("email") String email,
                      @JsonProperty("password") String password) {
        this.id = id;
        this.email = email;
        this.password = password;
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

}
