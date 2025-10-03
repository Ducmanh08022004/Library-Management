package project.intern.demo.service.Impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import project.intern.demo.dto.request.user.UserCreateRequest;
import project.intern.demo.dto.request.user.UserUpdateRequest;
import project.intern.demo.dto.response.UserResponse;
import project.intern.demo.entity.User;
import project.intern.demo.exception.AppException;
import project.intern.demo.exception.ErrorCode;
import project.intern.demo.repository.UserRepository;
import project.intern.demo.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private UserResponse maptoResponse(User user)
    {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getFullName(),
                user.getRole(),
                user.getEmail()
        );
    }


    @Override
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public List<UserResponse> getAllUser() {
        return
                userRepository.findAll().stream().map(this::maptoResponse)
                        .toList();
    }

    @Override
    @PreAuthorize("#id == authentication.tokenAttributes['userId'] or hasAuthority('SCOPE_ADMIN')")
    public UserResponse getUserById(int id) {

        return
                maptoResponse(userRepository.findById(id)
                        .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_FOUND)));
    }

    @Override
    @Transactional
    @PreAuthorize("#id == authentication.tokenAttributes['userId'] or hasAuthority('SCOPE_ADMIN')")
    public UserResponse updateUser(int id,UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(userUpdateRequest.getPassword()));
        user.setRole(userUpdateRequest.getRole());
        user.setFullName(userUpdateRequest.getFullName());

        return maptoResponse(userRepository.saveAndFlush(user));
    }

    @Override
    @Transactional
    public UserResponse addUser(UserCreateRequest userCreateRequest) {

        if (userRepository.existsByUsername(userCreateRequest.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);

        User user = new User();
        user.setUsername(userCreateRequest.getUsername());
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(userCreateRequest.getPassword()));
        user.setRole(userCreateRequest.getRole());
        user.setEmail(userCreateRequest.getEmail());
        user.setFullName(userCreateRequest.getFullName());
        return maptoResponse(userRepository.save(user));
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public String deleteUserById(int id) {
        if (!userRepository.existsById(id))
        {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        userRepository.deleteById(id);
        return "User has been deleted";
    }

    @Override
    @PreAuthorize("#username == authentication.name or hasAuthority('SCOPE_ADMIN')")
    public UserResponse findByUsername( String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return maptoResponse(user);
    }

    @Override
    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );
        return maptoResponse(user);
    }

}
