package com.rookie.bigdata.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @Class UserRoleDto
 * @Description
 * @Author rookie
 * @Date 2024/7/30 15:12
 * @Version 1.0
 */
@Data
@NoArgsConstructor
public class UserRoleDto {

    // ~ USER Fields
    // -----------------------------------------------------------------------------------------------------------------
    private String name;
    private String password;

    // ~ ROLE_USER Fields
    private Set<String> roles;

}
