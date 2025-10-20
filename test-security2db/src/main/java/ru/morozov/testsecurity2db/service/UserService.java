package ru.morozov.testsecurity2db.service;

import ru.morozov.testsecurity2db.dto.UserDto;
import ru.morozov.testsecurity2db.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);
    User findUserByEmail(String email);
    List<UserDto> findAllUsers();
}