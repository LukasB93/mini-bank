package com.lukas.minibank.business.service;

import com.lukas.minibank.data.entity.AppUser;
import com.lukas.minibank.data.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class UserService {
    private final AppUserRepository appUserRepository;

    @Autowired
    public UserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public List<AppUser> getUsers(){
        Iterable<AppUser> users = this.appUserRepository.findAll();
        List<AppUser> appUserList = new ArrayList<>();
        users.forEach(appUserList::add);
        appUserList.sort(new Comparator<AppUser>() {
            @Override
            public int compare(AppUser u1, AppUser u2) {
                if (u1.getLastName() == u2.getLastName()) {
                    return u1.getFirstName().compareTo(u2.getFirstName());
                }
                return u1.getLastName().compareTo(u2.getLastName());
            }
        });
        return appUserList;
    }
}
