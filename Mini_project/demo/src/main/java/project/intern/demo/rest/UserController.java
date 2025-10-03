package project.intern.demo.rest;





import org.springframework.web.bind.annotation.*;
import project.intern.demo.dto.request.user.UserCreateRequest;
import project.intern.demo.dto.request.user.UserUpdateRequest;
import project.intern.demo.dto.response.ApiResponse;
import project.intern.demo.dto.response.UserResponse;
import project.intern.demo.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/myInfo")
    public ApiResponse<UserResponse> getMyInfo()
    {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(userService.getMyInfo());
        return  apiResponse;
    }

    @GetMapping("/username/{username}")
    public ApiResponse<UserResponse> getByUsername(@PathVariable String username) {
        UserResponse userResponse = userService.findByUsername(username);
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userResponse);
        return apiResponse;
    }

    @GetMapping
    public ApiResponse<List<UserResponse>> getAllUsers() {
        ApiResponse<List<UserResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.getAllUser());
        return apiResponse;
    }

    @GetMapping("/id/{id}")
    public ApiResponse<UserResponse> getUserById(@PathVariable int id) {
        UserResponse userResponse = userService.getUserById(id);
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userResponse);
        return apiResponse;
    }

    @PostMapping
    public ApiResponse<UserResponse> addUser(@RequestBody UserCreateRequest userCreateRequest) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.addUser(userCreateRequest));
        return apiResponse;
    }

    @PutMapping("/{id}")
    public ApiResponse<UserResponse> updateUser(@PathVariable int id,
                                                @RequestBody UserUpdateRequest userUpdateRequest) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.updateUser(id, userUpdateRequest));
        return apiResponse;
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteUser(@PathVariable int id) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.deleteUserById(id));
        return apiResponse;
    }
}
