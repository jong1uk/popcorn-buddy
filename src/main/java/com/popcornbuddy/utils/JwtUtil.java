//package main.java.com.popcornbuddy.utils;
//
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jws;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//import java.security.Key;
//import java.util.Date;
//import javax.xml.crypto.Data;
//
//public class JwtUtil {
//    private static final long EXPIRATION_TIME = 86400000; // 토큰 만료 시간(24시간)
//    private static final String SECRET_KEY = "BBBTTTCCCAAAMMMPPP000888";
//    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//
//
//    // 토큰 검증 메서드
//    public static boolean validateToken(String jwtToken) {
//        try {
//            Jwts.parserBuilder().set
//        }
//
//    }
//
//    // 토큰에서 사용자 ID 추출 메서드
//    public static String extractUserId(String jwtToken) {
//        try {
//            Jwts.parserBuilder()
//                .setSigningKey(SECRET_KEY)
//                .build()
//                .parseClaimsJws(jwtToken);
//            return claimsJws.getBody().getSubject();
//        } catch (Exception e) {
//            return null;
//        }
//    }
//    public static String generateToken(String id) {
//        long currentTimeMillis = System.currentTimeMillis();
//        return Jwts.builder()
//            .setSubject(id) // 유저 id를 담아서 생성
//            .setIssuedAt(new Date(currentTimeMillis))
//            .setExpiration(new Date(currentTimeMillis + 1000 * 60 * 60)) // 토큰 만료 시간 설정 (1시간)
//            .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
//            .compact();
//    }
//
//}