package com.example.wantedpreonboardingbackend.common.security;

import com.example.wantedpreonboardingbackend.member.domain.Role;
import jakarta.persistence.Id;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
public class CustomUserDetails implements UserDetails {

    private String id;
    private String userName;
    private Role role;

    private Collection<GrantedAuthority> authorities = new ArrayList<>();
    private Long accessTokenExpiration;
    private Date createdAt;

    @Builder
    public CustomUserDetails(String id,String userName,
        Long accessTokenExpiration,
        Role role,
        Date createdAt
    ) {
        this.id = id;
        this.userName =userName;
        this.accessTokenExpiration = accessTokenExpiration;
        this.role = role;
        this.authorities.add(new SimpleGrantedAuthority(role.toString()));
        this.createdAt = createdAt;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {

        return this.authorities;
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return userName;
    }

    public String getLoginId() {
        return id;
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