package com.balasamajam.jwt;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    // what is the difference between the token generated for the same user?
    // we use time stamp. what happens a user token is created at the exact same time ?
    // one potential solution is to create a uuid and add it as a claim attribute.
    // since the uuid is random, the jwt token will always be uniquie.
    // but this requires we must store the uuid in the server. which again defeats the jwt purpose. Think!
    public String doGenerateToken(String username)
    {
        System.out.println("Generating token");
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
        return Jwts.builder()
                .setSubject(username)
                .signWith(key)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600))
                .compact();
    }

    public boolean isValidToken(String token)
    {
        try
        {
            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .isSigned(token);
        }
        catch (Exception e)
        {
            System.out.println("JWT token is not valid.");
            return false;
        }
    }
}
