package main.java.com.popcornbuddy.domain;

import java.sql.Timestamp;
import java.util.Objects;

public class User {
    private String id;
    private String email;
    private String pwd;
    private String name;
    private String profile_img;
    private Timestamp created_date;

    public User(){} // User Entity, DB의 account_user 테이블과 매칭된 User 클래스
    public User(String id, String email, String pwd, String name, String profile_img, Timestamp created_date){
        this.id = id;
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.profile_img = profile_img;
        this.created_date = created_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id.equals(user.id) && email.equals(user.email) && pwd.equals(user.pwd)
            && name.equals(
            user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, pwd, name, profile_img, created_date);
    }

    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", email='" + email + '\'' +
            ", pwd='" + pwd + '\'' +
            ", name='" + name + '\'' +
            ", profile_img='" + profile_img + '\'' +
            ", created_date=" + created_date +
            '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_img() {
        return profile_img;
    }

    public void setProfile_img(String profile_img) {
        this.profile_img = profile_img;
    }

    public Timestamp getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Timestamp created_date) {
        this.created_date = created_date;
    }
}
