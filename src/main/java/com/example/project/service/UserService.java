package com.example.project.service;

import com.example.project.dto.AddUserRequest;
import com.example.project.entity.User;
import com.example.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(AddUserRequest addUserRequest) {
        if (!addUserRequest.getPassword().equals(addUserRequest.getConfirmPassword())) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다");
        }

        if (userRepository.existsByEmail(addUserRequest.getEmail())) {
            throw new IllegalStateException("이미 사용되고 있는 이메일입니다");
        }

        //조건문 추가(입력한 이메일 인증번호가 일치하지 않을 때 exception 처리 일치할 경우 입력 정보 저장)

        User user = new User();
        user.setUserId(addUserRequest.getUserId());
        user.setUserName(addUserRequest.getUserName());
        user.setEmail(addUserRequest.getEmail());
        user.setPassword(addUserRequest.getPassword()); // 비밀번호 암호화 제거

        userRepository.save(user); // 사용자 저장
    }

    public boolean authenticate(String userId, String password) {
        Optional<User> userOptional = userRepository.findByUserId(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return password.equals(user.getPassword());
        }
        return false;
    }
}
