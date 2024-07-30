-- demo_security.`user` definition

CREATE TABLE `user` (
                        `id` int(11) NOT NULL AUTO_INCREMENT,
                        `name` varchar(10) DEFAULT NULL,
                        `password` varchar(100) DEFAULT NULL,
                        `role` varchar(10) DEFAULT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

INSERT INTO `user` (name,password,`role`) VALUES
                                              ('admin','$2a$10$0eA9i4hBVfPNiVz3u4Cg0uF0fEgjCK1EA7tenOXZ..I1W1HtYps.q','ADMIN'),
                                              ('user','$2a$10$8Oht.MIQTMVjjA.lf6hQL./pHHI0GnQC.BC9fBsvPWXaeathXQlry','USER');


-- demo_security.role_user definition

CREATE TABLE `role_user` (
                             `user_id` int(11) DEFAULT NULL,
                             `role_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO role_user (user_id,role_id) VALUES
                                            (1,1),
                                            (2,2);

-- demo_security.`role` definition

CREATE TABLE `role` (
                        `id` int(11) NOT NULL AUTO_INCREMENT,
                        `name` varchar(10) DEFAULT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

INSERT INTO `role` (name) VALUES
                              ('ROLE_ADMIN'),
                              ('ROLE_USER');

-- demo_security.menu_role definition

CREATE TABLE `menu_role` (
                             `role_id` int(11) NOT NULL COMMENT 'ROLE 的 ID',
                             `menu_id` int(11) NOT NULL COMMENT 'menu 的 ID',
                             PRIMARY KEY (`role_id`,`menu_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=COMPACT COMMENT='角色-权限关联表.\r\n一个角色可以有多个权限, 多个角色可以有相同权限, 角色和权限是本张表的联合主键.';

INSERT INTO menu_role (role_id,menu_id) VALUES
                                            (1,1),
                                            (2,2);


-- demo_security.menu definition

CREATE TABLE `menu` (
                        `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
                        `request_url` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '请求的 URL, 形如 /user/1 和 /admin/info',
                        PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=COMPACT COMMENT='权限定义表.\r\n每一条权限代表着一个可访问的 Restful API.';

INSERT INTO menu (request_url) VALUES
                                   ('/admin/info'),
                                   ('/user/*');












