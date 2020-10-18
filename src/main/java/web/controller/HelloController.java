package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import web.model.User;
import web.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class HelloController {

	// Constructor based Dependency Injection
	private UserService userService;

	public HelloController() {

	}
    @Autowired
	public HelloController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping(value = "/")
	public String printWelcome(ModelMap model) {
		List<String> messages = new ArrayList<>();
		messages.add("Hello!");
		messages.add("I'm Spring MVC application");
		messages.add("5.2.0 version by sep'19 ");
		model.addAttribute("messages", messages);
		return "index";
	}

	@GetMapping(value = "/allusers")
	public String allUsersGet(Model model) {
		List<User> users = userService.getAllUsers();
		model.addAttribute("usersFromServer", users);
		return "/allusers";
	}

	@GetMapping("/new")
	public String newPerson(@ModelAttribute("person") User user) {
		return "new";
	}

	@PostMapping("/save")
	public String create(@ModelAttribute("person") User user) {
		userService.saveUser(user);
		return "redirect:/allusers";
	}

	@GetMapping(value="/edituser/{id}")
	public ModelAndView updateUserForm(@PathVariable("id") Long id, Model model) {
			ModelAndView modelAndView = new ModelAndView();
			User user = userService.getUserById(id);
			model.addAttribute("user", user);
			modelAndView.setViewName("/edituser");
			return modelAndView;
	}

	@PostMapping(value="/edituser")
	public String saveUser(User user) {
		ModelAndView modelAndView = new ModelAndView();
		userService.editUser(user);
		List<User> users = userService.getAllUsers();
		modelAndView.addObject("usersFromServer", users);
		return "redirect:/allusers";
	}
	@GetMapping("user-delete/{id}")
	public String deleteUser(@PathVariable("id") Long id){
		userService.deleteUser(id);
		return "redirect:/allusers";
	}
}