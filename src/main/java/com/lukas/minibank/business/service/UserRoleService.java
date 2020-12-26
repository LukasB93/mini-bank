package com.lukas.minibank.business.service;

import com.lukas.minibank.data.entity.AppRole;
import com.lukas.minibank.data.entity.AppUser;
import com.lukas.minibank.data.entity.UserRole;
import com.lukas.minibank.data.repository.AppRoleRepository;
import com.lukas.minibank.data.repository.AppUserRepository;
import com.lukas.minibank.data.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Map<Long, List<AppRole>> getUserRolesMap(){
        List<UserRole> userRoleList = this.getUserRoles();
        Map<Long, List<AppRole>> userRoleMap = new HashMap<>();
        userRoleList.forEach(userRole -> {
            Long userId = userRole.getAppUser().getUserId();

            //User in Map schon vorhanden: Rolle zur Liste hinzufügen
            if (userRoleMap.containsKey(userId)) {
                List<AppRole> appRoleList = userRoleMap.get(userId);
                appRoleList.add(userRole.getAppRole());
                userRoleMap.put(userId, appRoleList);
            } else {
                List<AppRole> appRoleList = new ArrayList<>();
                appRoleList.add(userRole.getAppRole());
                userRoleMap.put(userId, appRoleList);
            }

        });
        return userRoleMap;
    }

    public List<AppRole> getRolesOfUser(AppUser appUser) {
        Long userId = appUser.getUserId();
        Map<Long, List<AppRole>> userAppRoleMap = getUserRolesMap();
        List<AppRole> appRoles = userAppRoleMap.get(userId);
        return appRoles;
    }
}
