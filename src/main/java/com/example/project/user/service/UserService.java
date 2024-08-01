package com.example.project.user.service;

import com.example.project.auth.domain.UserDetail;
import com.example.project.error.dto.ErrorMessage;
import com.example.project.error.exception.user.AlreadyExistUserEmailException;
import com.example.project.error.exception.user.InvalidLoginInfoException;
import com.example.project.error.exception.user.LoginTokenNullException;
import com.example.project.user.domain.User;
import com.example.project.user.domain.vo.Email;
import com.example.project.user.domain.vo.RoleType;
import com.example.project.user.dto.UserResponse;
import com.example.project.user.dto.request.UserRegisterRequest;
import com.example.project.user.dto.request.UserUpdateRequest;
import com.example.project.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponse register(UserRegisterRequest request){
        duplicateValidationMemberEmail(request.getEmail());

        var response = userRepository.save(request.toEntity());

        return response.toResponseDto();
    }

    @Transactional(readOnly = true)
    public void duplicateValidationMemberEmail(String email){
        userRepository.findByEmail(new Email(email))
                .ifPresent(member -> {
                    throw new AlreadyExistUserEmailException(ErrorMessage.ALREADY_EXIST_MEMBER_EMAIL_EXCEPTION, "이미 존재하는 이메일 정보입니다");
                });
    }


    @Transactional
    public UserResponse editMember(UserDetail detail, UserUpdateRequest request) {
        loginCheckException(detail);
        var user = findByIdFromLogin(detail.getId());
        user.updateUser(request);
        userRepository.save(user);

        return user.toResponseDto();
    }

    public void loginCheckException(UserDetail detail) {
        if(detail.getRoleType() == RoleType.GUEST) {
            throw new LoginTokenNullException(ErrorMessage.NOT_LOGIN_USER_EXCEPTION, "로그인 정보가 없습니다");
        }
    }

    @Transactional(readOnly = true)
    public User findByIdFromLogin(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new InvalidLoginInfoException(ErrorMessage.INVALID_LOGIN_USER_INFORMATION_EXCEPTION, "잘못된 유저 로그인 정보입니다"));
    }
}
