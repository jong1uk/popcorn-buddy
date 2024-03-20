package main.java.com.popcornbuddy.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class TokenGenerator {

    // 토큰 길이
    private static final int TOKEN_LENGTH = 32;

    // 안전한 난수 생성기
    private static SecureRandom secureRandom = new SecureRandom();

    // 토큰 생성 메서드
    public static String generateToken() {
        byte[] randomBytes = new byte[TOKEN_LENGTH];
        secureRandom.nextBytes(randomBytes);

        // 랜덤한 바이트 배열을 Base64 인코딩하여 문자열로 변환
        String token = Base64.getEncoder().encodeToString(randomBytes);

        // 해시화하여 더욱 안전한 토큰 생성
        return hashToken(token);
    }

    // 토큰을 해시화하는 메서드
    private static String hashToken(String token) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(token.getBytes());

            // 바이트 배열을 Base64 인코딩하여 문자열로 변환하여 반환
            return Base64.getEncoder().encodeToString(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            // 암호화 알고리즘이 지원되지 않을 경우 빈 문자열 반환
            return "";
        }
    }
}
