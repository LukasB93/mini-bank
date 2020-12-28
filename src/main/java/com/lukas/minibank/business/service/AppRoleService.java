package com.lukas.minibank.business.service;

import com.lukas.minibank.data.entity.AppRole;
import com.lukas.minibank.data.entity.UserRole;
import com.lukas.minibank.data.repository.AppRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppRoleService {
    private final AppRoleRepository appRoleRepository;
    private final EntityManager entityManager;

    @Autowired
    public AppRoleService(AppRoleRepository appRoleRepository, EntityManager entityManager) {
        this.appRoleRepository = appRoleRepository;
        this.entityManager = entityManager;
    }


    public List<AppRole> getAllRoles(){
        Iterable<AppRole> appRoles = this.appRoleRepository.findAll();
        List<AppRole> appRoleList = new ArrayList<>();
        appRoles.forEach(appRoleList::add);
        return appRoleList;
    }


    public List<String> getRoleNamesByUserId(Long userId) {
        String sql = "SELECT ur.appRole.roleName from " + UserRole.class.getName() + " AS ur "
                    + "WHERE ur.appUser.userId = :userId ";

        Query query = this.entityManager.createQuery(sql, String.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }
}
