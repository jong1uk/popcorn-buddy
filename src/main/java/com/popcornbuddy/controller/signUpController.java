package main.java.com.popcornbuddy.controller;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Scanner;
import javax.sound.midi.Soundbank;
import main.java.com.popcornbuddy.dao.DBConnection;
import main.java.com.popcornbuddy.dao.UserDao;
import main.java.com.popcornbuddy.dao.UserDaoImpl;
import main.java.com.popcornbuddy.domain.User;
import org.json.JSONObject;

public class signUpController {
    // 데이터베이스 연결 정보

    public static void signUp(String id, String email, String name, String pwd, String pwdCheck) {
        Calendar cal = Calendar.getInstance();

        String profile_img = "123";

            try {
                UserDao userDao = new UserDaoImpl();
                User user = userDao.selectUser(id);
                if(user==null&&pwd.equals(pwdCheck)){ // 해당 id로 조회한 아이디가 없고 pwd가 일치하면
                    user = new User(id, email, pwd, name, profile_img, new Timestamp(cal.getTimeInMillis()));
                    userDao.insertUser(user); // 회원가입 성공
                } else if (!pwd.equals(pwdCheck)) {
                    System.out.println("비밀번호와 비밀번호 확인이 일치하지 않습니다. 다시 시도해주세요.");
                    return;
                } else if (user.getId().equals(id)) {
                    System.out.println("동일한 id의 회원이 존재합니다..");
                    return;
                } else if (user.getEmail().equals(email)){
                    System.out.println("동일한 email의 회원이 존재합니다.");
            }} catch (Exception e) {
                System.out.println("null");
                throw new RuntimeException(e);
            }
            // 회원가입 완료
            System.out.println("회원가입이 완료되었습니다!");
    }
    }
