package com.common.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.dao.entity.UserDAO;
import com.common.dao.security.User;

@Service
@Transactional
public class UserServiceImpl implements UserService{
	@Autowired
	private UserDAO userDAO;
	
	
	

	@Transactional
	public User listContact(String name) {
		return userDAO.listContact(name);
	}

}
