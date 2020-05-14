package com.example.springvirtualstore.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.springvirtualstore.domain.form.SignupForm;
import com.example.springvirtualstore.domain.model.UserMst;
import com.example.springvirtualstore.domain.repository.UserDetailsImpl;
import com.example.springvirtualstore.domain.service.UserService;
import com.example.springvirtualstore.domain.valid.GroupOrder;

@Controller
public class UserInfoController {

	@Autowired
	private UserService userService;
	private Map<String, String> radioGender;

	private Map<String, String> initRadioGender() {
		Map<String, String> radio = new LinkedHashMap<>();
		radio.put("Man", "man");
		radio.put("Woman", "woman");
		radio.put("Unselected", "unselected");

		return radio;
	}

	@GetMapping("/user_info")
	public String getUserList(Model model) {
		model.addAttribute("contents", "user_info :: userinfo_contents");
		//ユーザー一覧の生成
		List<UserMst> userList = userService.selectMany();
		//Modelにユーザーリストを登録
		model.addAttribute("userList", userList);
		//データ件数を取得
		int count = userService.count();
		model.addAttribute("userListCount", count);

		return "home";
	}

	@GetMapping("/user_info/csv")
	public String userCsvOut(Model model) throws DataAccessException {
		// 拡張用 
		return getUserList(model);
	}

	//ユーザー一覧のCSV出力用処理.  
	@GetMapping("/userList/csv")
	public ResponseEntity<byte[]> getUserListCsv(Model model) {

		userService.userCsvOut();
		byte[] bytes = null;
		try {
			bytes = userService.getFile("sample.csv");
		} catch (IOException e) {
			e.printStackTrace();
		}
		//HTTPヘッダーの設定
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "text/csv;charset=UTF-8");
		header.setContentDispositionFormData("filename", "sample.csv");
		//sample.csvを戻す
		return new ResponseEntity<>(bytes, header, HttpStatus.OK);
	}

	// 管理者用
	@GetMapping("/user_detail/{id:.+}")
	public String getUserDetail(@ModelAttribute SignupForm form,
			@PathVariable("id") String userId, Model model) {
		model.addAttribute("contents", "user_detail :: userdetail_contents");

		//性別ステータス用ラジオボタンの初期化
		radioGender = initRadioGender();
		//ラジオボタン用のMapをModelに登録
		model.addAttribute("radioGender", radioGender);
		//ユーザーIDのチェック
		if (userId != null && userId.length() > 0) {
			//ユーザー情報を取得
			UserMst userMst = userService.selectOne(userId);
			//Userクラスをフォームクラスに変換
			form.setUserId(Integer.parseInt(userId));
			form.setUserName(userMst.getUser_name());
			form.setBirthday(userMst.getUser_birthday());
			form.setGender(userMst.getUser_gender());

			//Modelに登録
			model.addAttribute("SignupForm", form);

		}
		return "home";
	}

	@PostMapping(value = "/user_detail", params = "update")
	public String postUserDetailUpdate(
			@ModelAttribute @Validated(GroupOrder.class) SignupForm form,
			BindingResult bindingResult, Model model) {

		System.out.println("更新ボタンの処理");
		System.out.println("UP:userId=" + form.getUserId());

		if (bindingResult.hasErrors()) {
			return getUserDetail(form, String.valueOf(form.getUserId()), model);
		}
		System.out.println(form);
		String gender = form.getGender();
		if (gender == null) {
			gender = "unselected";
		}
		UserMst userMst = new UserMst();
		//フォームクラスをUserクラスに変換
		userMst.setUser_id(form.getUserId());
		userMst.setUser_name(form.getUserName());
		userMst.setUser_password(form.getPassword());
		userMst.setUser_birthday(form.getBirthday());
		userMst.setUser_gender(form.getGender());

		// パスワードは更新させない。
		boolean result = userService.updateInfo(userMst);
		if (result == true) {
			model.addAttribute("result", "更新成功");
		} else {
			model.addAttribute("result", "更新失敗");
		}
		return getUserList(model);
	}

	//ユーザー削除用処理.
	@PostMapping(value = "/user_detail", params = "delete")
	public String postUserDetailDelete(@ModelAttribute SignupForm form, Model model) {
		System.out.println("削除ボタンの処理");
		boolean result = userService.deleteOne(String.valueOf(form.getUserId()));
		if (result == true) {
			model.addAttribute("result", "削除成功");
		} else {
			model.addAttribute("result", "削除失敗");
		}
		return getUserList(model);
	}

	// 一般用
	@GetMapping("/login_user_detail")
	public String getLoginUserDetail(@ModelAttribute SignupForm form,
			@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
		model.addAttribute("contents", "user_detail :: userdetail_contents");

		// ラジオボタンの初期化メソッド呼び出し
		radioGender = initRadioGender();
		//ラジオボタン用のMapをModelに登録
		model.addAttribute("radioGender", radioGender);
		//ユーザー情報を取得
		UserMst userMst = userService.selectOne(String.valueOf(userDetails.getUserId()));
		//Userクラスをフォームクラスに変換
		form.setUserId(userDetails.getUserId());
		form.setUserName(userMst.getUser_name());
		form.setBirthday(userMst.getUser_birthday());
		form.setGender(userMst.getUser_gender());
		model.addAttribute("SignupForm", form);

		return "home";
	}

	@PostMapping(value = "/login_user_detail", params = "update")
	public String postUserDetailUpdate(@ModelAttribute @Validated(GroupOrder.class) SignupForm form,
			BindingResult bindingResult, @AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {

		if (bindingResult.hasErrors()) {
			return getLoginUserDetail(form, userDetails, model);
		}
		String gender = form.getGender();
		if (gender == null) {
			gender = "unselected";
		}
		UserMst userMst = new UserMst();
		userMst.setUser_id(form.getUserId());
		userMst.setUser_name(form.getUserName());
		userMst.setUser_password(form.getPassword());
		userMst.setUser_birthday(form.getBirthday());
		userMst.setUser_gender(form.getGender());

		// パスワードを更新許可
		userService.updateOne(userMst);

		return "redirect:/login";
	}
}
