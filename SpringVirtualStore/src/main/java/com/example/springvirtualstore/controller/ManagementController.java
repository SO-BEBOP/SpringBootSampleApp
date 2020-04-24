package com.example.springvirtualstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ManagementController {

	@GetMapping("/management")
	public String geManagiment(Model model) {

		model.addAttribute("contents", "management :: management_contents");

		return "home";
	}
}