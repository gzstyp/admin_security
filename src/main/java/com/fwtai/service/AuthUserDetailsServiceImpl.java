package com.fwtai.service;

import com.fwtai.bean.SysUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 要实现UserDetailsService接口，这个接口是security提供的
 */
@Service
public class AuthUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserService userService;

    @Autowired
    private SysRoleTableService roleService;

    /**
     * 通过账号查找用户、角色的信息
     * @param username
     * @return
     * @throws UsernameNotFoundException
    */
    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final SysUserEntity user = userService.getUserByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("%s.这个用户不存在", username));
        }else {
            //查找角色
            final List<String> roles =  roleService.getRolesByUserName(username);
            final List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            for (final String role : roles) {
                authorities.add(new SimpleGrantedAuthority(role));
            }
            System.out.println("loadUserByUsername......user ===> " + user);
            return new AuthUser(user.getUserName(), user.getPassWord(), user.getState(), authorities);
        }
    }
}