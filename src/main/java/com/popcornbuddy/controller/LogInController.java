package main.java.com.popcornbuddy.controller;

import java.sql.SQLException;
import main.java.com.popcornbuddy.dao.UserDao;
import main.java.com.popcornbuddy.dao.UserDaoImpl;
import main.java.com.popcornbuddy.domain.User;

public class LogInController {
//    JwtUtil;


    // 사용자 인증을 위한 가짜 메서드 (실제로는 데이터베이스와 연결하여 사용자를 확인해야 함)
    public boolean authenticateUser (String id, String pwd) throws Exception {

        UserDao userDao = new UserDaoImpl();
        // 여기에 실제 사용자 인증 로직을 구현합니다.
        // 예를 들어, 데이터베이스에서 사용자 정보를 가져와 비밀번호와 일치하는지 확인합니다.
        // 이 예시에서는 간단히 사용자 이름이 "admin"이고 비밀번호가 "password"인 경우에만 통과하도록 설정합니다.
        User user = userDao.selectUser(id); // id로 DB의 유저 데이터 불러오기

        return user.getId().equals(id) && user.getPwd().equals(pwd);
    }

    private boolean loginCheck(String id, String pwd) throws SQLException {
        User user = null;
        UserDao userDao = new UserDaoImpl();
        try {
            user = userDao.selectUser(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return user != null && user.getPwd().equals(pwd);
//        return "asdf".equals(id) && "1234".equals(pwd);
    }
}

