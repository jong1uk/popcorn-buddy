package main.java.com.popcornbuddy.dao;

import main.java.com.popcornbuddy.domain.User;

public interface UserDao { // User DB 접근
    User selectUser(String id) throws Exception;
    int deleteUser(String id) throws Exception;
    int insertUser(User user) throws Exception;
    int updateUser(User user) throws Exception;
    int count() throws Exception;
    void deleteAll() throws Exception;
}