package com.lukas.minibank.business.service;

import com.lukas.minibank.data.entity.User;
import com.lukas.minibank.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers(){
        Iterable<User> users = this.userRepository.findAll();
        List<User> userList = new ArrayList<>();
        users.forEach(userList::add);
        userList.sort(new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                if (u1.getLastName() == u2.getLastName()) {
                    return u1.getFirstName().compareTo(u2.getFirstName());
                }
                return u1.getLastName().compareTo(u2.getLastName());
            }
        });
        return userList;
    }
}
