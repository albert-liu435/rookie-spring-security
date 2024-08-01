package com.rookie.bigdata.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.*;

/**
 * @Class CustomUserDetails
 * @Description 参考 org.springframework.security.core.userdetails.User
 * @Author rookie
 * @Date 2024/8/1 9:33
 * @Version 1.0
 */
@Data
@NoArgsConstructor
public class CustomUserDetails implements UserDetails {

    /**
     * username
     */
    private String name;

    /**
     * password
     */
    private String password;

    /**
     *
     */
//    private final Set<GrantedAuthority> authorities;
    private Set<SimpleGrantedAuthority> authorities;//=new HashSet<>();

    public CustomUserDetails(@NonNull User user, Collection<SimpleGrantedAuthority> authorities) {
        this.name = user.getName();
        this.password = user.getPassword();

//        for (Role role : roleList) {
//            authorities.add(new SimpleGrantedAuthority(role.getName()));
//        }

        this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
//        this.authorities = Collections.singleton(new SimpleGrantedAuthority(user.getRole()));
    }

    @Override
    public Set<SimpleGrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
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


    private static SortedSet<SimpleGrantedAuthority> sortAuthorities(Collection<SimpleGrantedAuthority> authorities) {
        Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
        // Ensure array iteration order is predictable (as per
        // UserDetails.getAuthorities() contract and SEC-717)
        SortedSet<SimpleGrantedAuthority> sortedAuthorities = new TreeSet<>(new AuthorityComparator());
        for (SimpleGrantedAuthority grantedAuthority : authorities) {
            Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");
            sortedAuthorities.add(grantedAuthority);
        }
        return sortedAuthorities;
    }


    private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {

        private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

        @Override
        public int compare(GrantedAuthority g1, GrantedAuthority g2) {
            // Neither should ever be null as each entry is checked before adding it to
            // the set. If the authority is null, it is a custom authority and should
            // precede others.
            if (g2.getAuthority() == null) {
                return -1;
            }
            if (g1.getAuthority() == null) {
                return 1;
            }
            return g1.getAuthority().compareTo(g2.getAuthority());
        }

    }

}
