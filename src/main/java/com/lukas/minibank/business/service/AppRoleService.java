package com.lukas.minibank.business.service;

import com.lukas.minibank.data.entity.AppRole;
import com.lukas.minibank.data.repository.AppRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppRoleService {
    private final AppRoleRepository appRoleRepository;

    @Autowired
    public AppRoleService(AppRoleRepository appRoleRepository) {
        this.appRoleRepository = appRoleRepository;
    }

    public List<AppRole> getAllRoles(){
        Iterable<AppRole> appRoles = this.appRoleRepository.findAll();
        List<AppRole> appRoleList = new ArrayList<>();
        appRoles.forEach(appRoleList::add);
        return appRoleList;
    }

}
