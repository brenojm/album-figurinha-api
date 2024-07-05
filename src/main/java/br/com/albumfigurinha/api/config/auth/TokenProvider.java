package br.com.albumfigurinha.api.config.auth;

import br.com.albumfigurinha.api.entity.User;

import br.com.albumfigurinha.api.exception.AuthenticationErrorException;
import br.com.albumfigurinha.api.exception.UserNotFoundException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenProvider {
    @Value("${security.jwt.token.secret-key}")
    private String JWT_SECRET;

    @Value("${security.jwt.token.expiration}")
    private int JWT_EXPIRATION_HR;

    public String generateAccessToken(User user) {

            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
            return JWT.create()
                    .withSubject(user.getUsername())
                    .withClaim("username", user.getUsername())
                    .withClaim("role", user.getRole().toString())
                    .withClaim("id", user.getId())
                    .withExpiresAt(genAccessExpirationDate())
                    .sign(algorithm);

    }

    public String validateToken(String token) {

            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
            return JWT.require(algorithm)
                    .build()
                    .verify(token)
                    .getSubject();

    }


    private Instant genAccessExpirationDate() {
        return LocalDateTime.now().plusHours(JWT_EXPIRATION_HR).toInstant(ZoneOffset.of("-03:00"));
    }
}
