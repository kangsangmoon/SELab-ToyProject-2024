package com.example.project;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository repository;
    private final EncryptionService encryptionService;

    public User login(String id, String password) {
        if (repository.existsById(id)) {
            User user = repository.findById(id).orElse(new User("error", null)); //ID가 일치하지 않음
            String encryptedPassword = encryptionService.encrypt(password);// 비밀번호 암호화해서 저장된 비밀번호와 비교
            if (user.getPassword().equals(encryptedPassword)) {
                return user;
            } else {
                return new User(
                        user.getId(),
                        "error" //password가 일치하지 않음
                );
            }
        } else {
            return new User(
                    "error",    //ID가 일치하지 않음
                    null
            );
        }
    }

}