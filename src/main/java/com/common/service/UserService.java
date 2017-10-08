package com.common.service;
import java.util.List;

import org.springframework.stereotype.Component;

import com.common.dao.security.User;

public interface UserService {
	public User listContact(String name);
}
