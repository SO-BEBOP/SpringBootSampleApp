package com.example.springvirtualstore.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.springvirtualstore.domain.form.SignupForm;
import com.example.springvirtualstore.domain.model.UserMst;
import com.example.springvirtualstore.domain.service.UserService;
import com.example.springvirtualstore.domain.valid.GroupOrder;

@Controller
public class SignupController {

	@Autowired
	private UserService userService;

	private Map<String, String> radioGender;

	//ラジオボタンの初期化メソッド  
	private Map<String, String> initRadioGender() {
		Map<String, String> radio = new LinkedHashMap<>();
		//既婚、未婚をMapに格納
		radio.put("Man", "man");
		radio.put("Woman", "woman");
		radio.put("Unselected", "unselected");

		return radio;
	}

	// ユーザー 登録 画面 の GET 用 コントローラー.   
	@GetMapping("/signup")
	public String getSignUp(@ModelAttribute SignupForm form, Model model) {
		model.addAttribute("contents", "signup :: signup_contents");
		// ラジオボタンの初期化メソッド呼び出し
		radioGender = initRadioGender();
		// ラジオボタン用のMapをModelに登録
		model.addAttribute("radioGender", radioGender);

		return "home";
	}

	@PostMapping("/signup")
	public String postSignUp(@ModelAttribute @Validated(GroupOrder.class) SignupForm form,
			BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			return getSignUp(form, model);
		}
		System.out.println(form);

		// insert 用 変数     
		UserMst userMst = new UserMst();

		userMst.setUser_name(form.getUserName());//ユーザー名
		userMst.setUser_password(form.getPassword());//パスワード
		userMst.setUser_birthday(form.getBirthday());//生年月日
		userMst.setUser_gender(form.getGender());//性別

		boolean result = userService.insert(userMst);//ユーザー登録結果の判定
		if (result == true) {
			System.out.println("insert成功");
		} else {
			System.out.println("insert失敗");
		}

		return "redirect:/login";
	}

}
