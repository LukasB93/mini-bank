package com.lukas.minibank.business.service;

import com.lukas.minibank.data.entity.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private AppUserService appUserService;
    private AppRoleService appRoleService;

    @Autowired
    public UserDetailsServiceImpl(AppUserService appUserService, AppRoleService appRoleService) {
        this.appUserService = appUserService;
        this.appRoleService = appRoleService;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        AppUser appUser = this.appUserService.getUserByUsername(userName);

        if (appUser == null) {
            System.out.println("User not found: " + userName);
            throw new UsernameNotFoundException("User " + userName + " was not found in the databse");
        }

        System.out.println("Found User: " + appUser);

        List<String> appRoleNamesList = appRoleService.getRoleNamesOfUser(appUser.getUserId());
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        System.out.println("Found AppRoles: " + appRoleNamesList);
        if (appRoleNamesList != null) {
            appRoleNamesList.forEach(appRoleName -> {
                GrantedAuthority authority = new SimpleGrantedAuthority(appRoleName);
                grantedAuthorityList.add(authority);
            });
        }

        UserDetails userDetails = (UserDetails) new User(appUser.getUserName(), appUser.getEncryptedPassword(), grantedAuthorityList);

        return userDetails;
    }
}
