package com.example.project.

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository repository;

    public User login(String id, String password) throws NoSuchAlgorithmException {
        if (repository.existsById(id)) {
            User user = repository.findById(id).get();
            String encryptedPassword = encryptPassword(password);// 비밀번호 암호화해서 저장된 비밀번호와 비교
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


    public String encryptPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());
        return bytesToHex(md.digest());
    }// 입력받은 비밀번호 암호화하기

    private String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }// 바이트 배열을 16진수 문자열로 변환
}