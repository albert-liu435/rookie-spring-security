package com.rookie.bigdata.mapper;

import com.rookie.bigdata.domain.dto.RoleMenuDto;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

/**
 * @Class RoleMapper
 * @Description
 * @Author rookie
 * @Date 2024/7/30 15:12
 * @Version 1.0
 */
public interface RoleMapper {


    @Select("SELECT " +
            "   r.name " +
            "FROM " +
            "   ROLE r " +
            "WHERE " +
            "   r.id IN ( SELECT ru.role_id FROM ROLE_USER ru WHERE ru.user_id = #{userId} )")
    Set<String> queryRoleName(int userId);

    /**
     *  获取角色名称和权限 URL 的对应关系
     */
    @Select("SELECT " +
            "   r.name as roleName, " +
            "   rpp.request_url as requestUrl " +
            "FROM " +
            "   ROLE r" +
            "   LEFT JOIN ( SELECT rp.role_id, p.request_url FROM MENU_ROLE rp LEFT JOIN MENU p ON rp.menu_id = p.id ) rpp ON r.id = rpp.role_id")
    List<RoleMenuDto> queryRoleMenuDto();
}
