package ru.morozov.testsecurity2db.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.morozov.testsecurity2db.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}