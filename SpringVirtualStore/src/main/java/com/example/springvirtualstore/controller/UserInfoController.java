package com.example.springvirtualstore.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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

	@RequestMapping(value = "/user_detail", method = RequestMethod.GET)
	public String getUserDetail(@ModelAttribute SignupForm form,
			@RequestParam(name = "userId", defaultValue = "non") String userId,
			@AuthenticationPrincipal UserDetailsImpl userDetails,
			Model model) {
		model.addAttribute("contents", "user_detail :: userdetail_contents");

		if (userId.equals("non")) {
			userId = String.valueOf(userDetails.getUserId());
		}

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
			if (userDetails.getAuthorities().toString().equals("[ROLE_ADMIN]")) {
				form.setPassword("CannotChanged");
			}
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
			@AuthenticationPrincipal UserDetailsImpl userDetails,
			BindingResult bindingResult, Model model) {

		// 結果判定変数
		boolean result = false;
		System.out.println("更新ボタンの処理");
		System.out.println("UP:userId=" + form.getUserId());

		if (bindingResult.hasErrors()) {
			return getUserDetail(form, String.valueOf(form.getUserId()), userDetails, model);
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

		// 管理者は処理後リストへ遷移。
		if (userDetails.getAuthorities().toString().equals("[ROLE_ADMIN]")) {
			// 既存のパスワードを更新させない。
			result = userService.updateInfo(userMst);
			if (result == true) {
				model.addAttribute("result", "更新成功");
				return getUserList(model);
			} else {
				model.addAttribute("result", "更新失敗");
				return getUserList(model);
			}
		} else {
			// パスワードを更新する。
			result = userService.updateOne(userMst);
		}
		// 一般ユーザはhomeへ遷移。
		return "redirect:/";
	}

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

	//ユーザー一覧のCSV出力用処理
	@GetMapping("/user_info/csv")
	public String userCsvOut(Model model) throws DataAccessException {
		// 拡張用 
		return getUserList(model);
	}

	//	//ユーザー一覧のCSV出力用処理
	//	@GetMapping("/userList/csv")
	//	public ResponseEntity<byte[]> getUserListCsv(Model model) {
	//
	//		userService.userCsvOut();
	//		byte[] bytes = null;
	//		try {
	//			bytes = userService.getFile("sample.csv");
	//		} catch (IOException e) {
	//			e.printStackTrace();
	//		}
	//		//HTTPヘッダーの設定
	//		HttpHeaders header = new HttpHeaders();
	//		header.add("Content-Type", "text/csv;charset=UTF-8");
	//		header.setContentDispositionFormData("filename", "sample.csv");
	//		//sample.csvを戻す
	//		return new ResponseEntity<>(bytes, header, HttpStatus.OK);
	//	}

}
