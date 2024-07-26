package com.example.project.service;

import com.example.project.dto.AddUserRequest;
import com.example.project.entity.User;
import com.example.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void addUser(AddUserRequest addUserRequest) {
        if (!addUserRequest.getPassword().equals(addUserRequest.getRepeatPassword())) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다");
        }

        Optional<User> existingUser = userRepository.findByEmail(addUserRequest.getEmail());
        if (existingUser.isPresent()) {
            throw new IllegalStateException("이미 사용되고 있는 이메일입니다");
        }

        User user = new User(
                addUserRequest.getUserId(),
                bCryptPasswordEncoder.encode(addUserRequest.getPassword()),
                addUserRequest.getUserName(),
                addUserRequest.getEmail()
        );
        userRepository.save(user);
    }

    public boolean authenticate(String userId, String rawPassword) {
        Optional<User> userOptional = userRepository.findByUserId(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return bCryptPasswordEncoder.matches(rawPassword, user.getPassword());
        }
        return false;
    }
}
