package com.example.springvirtualstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

	@GetMapping("/login")
	public String getLogin(Model model) {
		model.addAttribute("contents", "login :: login_contents");

		return "home";
	}

	@PostMapping("/login")
	public String postLogin(Model model) {
		model.addAttribute("contents", "blank :: blank_contents");

		return "redirect:home";
	}

}
