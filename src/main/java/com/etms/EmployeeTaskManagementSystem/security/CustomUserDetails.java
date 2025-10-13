package com.etms.EmployeeTaskManagementSystem.security;

import com.etms.EmployeeTaskManagementSystem.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.List;


public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail(); // we use email for login
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // can add logic if needed
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // can add logic if needed
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // can add logic if needed
    }

    @Override
    public boolean isEnabled() {
        return true; // can add logic if needed
    }
}
