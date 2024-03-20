package test;

import static junit.framework.TestCase.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import org.junit.Before;
import org.junit.Test;
import main.java.com.popcornbuddy.dao.DBConnection;
import main.java.com.popcornbuddy.domain.User;
import main.java.com.popcornbuddy.dao.UserDao;
import main.java.com.popcornbuddy.dao.UserDaoImpl;

public class UserDaoImplTest {
    private UserDao userDao;
    private Calendar cal;

    private Connection conn;

    @Before
    public void setUp() throws SQLException {
        this.conn = DBConnection.getConnection();
        this.userDao = new UserDaoImpl();
        this.cal = Calendar.getInstance();
    }

    @Test(expected = Exception.class) // 예외가 발생해야 테스트 통과
    public void insertUser() throws Exception {
        userDao.deleteAll();
        assertTrue(userDao.count()==0);

        User user = new User("asdf", "aaa3@naver.com", "qwer1234", "babo", "image", new Timestamp(cal.getTimeInMillis()));
        assertTrue(userDao.insertUser(user)==1);
        assertTrue(userDao.count()==1);

        User user2 = userDao.selectUser(user.getId());
        assertTrue(user.equals(user2));

        User user3 = new User("asdf", "aaa3@naver.com", "qwer1234", "babo", "image", new Timestamp(cal.getTimeInMillis()));
        assertTrue(userDao.insertUser(user3)==1);
        assertTrue(userDao.count()==2);

        // 같은 데이터를 2번 입력하고 예외가 발생하는지 테스트
        userDao.insertUser(user);  // java.sql.SQLIntegrityConstraintViolationException예외발생. Duplicate entry 'asdf' for key 'PRIMARY'
    }

    @Test
    public void deleteUser() throws Exception {
        userDao.deleteAll();
        assertTrue(userDao.count()==0);

        User user = new User("asdf", "aaa@naver.com", "qwer1234", "babo", "image", new Timestamp(cal.getTimeInMillis()));
        assertTrue(userDao.insertUser(user)==1);
        assertTrue(userDao.count()==1);

        User user2 = userDao.selectUser(user.getId());
        assertTrue(user.equals(user2));
        assertTrue(userDao.deleteUser(user.getId())==1);

        user = userDao.selectUser("asdf");
        assertTrue(user==null);
        assertTrue(userDao.count()==0);
    }

    @Test
    public void selectUser() throws Exception {
        userDao.deleteAll();
        assertTrue(userDao.count()==0);

        User user = new User("asdf", "aaa@naver.com", "qwer1234", "babo", "image", new Timestamp(cal.getTimeInMillis()));
        assertTrue(userDao.insertUser(user)==1);

        System.out.println(user.getId());
        User user2 = userDao.selectUser(user.getId());
        assertTrue(user.equals(user2));

        user2 = userDao.selectUser("aaaaaaa");
        assertTrue(user2==null);
    }

    @Test
    public void updateUser() throws Exception {
        userDao.deleteAll();
        User user = new User("asdf", "aaa@naver.com", "qwer1234", "babo", "image", new Timestamp(cal.getTimeInMillis()));
        int rowCnt = userDao.insertUser(user);
        assertTrue(rowCnt==1);

        user.setPwd("4321");
        user.setEmail("bbb@bbb.com");
        rowCnt = userDao.updateUser(user);
        assertTrue(rowCnt==1);

        User user2 = userDao.selectUser(user.getId());
        System.out.println("user = " + user);
        System.out.println("user2 = " + user2);
        assertTrue(user.equals(user2));
    }
}
