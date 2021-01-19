package com.hcl.pp.service;

import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.hcl.pp.actions.PetsAppController;
import com.hcl.pp.dao.PetDAO;
import com.hcl.pp.dao.UserDAO;
import com.hcl.pp.model.Pet;
import com.hcl.pp.model.User;

@Service("petService")
public class PetServiceImpl implements PetService {

	@Autowired
	private PetDAO petDAO;
	private static Logger logger = (Logger) LogManager.getLogger(PetServiceImpl.class);

	@Override
	public boolean savePet(Pet pet) {
		logger.info("Saving pet");
		return petDAO.savePet(pet);
	}

	@Override
	public List<Pet> getAllPets() {
		logger.info("Fetching Pet");
		return petDAO.fetchAll();
	}

	@Override
	public Pet getPetById(long PET_ID) {
		logger.debug("Fetching pet by ID");
		return petDAO.getPetById(PET_ID);
	}

}
