package project.intern.demo.service.Impl;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.intern.demo.dto.request.user.IntrospectRequest;
import project.intern.demo.dto.request.user.LoginRequest;
import project.intern.demo.dto.response.AuthenticationResponse;
import project.intern.demo.dto.response.IntrospectResponse;
import project.intern.demo.entity.User;
import project.intern.demo.exception.AppException;
import project.intern.demo.exception.ErrorCode;
import project.intern.demo.repository.UserRepository;
import project.intern.demo.service.AuthenticationService;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;

    @Value("${jwt.signer_key}")
    protected String SIGNER_KEY;

    public AuthenticationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public AuthenticationResponse authenticate(LoginRequest loginRequest) {
        var user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));;


        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        boolean authenticated = passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());
        if (!authenticated)
        {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        var token = generateToken(user);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setToken(token);
        authenticationResponse.setAuthenticated(true);
        return authenticationResponse;
    }

    @Override
    public IntrospectResponse introspectRequest(IntrospectRequest introspectRequest) throws JOSEException, ParseException {
        var token = introspectRequest.getToken();
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        var verify = signedJWT.verify(verifier);
        Date expireTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        IntrospectResponse introspectRespone = new IntrospectResponse();
        introspectRespone.setValid(verify && expireTime.after(new Date()));
        return introspectRespone;
    }

    private String generateToken(User user)
    {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .claim("userId",user.getId())
                .claim("scope", user.getRole())
                .issueTime(new Date())
                .expirationTime(Date.from(Instant.now().plus(1,ChronoUnit.HOURS)))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(jwsHeader,payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {

            throw new RuntimeException(e);
        }
    }

}
