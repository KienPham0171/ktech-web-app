package com.kein.ktech.security;

import com.kein.ktech.domain.Role;
import com.kein.ktech.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {
    private User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.user.getRoles()
                .stream().map(role ->
                    new SimpleGrantedAuthority(role.getRoleName())
                ).collect(Collectors.toList());

    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getEmail();
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
    public boolean isActive() {
        return this.user.isActive();
    }
    public String getEmail() {return this.user.getEmail();}
    public long getId() {return this.user.getId();}
    public String getFullName() {return this.user.getFullName();}
    public boolean isAdmin()
    {
        Collection<Role> roles =this.user.getRoles();
        for(Role role : roles){
            if(role.getRoleName().compareTo("admin")==0) return true;
        }
        return false;
    }
}
