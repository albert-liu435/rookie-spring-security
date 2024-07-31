package com.rookie.bigdata.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Class CustomUserDetailsDto
 * @Description
 * @Author rookie
 * @Date 2024/7/30 15:22
 * @Version 1.0
 */
@Data
@NoArgsConstructor
public class CustomUserDetailsDto implements UserDetails {

    private String name;
    private String password;

    private Set<SimpleGrantedAuthority> authorities;

    public CustomUserDetailsDto(UserRoleDto userRoleDto) {
        this.name = userRoleDto.getName();
        this.password = userRoleDto.getPassword();
        this.authorities = userRoleDto.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
