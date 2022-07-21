package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.dao.UserRepository;
import com.example.dao.UserService;
import com.example.model.UserBean;

@SpringBootTest
@AutoConfigureMockMvc
public class TestUserController {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	UserService userService;

	@MockBean
	UserRepository userRepository;

	// testing user loginValidate
	@Test
	public void loginValidateTest() throws Exception {
		this.mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("LGN001"));
	}

	// testing user login
	@Test
	public void loginTest() throws Exception {
		UserBean bean = new UserBean();
		bean.setUid("USR001");
		bean.setUsername("Snow");
		bean.setPassword("123");

		this.mockMvc.perform(post("/login").flashAttr("user", bean).sessionAttr("userid", bean.getUid()))
				.andExpect(status().isOk()).andExpect(view().name("MNU001"));
	}

	// testing user logout
	@Test
	public void logoutTest() throws Exception {
		this.mockMvc.perform(get("/logout")).andExpect(view().name("redirect:/"));
	}

	// testing user display all
	@Test
	public void displayAllUserTest() throws Exception {

		this.mockMvc.perform(get("/USR003")).andExpect(status().isOk()).andExpect(model().attributeExists("userlist"))
				.andExpect(view().name("USR003"));
	}
	
	//testing auto generated id
	@Test
	  public void testIdAutoGenerate() throws Exception {
        UserBean user = new UserBean();
        user.setUid("USR001");
        user.setUsername("Snow");
        user.setPassword("123");
        
        List<UserBean> userList = new ArrayList<>();
        userList.add(user);
        
        Mockito.when(userRepository.findAll()).thenReturn(userList);
        assertEquals(0, userList.size());
        
        this.mockMvc.perform(get("/setupadduser").flashAttr("userbean", user))
        .andExpect(status().isOk())
        .andExpect(view().name("USR001"))
        .andExpect(model().attributeExists("userbean"));
    }

	// testing setupadduser
	@Test
	public void setupadduserTest() throws Exception {
		
		this.mockMvc.perform(get("/setupadduser"))
		.andExpect(status().isOk())
		.andExpect(view().name("USR001"))
		.andExpect(model().attributeExists("userbean"));
	}

	// testing adduser validate
	@Test
	public void adduserValidateTest() throws Exception {
		this.mockMvc.perform(post("/adduser")).andExpect(status().isOk()).andExpect(view().name("USR001"));
	}

	// testing adduser
	@Test
	public void testadduser() throws Exception {
		UserBean userBean = new UserBean();
		userBean.setUid("USR001");
		userBean.setUsername("Snow");
		userBean.setPassword("123");

		this.mockMvc.perform(post("/adduser").flashAttr("userbean", userBean)).andExpect(status().is(302))
				.andExpect(redirectedUrl("/USR003"));
	}

	// testing setupUpdateUser method
	@Test
	public void testsetupupate() throws Exception {
		
		UserBean bean = new UserBean();
		bean.setUid("BKK-11");
		bean.setUsername("Andrew");
		bean.setPassword("123");
		Optional<UserBean> beanOptional = Optional.of(bean);
		when(userService.getUserByUid("BKK-11")).thenReturn(beanOptional);

		this.mockMvc.perform(get("/setupUpdateUser").param("uid", "BKK-11"))
		.andExpect(status().isOk())
		.andExpect(view().name("USR002"))
		.andExpect(model().attributeExists("userbean"));
	}

	// testing updateUser method
	@Test
	public void updateuservalidatetest() throws Exception {
		this.mockMvc.perform(post("/updateuser")).andExpect(status().isOk()).andExpect(view().name("USR002"));
	}

	// testing updateUser method
	@Test
	public void updateusertest() throws Exception {
		UserBean bean = new UserBean();
		bean.setUid("USR001");
		bean.setUsername("Snow");
		bean.setPassword("123");

		this.mockMvc.perform(post("/updateuser").flashAttr("userbean", bean)).andExpect(status().is(302))
				.andExpect(redirectedUrl("/USR003"));
	}

	// testing deleteUser method
	@Test
	public void deleteUser() throws Exception {
		this.mockMvc.perform(get("/deleteUser").param("uid", "USR001")).andExpect(status().is(302))
				.andExpect(redirectedUrl("/USR003"));

	}

	// testing searchuser method
	@Test
	public void searchUserTest() throws Exception {
		this.mockMvc.perform(post("/searchuser").param("uid", "USR001").param("username", "Snow"))
				.andExpect(status().isOk()).andExpect(model().attributeExists("userlist"))
				.andExpect(view().name("USR003"));
	}

}