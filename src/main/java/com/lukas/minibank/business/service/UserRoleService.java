package com.lukas.minibank.business.service;

import com.lukas.minibank.data.entity.UserRole;
import com.lukas.minibank.data.repository.AppRoleRepository;
import com.lukas.minibank.data.repository.AppUserRepository;
import com.lukas.minibank.data.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserRoleService {
    private final UserRoleRepository userRoleRepository;
    private final AppUserRepository appUserRepository;
    private final AppRoleRepository appRoleRepository;

    @Autowired
    public UserRoleService(UserRoleRepository userRoleRepository, AppUserRepository appUserRepository, AppRoleRepository appRoleRepository) {
        this.userRoleRepository = userRoleRepository;
        this.appUserRepository = appUserRepository;
        this.appRoleRepository = appRoleRepository;
    }

    public List<UserRole> getUserRoles(){
        Iterable<UserRole> userRoles = this.userRoleRepository.findAll();
        List<UserRole> userRoleList = new ArrayList<>();
        userRoles.forEach(userRoleList::add);
        return userRoleList;
    }

}
