package main.java.com.popcornbuddy.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

public class JwtUtil {
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION_TIME = 86400000; // 토큰 만료 시간(24시간)
    private static final String SECRET_KEY = "BBBTTTCCCAAAMMMPPP000888";

    public static String generateToken(String id) {
        long currentTimeMillis = System.currentTimeMillis();
        return Jwts.builder()
            .setSubject(id)
            .setIssuedAt(new Date(currentTimeMillis))
            .setExpiration(new Date(currentTimeMillis + 1000 * 60 * 60)) // 토큰 만료 시간 설정 (1시간)
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
            .compact();
    }
}