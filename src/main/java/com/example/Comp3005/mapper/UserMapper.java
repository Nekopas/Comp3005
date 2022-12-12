package com.example.Comp3005.mapper;

import com.example.Comp3005.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface UserMapper {
    public ArrayList<User> findAllUser();
    public ArrayList<User> findUserById(long id);
    public ArrayList<User> findUserByAccount(String account);
    public ArrayList<User> findUserByName(String name);
    public ArrayList<User> findUserByPhoneNumber(String phoneNumber);
    public ArrayList<User> findUserByEmail(String email);
    public ArrayList<User> findUserByAccountAndAccess(String email,boolean access);
    public void insertUser(User p);
    public void updateUser(User p);
    public void deleteUserById(long id);
}
