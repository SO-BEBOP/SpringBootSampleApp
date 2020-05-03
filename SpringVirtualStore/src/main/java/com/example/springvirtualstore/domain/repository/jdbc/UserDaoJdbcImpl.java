package com.example.springvirtualstore.domain.repository.jdbc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.springvirtualstore.domain.model.UserMst;
import com.example.springvirtualstore.domain.repository.UserDao;

@Repository("UserDaoJdbcImpl")
public class UserDaoJdbcImpl implements UserDao {

	@Autowired
	JdbcTemplate jdbc;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public int count() throws DataAccessException {

		//全件取得してカウント
		int count = jdbc.queryForObject(
				"SELECT COUNT(*) FROM user_mst", Integer.class);
		return count;
	}

	@Override
	public int insertOne(UserMst userMst) throws DataAccessException {

		//パスワード暗号化
		String password = passwordEncoder.encode(userMst.getUser_password());

		//ユーザーテーブルに１件登録するSQL
		String sql = "INSERT INTO user_mst("
				+ " user_name,"
				+ " user_password,"
				+ " user_birthday,"
				+ " user_gender"
				+ ")VALUES(?,?,?,?)";
		//１件挿入
		int rowNumber = jdbc.update(sql,
				userMst.getUser_name(),
				password,
				userMst.getUser_birthday(),
				userMst.getUser_gender());

		return rowNumber;
	}

	@Override
	public UserMst selectOne(String userId) throws DataAccessException {
		//１件取得
		Map<String, Object> map = jdbc.queryForMap("SELECT * FROM user_mst" + " WHERE user_id=?", userId);
		//結果返却用の変数
		UserMst userMst = new UserMst();
		//取得したデータを結果返却用の変数にセットしていく
		userMst.setUser_id((Integer) map.get("user_id"));
		userMst.setUser_name((String) map.get("user_name"));
		userMst.setUser_password((String) map.get("user_password"));
		userMst.setUser_birthday((Date) map.get("user_birthday"));
		userMst.setUser_gender((String) map.get("user_gender"));
		userMst.setUser_state((Integer) map.get("user_state"));
		userMst.setUser_role((String) map.get("user_role"));
		userMst.setCreate_at((Date) map.get("create_at"));
		userMst.setUpdata_at((Date) map.get("updata_at"));

		return userMst;
	}

	@Override
	public List<UserMst> selectMany() throws DataAccessException {
		//user_mstテーブルのデータを全件取得
		List<Map<String, Object>> getList = jdbc.queryForList("SELECT * FROM user_mst");
		//結果返却用の変数
		List<UserMst> userList = new ArrayList<>();
		//取得したデータを結果返却用のListに格納していく
		for (Map<String, Object> map : getList) {
			//Userインスタンスの生成
			UserMst userMst = new UserMst();
			//Userインスタンスに取得したデータをセットする
			userMst.setUser_id((Integer) map.get("user_id"));
			userMst.setUser_name((String) map.get("user_name"));
			userMst.setUser_password((String) map.get("user_password"));
			userMst.setUser_birthday((Date) map.get("user_birthday"));
			userMst.setUser_gender((String) map.get("user_gender"));
			userMst.setUser_state((Integer) map.get("user_state"));
			userMst.setUser_role((String) map.get("user_role"));
			userMst.setCreate_at((Date) map.get("create_at"));
			userMst.setUpdata_at((Date) map.get("updata_at"));

			//結果返却用のListに追加
			userList.add(userMst);
		}
		return userList;
	}

	@Override
	public int updateOne(UserMst userMst) throws DataAccessException {

		//パスワード暗号化
		String password = passwordEncoder.encode(userMst.getUser_password());

		//１件更新するSQL
		String sql = "UPDATE user_mst SET"
				+ " user_name = ?,"
				+ " user_password = ?,"
				+ " user_birthday = ?,"
				+ " user_gender = ?"
				+ " WHERE"
				+ " user_id = ?";
		//１件更新
		int rowNumber = jdbc.update(sql,
				userMst.getUser_name(),
				password,
				userMst.getUser_birthday(),
				userMst.getUser_gender(),
				userMst.getUser_id());

		return rowNumber;
	}

	@Override
	public int updateInfo(UserMst userMst) throws DataAccessException {

		//１件更新するSQL
		String sql = "UPDATE user_mst SET"
				+ " user_name = ?,"
				+ " user_birthday = ?,"
				+ " user_gender = ?"
				+ " WHERE"
				+ " user_id = ?";
		//１件更新
		int rowNumber = jdbc.update(sql,
				userMst.getUser_name(),
				userMst.getUser_birthday(),
				userMst.getUser_gender(),
				userMst.getUser_id());

		return rowNumber;
	}

	@Override
	public int deleteOne(String userId) throws DataAccessException {
		//１件削除
		int rowNumber = jdbc.update("DELETE FROM user_mst WHERE user_id = ?", userId);
		return rowNumber;
	}

	@Override
	public void userCsvOut() throws DataAccessException {
		//テーブルのデータを全件取得するSQL
		String sql = "SELECT ∗ FROM user_mst";
		//ResultSetExtractorの生成
		UserRowCallbackHandler handler = new UserRowCallbackHandler();
		//SQL実行＆CSV出力
		jdbc.query(sql, handler);
	}
}
