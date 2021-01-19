package com.hcl.pp.service;

import java.util.List;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.pp.dao.UserDAO;
import com.hcl.pp.model.Pet;
import com.hcl.pp.model.User;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDAO userDAO;
	private static Logger logger = (Logger) LogManager.getLogger(UserServiceImpl.class);

	@Override
	public User addUser(User user) {
		logger.info("Adding User");
		return userDAO.addUser(user);
	}

	@Override
	public User updateUser(User user) {
		logger.warn("Updation by " + user.getUsername());
		return userDAO.updateUser(user);
	}

	@Override
	public List<User> listUsers() {
		logger.debug("Fetching list of users");
		return userDAO.listUsers();
	}

	@Override
	public User getUserById(long USER_ID) {
		logger.debug("Getting User by ID");
		return userDAO.getUserById(USER_ID);
	}

	@Override
	public boolean removeUser(User user) {
		logger.warn(user.getUsername() + " removed");
		return userDAO.removeUser(user);
	}

	@Override
	public User findByUserName(String username) {
		logger.debug("Checking if " + username + " exists");
		return userDAO.findByUserName(username);
	}

	@Override
	public boolean buyPet(Pet pet, User user) {
		logger.info(pet.getName() + " bought by " + user.getUsername());
		return userDAO.buyPet(pet, user);
	}

	@Override
	public Set<Pet> getMyPets(User user) {
		logger.info("Getting " + user.getUsername() + "/'s pets");
		return userDAO.getMyPets(user);
	}

}
