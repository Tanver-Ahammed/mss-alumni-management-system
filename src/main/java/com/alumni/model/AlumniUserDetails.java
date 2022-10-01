package com.alumni.model;

import com.alumni.entities.Alumni;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AlumniUserDetails implements UserDetails {

    private final Alumni alumni;

    public AlumniUserDetails(Alumni alumni) {
        this.alumni = alumni;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(alumni.getRole()));
        return null;
    }

    @Override
    public String getPassword() {
        return this.alumni.getPassword();
    }

    @Override
    public String getUsername() {
        return this.alumni.getEmail();
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
        return this.alumni.isEnable();
    }
}
