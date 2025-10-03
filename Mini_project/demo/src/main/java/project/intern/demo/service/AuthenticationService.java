package project.intern.demo.service;

import com.nimbusds.jose.JOSEException;
import project.intern.demo.dto.request.user.IntrospectRequest;
import project.intern.demo.dto.request.user.LoginRequest;
import project.intern.demo.dto.response.AuthenticationResponse;
import project.intern.demo.dto.response.IntrospectResponse;

import java.text.ParseException;

public interface AuthenticationService {
    public AuthenticationResponse authenticate(LoginRequest loginRequest);
    public IntrospectResponse introspectRequest(IntrospectRequest introspectRequest) throws JOSEException, ParseException;
}
