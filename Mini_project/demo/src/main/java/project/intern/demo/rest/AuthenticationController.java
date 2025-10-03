package project.intern.demo.rest;

import com.nimbusds.jose.JOSEException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import project.intern.demo.dto.request.user.IntrospectRequest;
import project.intern.demo.dto.request.user.LoginRequest;
import project.intern.demo.dto.response.ApiResponse;
import project.intern.demo.dto.response.AuthenticationResponse;
import project.intern.demo.dto.response.IntrospectResponse;
import project.intern.demo.entity.User;
import project.intern.demo.service.AuthenticationService;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
    @PostMapping("/token")
    public ApiResponse<AuthenticationResponse> authentication(@RequestBody LoginRequest loginRequest)
    {
        AuthenticationResponse result = authenticationService.authenticate(loginRequest);
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setResult(result);
        return apiResponse;
    }
    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> authentication(@RequestBody IntrospectRequest introspectRequest) throws ParseException, JOSEException {
        var result = authenticationService.introspectRequest(introspectRequest);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResult(result);
        return apiResponse;
    }
}
