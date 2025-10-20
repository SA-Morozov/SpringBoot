package ru.morozov.testsecurity2db.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.morozov.testsecurity2db.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}