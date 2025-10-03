package project.intern.demo.service;

import org.springframework.stereotype.Service;
import project.intern.demo.dto.request.user.UserCreateRequest;
import project.intern.demo.dto.request.user.UserUpdateRequest;
import project.intern.demo.dto.response.UserResponse;
import project.intern.demo.entity.User;

import java.util.List;

public interface UserService {
    public List<UserResponse> getAllUser();
    public UserResponse getUserById(int id);
    public UserResponse updateUser(int id,UserUpdateRequest userUpdateRequest);
    public UserResponse addUser(UserCreateRequest userCreateRequest);
    public String deleteUserById(int id);
    public UserResponse findByUsername(String username);
    public UserResponse getMyInfo();

}
