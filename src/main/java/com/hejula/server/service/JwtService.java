package com.hejula.server.service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jooyeon
 * @since 2021.07.17
 */
@Service
@Slf4j
public class JwtService {

    @Value("${custom.secret-key}")
    private String secretKey;

    public String createToken(String userId) throws JwtException {
        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        header.put("alg", "HS256");

        Date exp = new Date();
        exp.setTime(exp.getTime() + 86400000); //유효기간 : 하루

        String token = Jwts.builder()
                .setHeader(header)
                .setIssuer("com.hejula.server")
                .setExpiration(exp)
                .setSubject("hejulaServer")
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();

        return token;
    }

    public boolean checkVerifyToken(String jwt) throws Exception {
        Jwts.parser()
                .setSigningKey(secretKey.getBytes("UTF-8"))
                .parseClaimsJws(jwt)
                .getBody();

        return true; // 파싱 및 검증 실패시에 위에서 exception 발생
    }
}
