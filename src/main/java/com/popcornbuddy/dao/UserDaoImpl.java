package main.java.com.popcornbuddy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import main.java.com.popcornbuddy.domain.User;
import org.junit.Before;

public class UserDaoImpl implements UserDao { // User DB 접근

    private Connection conn;
    Calendar cal;


    @Before // 각 테스트가 수행되기 직전에 이 메서드가 실행된다.
    public void init() {
        cal = Calendar.getInstance();
        cal.clear();
        cal.set(2021, 1, 1);
    }

    public UserDaoImpl() throws SQLException {
        this.conn = DBConnection.getConnection();
    }

    public User selectUser(String id) throws Exception {
        User user = null;
        String sql = "SELECT * FROM account_user WHERE id = ? ";

        try (
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setId(rs.getString(1));
                    user.setEmail(rs.getString(2));
                    user.setPwd(rs.getString(3));
                    user.setName(rs.getString(4));
                    user.setProfile_img(rs.getString(5));
                    user.setCreated_date((rs.getTimestamp(6)));
                }
            }
        }
        System.out.printf("%s 조회", user != null ? user.getId() : "사용자가 없음");

        return user;
    }

    @Override
    public int deleteUser(String id) throws Exception { // id로 조회한 user를 삭제하는 메서드
        int rowCnt = 0;
        String sql = "DELETE FROM account_user WHERE id = ? ";

        try (
            PreparedStatement pstmt = conn.prepareStatement(sql); // 쿼리문 실행
        ) {
            pstmt.setString(1, id);
            System.out.println("삭제 되었습니다");
            return pstmt.executeUpdate();
        }

    }

    @Override
    public int insertUser(User user) throws Exception {
        int rowCnt = 0;
        String sql = "INSERT INTO account_user (id, email, pwd, name, profile_img, created_date) VALUES (?,?,?,?,?,?)";

        try (
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setString(1, user.getId());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPwd());
            pstmt.setString(4, user.getName());
            pstmt.setString(5, user.getProfile_img());
            pstmt.setTimestamp(6, new Timestamp(user.getCreated_date().getTime()));

            System.out.println("추가되었습니다.");
            return pstmt.executeUpdate();
        }
    }

    @Override
    public int updateUser(User user) throws Exception {
        int rowCnt = 0;

        String sql = "UPDATE account_user " +
            "SET pwd = ?, name = ?, profile_img = ? " +
            "WHERE id = ? ";
        try (
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.setString(1, user.getPwd());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getProfile_img());
            pstmt.setString(4, user.getId());

            rowCnt = pstmt.executeUpdate();
            System.out.println("업데이트 되었습니다.");
        }
        return rowCnt;
    }

    @Override
    public int count() throws Exception {
        String sql = "SELECT count(*) FROM account_user ";

        try (
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
        ) {
            rs.next();
            int result = rs.getInt(1);

            return result;
        }
    }

    @Override
    public void deleteAll() throws Exception {
        String sql = "DELETE FROM account_user ";

        try (
            PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            pstmt.executeUpdate();
            System.out.println("모두 삭제되었습니다");
        }
    }
}
