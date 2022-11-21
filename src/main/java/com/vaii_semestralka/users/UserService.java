package com.vaii_semestralka.users;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class UserService implements UserServiceInterface {
    @Autowired private UserRepository userRepository;

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void save(UserEntity user) {
        userRepository.save(user);
    }

    @Override
    public UserEntity getByEmail(String email) {
        Optional<UserEntity> optional = userRepository.findById(email);
        UserEntity user = null;
        return optional.orElse(null);
    }

    @Override
    public void deleteViaEmail(String email) {
        userRepository.deleteById(email);
    }
}
