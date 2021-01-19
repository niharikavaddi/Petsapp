package com.hcl.pp.dao;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hcl.pp.actions.PetsAppController;
import com.hcl.pp.model.Pet;
import com.hcl.pp.model.User;

@Transactional
@Repository("userDAO")
public class UserDAOImpl implements UserDAO {
	@Autowired
	private SessionFactory sessionFactory;
	private static Logger logger = (Logger) LogManager.getLogger(PetsAppController.class);

	@Override
	public User addUser(User user) {
		Session session = sessionFactory.getCurrentSession();
		session.persist(user);
		logger.info(user.getUsername() + " added");
		return user;
	}

	@Override
	public User updateUser(User user) {
		logger.info(user.getUsername() + " updated");
		return null;
	}

	@Override
	public List<User> listUsers() {
		Session session = sessionFactory.getCurrentSession();
		List<User> users = session.createNamedQuery("listUsers").getResultList();
		logger.debug("Authenticating user");
		return users;
	}

	@Override
	public User getUserById(long USER_ID) {
		Session session = sessionFactory.getCurrentSession();
		logger.info("fetching user by id");
		return session.get(User.class, USER_ID);
	}

	@Override
	public boolean removeUser(User user) {
		Session session = sessionFactory.getCurrentSession();
		session.get(User.class, user.getId());
		session.remove(user);
		logger.info("User removed");
		return true;
	}

	@Override
	public User findByUserName(String username) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from User user where user.username=:name");
		query.setParameter("name", username);
		User user = (User) query.getSingleResult();
		if (user != null) {
			logger.info(user.getUsername() + " fetched");
			return user;
		} else {
			logger.info("Couldn't fetch user");
			return null;
		}
	}

	@Override
	public boolean buyPet(Pet pet, User user) {
		Session session = sessionFactory.getCurrentSession();
		pet.setUser(user);
		session.update(pet);
		logger.info(pet.getName() + " bought by" + user.getUsername());
		return true;
	}

	@Override
	public Set<Pet> getMyPets(User user) {
		Session session = sessionFactory.getCurrentSession();
		session.get(User.class, user.getId());
		logger.info("Fetching all pets");
		return user.getPets();

	}

}
