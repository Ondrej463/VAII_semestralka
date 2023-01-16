package com.vaii_semestralka.users;

import java.util.List;

public interface UserServiceInterface {
    List<UserEntity> getAllUsers();
    void save(UserEntity user);
    UserEntity getByEmail(String email);
    void updateUserCredit(double credit, String email);
}
