package com.lukas.minibank.data.repository;

import com.lukas.minibank.data.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

}