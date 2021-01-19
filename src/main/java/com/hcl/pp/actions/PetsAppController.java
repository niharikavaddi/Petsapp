package com.hcl.pp.actions;

import java.util.NoSuchElementException;
import java.util.List;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.hcl.pp.model.Pet;
import com.hcl.pp.model.User;
import com.hcl.pp.service.PetService;
import com.hcl.pp.service.SecurityService;
import com.hcl.pp.service.UserService;

import jakarta.validation.Valid;

@Controller
@SessionAttributes("sessionuser")
public class PetsAppController {
	@Autowired
	private UserService userService;
	@Autowired
	private PetService petService;
	@Autowired
	private SecurityService securityService;
	private static Logger logger = (Logger) LogManager.getLogger(PetsAppController.class);

	@RequestMapping(value = "/user")
	public String welcome(Model model) {
		logger.info("Opened login page");
		model.addAttribute("user", new User());
		return "login";
	}

	@RequestMapping(value = "/user/add")
	public ModelAndView addUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {
		ModelAndView modelAndView = null;
		logger.info(user.getUserPassword() + " " + user.getConfirmPassword());
		if (bindingResult.getErrorCount() == 0 && user.getUserPassword().equals(user.getConfirmPassword())) {
			userService.addUser(user);
			modelAndView = new ModelAndView("registered");
		} else {
			logger.error(user.getUsername() + " " + user.getUserPassword() + " " + user.getConfirmPassword());
			modelAndView = new ModelAndView("userregn");
			modelAndView.addObject("nomatch", "Password and confirm password should be equal");
		}
		return modelAndView;
	}

	@RequestMapping(value = "/user/new")
	public String newUser(@ModelAttribute("user") User user) {
		logger.info("New User Entry");
		return "userregn";
	}

	@RequestMapping(value = "/user/authenticate")
	public ModelAndView authenticateUser(@ModelAttribute("user") User user, Model model) {
		ModelAndView modelAndView = null;
		try {
			User sessionuser = securityService.authenticateUser(user);
			modelAndView = new ModelAndView("pet_home");
			model.addAttribute("sessionuser", sessionuser);
		} catch (NoSuchElementException exception) {
			modelAndView = new ModelAndView("userregn");
			modelAndView.addObject("nouser", "No such user, Please register");
			logger.warn(user.getUsername() + " " + user.getUserPassword()
					+ " not in the database, redirecting to user registration page");
		}
		return modelAndView;
	};

	@RequestMapping(value = "/user/logout")
	public String logout(@ModelAttribute("sessionuser") User user) {
		logger.info(user.getUsername() + " logged out");
		return "login";
	}

	@RequestMapping(value = "/pets/myPets")
	public String myPets(@ModelAttribute("sessionuser") User user, Model model) {
		Set<Pet> pets = userService.getMyPets(user);
		model.addAttribute("pets", pets);
		logger.info("my pets info");
		return "my_pets";
	}

	@RequestMapping(value = "/pets/petDetail")
	public String petDetail(Model model) {
		model.addAttribute("pet", new Pet());
		logger.info("Adding pets");
		return "pet_form";
	}

	@RequestMapping(value = "/pets/addPet")
	public String addPet(@ModelAttribute("pet") Pet pet) {
		petService.savePet(pet);
		logger.debug(pet.getName() + " added");
		return "pet_home";
	}

	@RequestMapping(value = "/pets/buyPet", method = RequestMethod.GET)
	public String buyPet(@ModelAttribute("sessionuser") User user, @RequestParam long petId) {
		Pet pet = petService.getPetById(petId);
		userService.buyPet(pet, user);
		logger.debug(pet.getName() + " bought by" + user.getUsername());
		return "pet_home";
	}

	@RequestMapping(value = "/user/registered")
	public String registered(@ModelAttribute("user") User user) {
		logger.info(user.getUsername() + " registered");
		return "registered";
	}

	@RequestMapping(value = "/pets")
	public String petHome(Model model) {
		List<Pet> pets = petService.getAllPets();
		model.addAttribute("pets", pets);
		logger.info("directing to pet home page");
		return "pet_home";
	}
}
