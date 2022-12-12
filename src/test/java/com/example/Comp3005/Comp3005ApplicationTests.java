package com.example.Comp3005;

import com.example.Comp3005.entity.User;
import com.example.Comp3005.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class Comp3005ApplicationTests {

	@Autowired
	UserMapper userMapper;

	@Test
	public void findUser(){
		ArrayList<User> list = userMapper.findAllUser();
		System.out.println(list);
	}
}
