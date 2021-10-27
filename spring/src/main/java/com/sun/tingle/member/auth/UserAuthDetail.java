package com.sun.tingle.member.auth;

import com.sun.tingle.member.db.entity.MemberEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserAuthDetail implements UserDetails {
    @Autowired
    MemberEntity memberEntity;
    boolean accountNonExpired;
    boolean accountNonLocked;
    boolean credentialNonExpired;
    boolean enabled = false;
    List<GrantedAuthority> roles = new ArrayList<>();

    public UserAuthDetail(MemberEntity memberEntity) {
        this.memberEntity = memberEntity;
        roles.add(new SimpleGrantedAuthority(memberEntity.getAuth()));
    } 

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    public void setAutorities(List<GrantedAuthority> roles) {
        this.roles = roles;
    }
    @Override
    public String getPassword() {
        return this.memberEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return this.memberEntity.getMemberId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
