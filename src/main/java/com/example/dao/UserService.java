package com.example.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.UserBean;

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;

	// Getting all user using using the method findAll
	public List<UserBean> getAllUser() {
		List<UserBean> userlist = (List<UserBean>) userRepository.findAll();
		return userlist;
	}

	// saving a specific record by using the method save() of CrudRepository
	public void save(UserBean user) {
		userRepository.save(user);
	}

	// find by id using CrudRepository
	public Optional<UserBean> getUserByUid(String uid) {
		return userRepository.findById(uid);
	}

	// updating a record
	public void update(UserBean user) {
		userRepository.save(user);
	}
	
	//delete a record
	public void delete(String uid) {
		userRepository.deleteById(uid);
	}
	
	//search by id or name;
	public List<UserBean> getAllUserByIdOrName(String uid, String name){
		return userRepository.findByUidContainingOrUsernameContaining(uid, name);
	}
	

}
