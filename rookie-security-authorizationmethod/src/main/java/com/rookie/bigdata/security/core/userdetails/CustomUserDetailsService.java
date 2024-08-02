package com.rookie.bigdata.security.core.userdetails;

import com.rookie.bigdata.domain.dto.CustomUserDetailsDto;
import com.rookie.bigdata.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Class CustomUserDetailsService
 * @Description 参考 InitializeUserDetailsBeanManagerConfigurer.InitializeUserDetailsManagerConfigurer#configure()
 * 		List<BeanWithName<UserDetailsService>> userDetailsServices = getBeansWithName(UserDetailsService.class);
 * @Author rookie
 * @Date 2024/7/30 15:21
 * @Version 1.0
 */
//@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final String USERNAME_NOT_FOUND_MESSAGE = "User with name (%s) not exists.";

//    @Autowired
    private UserMapper userMapper;

    public CustomUserDetailsService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new CustomUserDetailsDto(
                Optional.ofNullable(userMapper.getUserRoleDto(username)).orElseThrow(() -> new UsernameNotFoundException(String.format(USERNAME_NOT_FOUND_MESSAGE, username)))
        );
    }


}
