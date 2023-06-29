package com.amigoscode.chohort2.carRental.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class JWTUtil {

    private static final String SECRET_KEY =
            "foobar_123456789_foobar_123456789_foobar_123456789_foobar_123456789_foobar_123456789_foobar_123456789";


    public String issueToken(String subject, List<SimpleGrantedAuthority> authorities) {

        return issueToken(subject, Map.of("authorities", authorities));
    }

    public String issueToken(String subject,
                             Map<String, Object> claims) {


        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuer("https://car-rental.com")
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(15, ChronoUnit.DAYS)))
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getSubject(String token) {

        return getClaims(token).getSubject();
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }

    private Key getSigninKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public boolean isTokenValid(String jwt, String username) {
        return getSubject(jwt).equals(username) && !isTokenExpired(jwt);
    }

    private boolean isTokenExpired(String jwt) {

        return getClaims(jwt).getExpiration().before(Date.from(Instant.now()));
    }
}
