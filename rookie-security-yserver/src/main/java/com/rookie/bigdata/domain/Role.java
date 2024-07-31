package com.rookie.bigdata.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @Class Role
 * @Description
 * @Author rookie
 * @Date 2024/7/30 15:11
 * @Version 1.0
 */
@Data
public class Role implements Serializable {

    private String id;
    private String name;
}
