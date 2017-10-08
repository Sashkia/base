package com.common.dao.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.common.dao.security.User;

@Repository
@Transactional
public class UserDAOImpl implements UserDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	public User listContact(String name) {
		System.out.println("here");
		Criteria criteria = sessionFactory
				.getCurrentSession()
				.createCriteria(User.class, "user")
				.add(Restrictions.ilike("user.username", (name),
						MatchMode.ANYWHERE));
		return (User) criteria.list().get(0);
	}

}
