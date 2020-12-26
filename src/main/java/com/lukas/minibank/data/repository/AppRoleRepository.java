package com.lukas.minibank.data.repository;

import com.lukas.minibank.data.entity.AppRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppRoleRepository extends CrudRepository<AppRole, Long> {
}
