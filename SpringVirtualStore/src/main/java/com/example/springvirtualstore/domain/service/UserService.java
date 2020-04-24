package com.example.springvirtualstore.domain.service;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.example.springvirtualstore.domain.model.UserMst;
import com.example.springvirtualstore.domain.repository.UserDao;

@Service
public class UserService {

	@Autowired
	@Qualifier("UserDaoJdbcImpl")
	UserDao dao;

	@Autowired
	PlatformTransactionManager txManager;

	public boolean insert(UserMst userMst) {
		// insert実行
		int rowNumber = dao.insertOne(userMst);
		// 判定用変数
		boolean result = false;
		if (rowNumber > 0) {
			// insert 成功
			result = true;
		}
		return result;
	}

	public int count() {
		return dao.count();
	}

	public List<UserMst> selectMany() {
		return dao.selectMany();
	}

	public UserMst selectOne(String userId) {
		return dao.selectOne(userId);
	}

	public boolean updateOne(UserMst userMst) throws DataAccessException {
		// インスタンス 生成
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		// 設定
		def.setName("UpdateUser");
		def.setReadOnly(false);
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		//トランザクション開始
		TransactionStatus status = txManager.getTransaction(def);
		//判定用変数
		boolean result = false;
		try {
			//１件更新
			int rowNumber = dao.updateOne(userMst);
			if (rowNumber > 0) {
				//update成功
				result = true;
			}
		} catch (Exception e) {
			//ロールバック
			txManager.rollback(status);
			throw new DataAccessException("ERROR Update", e) {
			};
		}
		//コミット    
		txManager.commit(status);

		return result;
	}

	public boolean deleteOne(String userId) {
		//１件削除
		int rowNumber = dao.deleteOne(userId);
		//判定用変数
		boolean result = false;
		if (rowNumber > 0) {
			//delete成功
			result = true;
		}
		return result;
	}

	public void userCsvOut() throws DataAccessException {
		//CSV出力
		dao.userCsvOut();
	}

	//サーバーに保存されているファイルを取得して、byte配列に変換する.
	public byte[] getFile(String fileName) throws IOException {
		//ファイルシステム（デフォルト）の取得
		FileSystem fs = FileSystems.getDefault();//ファイル取得
		Path p = fs.getPath(fileName);
		//ファイルをbyte配列に変換
		byte[] bytes = Files.readAllBytes(p);
		return bytes;
	}
}
