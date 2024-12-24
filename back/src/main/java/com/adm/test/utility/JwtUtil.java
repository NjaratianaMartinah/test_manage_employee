package com.adm.test.utility;


import com.adm.test.utility.exception.TokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;


@Component
public class JwtUtil {

    @Value("${jwt.token.secret}")
    private String tokenSecret;

    @Value("${jwt.token.duration}")
    private int tokenDuration;

    public String generateToken(String userName, @Nullable final Map<String, Object> additionalClaims) {
        Objects.requireNonNull(userName, "User Name for token generation must not be null");
        JwtBuilder jwtBuilder = Jwts.builder()
                .issuer("ERP")
                .subject(userName)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + tokenDuration))
                .signWith(SignatureAlgorithm.HS256, tokenSecret);

        if (!Objects.isNull(additionalClaims)) {
            jwtBuilder.claims(additionalClaims);
        }
        return jwtBuilder.compact();
    }

    public String extractUserName(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    private Date extractExpirationDate(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        Date expirationDate = extractExpirationDate(token);
        return expirationDate.before(new Date());
    }

    public void validateToken(String token, String userName) throws TokenException {
        if (!userName.equals(extractUserName(token))) {
            throw new TokenException("Mismatch between token infos and username");
        }

        if (isTokenExpired(token)) {
            throw new TokenException("The token is expired");
        }
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(tokenSecret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }


}