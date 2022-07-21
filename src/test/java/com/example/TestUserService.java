package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.dao.UserRepository;
import com.example.dao.UserService;
import com.example.model.UserBean;

@SpringBootTest
public class TestUserService {
	
	@Mock
	UserRepository userRepository;
	
	@InjectMocks
	UserService userService;
	
	@Test
	public void getAllUserTest(){
		
		List<UserBean> list = new ArrayList<UserBean>();
		UserBean bean = new UserBean();
		bean.setUid("USR001");
		bean.setUsername("Jame");
		bean.setPassword("123");
		
		UserBean bean1 = new UserBean();
		bean1.setUid("USR001");
		bean1.setUsername("Jame");
		bean1.setPassword("123");
		
		UserBean bean2 = new UserBean();
		bean2.setUid("USR001");
		bean2.setUsername("Jame");
		bean2.setPassword("123");
		
		list.add(bean);
		list.add(bean1);
		list.add(bean2);
		
		when(userRepository.findAll()).thenReturn(list);
		List<UserBean> userlist = userService.getAllUser();
		assertEquals(3,userlist.size());
		
		verify(userRepository, times(1)).findAll();	
	}
	
	@Test
	public void userSaveTest() {
		UserBean bean = new UserBean();
		bean.setUid("USR001");
		bean.setUsername("Snow");
		bean.setPassword("123");
		userService.save(bean);
		
		verify(userRepository, times(1)).save(bean);
		
	}
	
	@Test
	public void userUpdateTest() {
		UserBean bean = new UserBean();
		bean.setUid("USR001");
		bean.setUsername("Snow");
		bean.setPassword("123");
		userService.update(bean);
		
		verify(userRepository,times(1)).save(bean);
		
	}
	
	@Test
	public void userDeleteTest() {
		userService.delete("USR001");
		
		verify(userRepository,times(1)).deleteById("USR001");
	}
	
	@Test
	public void userSearchTest() {
		List<UserBean> list = new ArrayList<UserBean>();
		list = userService.getAllUserByIdOrName("USR001","John");
		
		verify(userRepository,times(1)).findByUidContainingOrUsernameContaining("USR001", "John");
		
	}
	
	@Test
	public void getUserByUidTest() {
		userService.getUserByUid("USR001");
		
		verify(userRepository,times(1)).findById("USR001");
	}

}
