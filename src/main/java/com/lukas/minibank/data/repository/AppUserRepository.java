package com.lukas.minibank.data.repository;

import com.lukas.minibank.data.entity.AppUser;
import org.springframework.data.repository.CrudRepository;

public interface AppUserRepository extends CrudRepository<AppUser, Long> {

}