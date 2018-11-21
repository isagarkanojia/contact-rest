package com.contact.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contact.api.model.Role;
import com.contact.api.model.RoleName;

import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
