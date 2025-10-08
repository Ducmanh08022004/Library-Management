package project.intern.demo.configuration;

import com.nimbusds.jose.JOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;
import project.intern.demo.dto.request.user.IntrospectRequest;
import project.intern.demo.service.AuthenticationService;

import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;
import java.util.Objects;


@Component
public class CustomJwtDecoder implements JwtDecoder {
    @Value("${jwt.signer_key}")
    private String Secret_key;


    @Autowired
    private AuthenticationService authenticationService;


    private NimbusJwtDecoder jwtDecoder = null;


    @Override
    public Jwt decode(String token) throws JwtException {
        IntrospectRequest introspectRequest = new IntrospectRequest();
        introspectRequest.setToken(token);
        try {
            var response = authenticationService.introspectRequest(introspectRequest);
            if (!response.isValid())
                throw new BadJwtException("Token inValid");
        } catch (ParseException | JOSEException e) {
            throw new JwtException(e.getMessage());
        }

        if (Objects.isNull(jwtDecoder))
        {
            SecretKeySpec secretKeySpec = new SecretKeySpec(Secret_key.getBytes(), "HS512");
            jwtDecoder = NimbusJwtDecoder.withSecretKey(secretKeySpec)
                    .macAlgorithm(MacAlgorithm.HS512)
                    .build();
        }

        return jwtDecoder.decode(token);
    }
}
