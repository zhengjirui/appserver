package com.zhengjr.app;

//import org.springframework.data.annotation.Id;
//
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//
//
//@Entity
public class UserInfo {

//    @Id
//    @GeneratedValue
    private String name;
    private String nick;
    private int age;
    private String sex;
    private String address;

    public UserInfo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
