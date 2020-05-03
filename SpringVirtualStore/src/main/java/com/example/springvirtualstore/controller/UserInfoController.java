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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.springvirtualstore.domain.form.SignupForm;
import com.example.springvirtualstore.domain.model.UserMst;
import com.example.springvirtualstore.domain.service.UserService;

@Controller
public class UserInfoController {

	@Autowired
	private UserService userService;

	@GetMapping("/user_info")
	public String getUserList(Model model) {
		//コンテンツ部分にユーザー一覧を表示するための文字列を登録
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

	//ポイント１：ラジオボタンの実装  
	private Map<String, String> radioGender;

	//ラジオボタンの初期化メソッド  
	private Map<String, String> initRadioGender() {
		Map<String, String> radio = new LinkedHashMap<>();
		//既婚、未婚をMapに格納
		radio.put("男性", "male");
		radio.put("女性", "female");

		return radio;
	}

	// ポイント １： 動的 URL   // ポイント ２：
	@GetMapping("/user_detail/{id:.+}")
	public String getUserDetail(@ModelAttribute SignupForm form,
			@PathVariable("id") String userId, Model model) {

		//ユーザーID確認（デバッグ）
		System.out.println("userId=" + userId);

		//コンテンツ部分にユーザー詳細を表示するための文字列を登録
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

	// ポイント： ボタン 名 による メソッド 判定   // ユーザー 更新 用 処理.  
	@PostMapping(value = "/user_detail", params = "update")
	public String postUserDetailUpdate(@ModelAttribute SignupForm form,
			Model model) {
		System.out.println("更新ボタンの処理");
		System.out.println("userId=" + form.getUserId());

		UserMst userMst = new UserMst();
		//フォームクラスをUserクラスに変換
		userMst.setUser_id(form.getUserId());
		userMst.setUser_name(form.getUserName());
		userMst.setUser_birthday(form.getBirthday());
		userMst.setUser_gender(form.getGender());

		boolean result = userService.updateInfo(userMst);
		if (result == true) {
			model.addAttribute("result", "更新成功");
		} else {
			model.addAttribute("result", "更新失敗");
		} //ユーザー一覧画面を表示
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
		//ユーザー一覧画面を表示
		return getUserList(model);
	}

	//ユーザー一覧のCSV出力用処理.  
	@GetMapping("/userList/csv")
	public ResponseEntity<byte[]> getUserListCsv(Model model) {
		//ユーザーを全件取得して、CSVをサーバーに保存する
		userService.userCsvOut();
		byte[] bytes = null;
		try {//サーバーに保存されているsample.csvファイルをbyteで取得する
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

}
