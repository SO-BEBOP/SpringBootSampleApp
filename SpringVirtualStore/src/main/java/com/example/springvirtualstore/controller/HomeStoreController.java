package com.example.springvirtualstore.controller;

import java.security.Principal;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.springvirtualstore.domain.repository.UserDetailsImpl;

@Controller
public class HomeStoreController {

	@GetMapping("/")
	public String getHomeStore(Principal principal, @AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
		model.addAttribute("contents", "blank :: blank_contents");

		return "/home";
	}

	@PostMapping("/logout")
	public String postLogout() {

		return "redirect:/home";
	}

	// TODO 購入処理
	@PostMapping("/")
	public void postBuyBtnRequest(@RequestParam() String str, Model model) {

	}
}
