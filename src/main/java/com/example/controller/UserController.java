package com.example.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.dao.UserService;
import com.example.model.UserBean;

@Controller
public class UserController {
	@Autowired
	private UserService userService;

	@ModelAttribute("userbean")
	public UserBean getUserBean() {
		return new UserBean();
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String displayView(ModelMap model) {
		return "LGN001";
	}

	@PostMapping("/login")
	public String login(@ModelAttribute(name = "user") UserBean uesrbean, HttpSession session, Model m) {
		String uid = uesrbean.getUid();
		String pass = uesrbean.getPassword();
		if (uid.equals("USR001") && pass.equals("123")) {

			session.setAttribute("userid", uid);
			
			return "MNU001";
		}
		m.addAttribute("error", "Incorrect Username & Password");
		return "LGN001";

	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("userid");
		session.invalidate();
		return "redirect:/";
	}

	@RequestMapping(value = "/USR003", method = RequestMethod.GET)
	public String displayAllUser(ModelMap model) {
		List<UserBean> list = userService.getAllUser();
		model.addAttribute("userlist", list);
		return "USR003";
	}

	@RequestMapping(value = "/setupadduser", method = RequestMethod.GET)
	public ModelAndView setupadduser() {
		UserBean userbean = new UserBean();
		List<UserBean> userlist = userService.getAllUser();
		if(userlist.size() == 0) {
			userbean.setUid("USR001");
		}else {
			int temId = Integer.parseInt(userlist.get(userlist.size()-1).getUid().substring(3))+1;
			String uid = String.format("USR%03d", temId);
			userbean.setUid(uid);
		}
		return new ModelAndView("USR001", "userbean", userbean);
	}

	@RequestMapping(value = "/adduser", method = RequestMethod.POST)
	public String adduser(@ModelAttribute("userbean") @Validated UserBean bean, BindingResult bs, ModelMap model) {
		if (bs.hasErrors()) {
			return "USR001";
		}
		UserBean dto = new UserBean();
		dto.setUid(bean.getUid());
		dto.setUsername(bean.getUsername());
		dto.setPassword(bean.getPassword());

		userService.save(dto);
		return "redirect:/USR003";
	}

	@RequestMapping(value = "/setupUpdateUser", method = RequestMethod.GET)
	public ModelAndView setupUpdateUser(@RequestParam("uid") String uid) {
		return new ModelAndView("USR002", "userbean", userService.getUserByUid(uid));
	}

	@RequestMapping(value = "/updateuser", method = RequestMethod.POST)
	public String updateuser(@ModelAttribute("userbean") @Validated UserBean bean, BindingResult bs, ModelMap model) {
		if (bs.hasErrors()) {
			return "USR002";
		}
		UserBean dto = new UserBean();
		dto.setUid(bean.getUid());
		dto.setUsername(bean.getUsername());
		dto.setPassword(bean.getPassword());
		
		userService.update(dto);
		
		return "redirect:/USR003";
	}

	@RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
	public String deleteUser(@RequestParam("uid") String uid, ModelMap model) {
		userService.delete(uid);
		return "redirect:/USR003";
	}

	@RequestMapping(value = "/searchuser", method = RequestMethod.POST)
	public String searchuser(@RequestParam("uid") String uid, @RequestParam("username") String username,
			ModelMap model) {

		String id = uid.isBlank() ? "#@#(" : uid;
		String name = username.isBlank() ? "#@#(" : username;

		List<UserBean> searchlist = null;
		searchlist = userService.getAllUserByIdOrName(id, name);
		if (searchlist.size() == 0) {
			searchlist = userService.getAllUser();
		}
		model.addAttribute("userlist", searchlist);
		return "USR003";

	}

}
