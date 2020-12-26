package com.lukas.minibank.business.service;

import com.lukas.minibank.data.entity.AppUser;
import com.lukas.minibank.data.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AppUserService {
    private final AppUserRepository appUserRepository;

    @Autowired
    public AppUserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public List<AppUser> getUsers(){
        Iterable<AppUser> users = this.appUserRepository.findAll();
        List<AppUser> appUserList = new ArrayList<>();
        users.forEach(appUserList::add);
        return appUserList;
    }

    public AppUser getUserByUsername(String userName) throws UsernameNotFoundException {
        Iterable<AppUser> users = this.appUserRepository.findAll();
        Map<String, AppUser> appUsersMap = new HashMap<>();
        users.forEach(appUser -> appUsersMap.put(appUser.getUserName(), appUser));
        AppUser appUser = appUsersMap.get(userName);
        return appUser;
    }

    public Optional<AppUser> getUserById(Long userId) {
        return this.appUserRepository.findById(userId);
    }

    public AppUser findUserAccount(String userName) {

        return null;
    }
}
