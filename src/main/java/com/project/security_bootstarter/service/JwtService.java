package com.project.security_bootstarter.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private String secretKey =
            "ef00f5ee141e1d9d8154775f50ed98918ab1f858fb811741941bacaddb18a1062469941354ef2d17f0ce8a7641b5aadcc94b87cf010e79c97b086a3fdedbf87ec834abd33d1452b3ee68e34351cf17be5762f14271de511e4b3c778bae5b3955a5e15db0185712d0bdc2cbbbb77927545f93e79c5ee2b637182d2b7199cb4a4c2c7b8dbdf207e10abfcca91c54973d1f364452b09fe141020d86a54c96939f5ff08475bbec2b08fec20babbe76056fcce8b03c14e01f0cefd7476092d43114e012c0339d9ac515ebb9edb74ef253fe23399fc7816b7a98374d14eeceaef588aa88e17c8c8ac642f4daf82ed4693959c6d72b595c7d875c415b4dd2ab641dcda4";
    private long jwtExpiration = 3600;


    public String extractUsername(String token){
        return extractClaims(token, Claims::getSubject);
    }
    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails){
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long jwtExpiration){
        return Jwts.builder()
                .claims().add(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .and()
                .signWith(getSignKey())
                .compact();
    }
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        if(isTokenExpired(token)) return false;
        return username.equals(userDetails.getUsername());
    }
    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }
    private Claims extractAllClaims(String token){
        return Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims(token).getPayload();
    }
    private SecretKey getSignKey(){
        byte[] bytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(bytes);
    }
}
