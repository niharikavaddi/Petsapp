package com.hcl.pp.service;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.pp.dao.UserDAO;
import com.hcl.pp.model.User;

@Service("securityService")
public class SecurityServiceImpl implements SecurityService {

	@Autowired
	private UserDAO userDAO;
	private static Logger logger = (Logger) LogManager.getLogger(SecurityServiceImpl.class);

	public User authenticateUser(User user) {

		logger.debug("Authenticating " + user.getUsername());
		return userDAO.listUsers().stream().filter(a -> a.getUsername().equals(user.getUsername()))
				.filter(a -> a.getUserPassword().equals(user.getUserPassword())).findFirst().get();
	}

}
