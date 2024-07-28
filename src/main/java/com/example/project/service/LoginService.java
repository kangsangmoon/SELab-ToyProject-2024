package com.example.project.service;

import com.example.project.dto.LoginForm;
import com.example.project.exception.LoginException;
import com.example.project.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserRepo repo;


    @Transactional
    public void login(LoginForm form){
        if(!repo.existsById(form.getUserId())){
            throw new LoginException("Id Error");
        }

        User user = repo.findById(form.getUserId());

        if(!user.getPassword().eqeals(form.getPassword())){
            throw new LoginException("Password Error");
        }

        return user;
    }
}
