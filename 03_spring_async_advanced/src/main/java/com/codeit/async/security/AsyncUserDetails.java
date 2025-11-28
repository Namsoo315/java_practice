package com.codeit.async.security;

import com.codeit.async.entiry.User;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Builder
public class AsyncUserDetails implements UserDetails {

    private final Long id;
    private final String username;
    private final String password;
    private final String email;
    private final String role;
    private final Collection<? extends GrantedAuthority> authorities;

    public static AsyncUserDetails from(User user) {
        String roleName = user.getRole();
        String authority = roleName.startsWith("ROLE_") ? roleName : "ROLE_" + roleName;

        return AsyncUserDetails.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .role(roleName)
                .authorities(List.of(new SimpleGrantedAuthority(authority)))
                .build();
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}
