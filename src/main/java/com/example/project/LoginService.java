package com.example.project;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository repository;

    public User login(String id, String password){
        if(repository.existsById(id)){
            User user = repository.findById(id).get();
            if (user.getPassword().equals(password)){
                return user;
            }else {
                return new User(
                        user.getId(),
                        "error" //password가 일치하지 않음
                );
            }
        }else {
            return new User(
                    "error",    //ID가 일치하지 않음
                    null
            );
        }
    }
}
