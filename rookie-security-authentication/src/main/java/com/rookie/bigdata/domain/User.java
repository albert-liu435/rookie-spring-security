package com.rookie.bigdata.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @Class User
 * @Description
 * @Author rookie
 * @Date 2024/7/30 14:45
 * @Version 1.0
 */
@Data
public class User implements Serializable {
    private int id;
    private String name;
    private String password;

//    private String role;
}

