package com.example.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.model.UserBean;

@Repository
public interface UserRepository extends CrudRepository<UserBean,String> {
	List<UserBean> findByUidContainingOrUsernameContaining(String uid, String username);
}
