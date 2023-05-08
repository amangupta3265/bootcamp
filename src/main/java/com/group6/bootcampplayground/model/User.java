 package com.group6.bootcampplayground.model;

import com.group6.bootcampplayground.auth.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User implements UserDetails {
    @Id
    private String email;
    private String name;
    private String password;
    private String profilePictureUrl;

    @Enumerated(EnumType.STRING)
    private Role role;

     @Override
     public Collection<? extends GrantedAuthority> getAuthorities() {
         return List.of(new SimpleGrantedAuthority(role.name()));
     }

     @Override
     public String getUsername() {
         return email;
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
     public boolean isCredentialsNonExpired() { return true; }

     @Override
     public boolean isEnabled() {
         return true;
     }
 }
