package com.joaovictor.PhegonHotel.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Service
public class JWTutils {

    private static final long EXPIRATION_TIME = 1000 * 60 * 24 * 7;// 7 dias

    private final SecretKey secretKey;

    public JWTutils() {
        String secreteString = "843567893696976453275974432697R634976R738467TR678T34865R6834R8763T478378637664538745673865783678548735687R3";// chave secreta
        byte[] keyBytes = Base64.getDecoder().decode(secreteString.getBytes(StandardCharsets.UTF_8));// decodifica a chave secreta
        this.secretKey = new SecretKeySpec(keyBytes, "HmacSHA256");// cria a chave secreta
    }

    public String getSecretKey(UserDetails userDetails) {
        return Jwts.builder()// cria o token
                .subject(userDetails.getUsername())// adiciona o username
                .issuedAt(new Date(System.currentTimeMillis()))// adiciona a data de criação
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))// adiciona a data de expiração
                .signWith(secretKey)// adiciona a chave secreta
                .compact();// compacta o token
    }

    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims ,T> claimsTFunction) {
        return claimsTFunction.apply(Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload());// verifica o token
    }

    public boolean isvalidateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);// extrai o username do token
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));// verifica se o token é valido
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token, Claims::getExpiration).before(new Date());// verifica se o token expirou
    }
}
