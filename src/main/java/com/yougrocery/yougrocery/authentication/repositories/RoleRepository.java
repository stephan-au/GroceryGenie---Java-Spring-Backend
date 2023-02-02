package com.yougrocery.yougrocery.authentication.repositories;

import com.yougrocery.yougrocery.authentication.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}