package com.example.springvirtualstore.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.example.springvirtualstore.domain.model.CartTbl;
import com.example.springvirtualstore.domain.repository.CartDao;

@Service
public class CartService {
	@Autowired
	@Qualifier("CartDaoJdbcImpl")
	CartDao dao;

	@Autowired
	PlatformTransactionManager txManager;

	public boolean insert(CartTbl cartTbl) {
		// insert実行
		int rowNumber = dao.insertOne(cartTbl);
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

	public List<CartTbl> selectMany() {
		return dao.selectMany();
	}

	public List<CartTbl> selectManyFromStateParam(String userId, String cartState) {
		return dao.selectManyFromStateParam(userId, cartState);
	}

	public CartTbl selectOne(String cartId) {
		return dao.selectOne(cartId);
	}

	public boolean updateOne(CartTbl cartTbl) throws DataAccessException {
		// インスタンス 生成
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		// 設定
		def.setName("UpdateCart");
		def.setReadOnly(false);
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		//トランザクション開始
		TransactionStatus status = txManager.getTransaction(def);
		//判定用変数
		boolean result = false;
		try {
			//１件更新
			int rowNumber = dao.updateOne(cartTbl);
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

	public boolean updateOneFromStateParam(Integer userId) throws DataAccessException {
		// インスタンス 生成
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		// 設定
		def.setName("UpdateCart");
		def.setReadOnly(false);
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		//トランザクション開始
		TransactionStatus status = txManager.getTransaction(def);
		//判定用変数
		boolean result = false;
		try {
			//１件更新
			int rowNumber = dao.updateOneFromStateParam(userId);
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

	public boolean deleteOne(String cartId) {
		//１件削除
		int rowNumber = dao.deleteOne(cartId);
		//判定用変数
		boolean result = false;
		if (rowNumber > 0) {
			//delete成功
			result = true;
		}
		return result;
	}

	//	public void cartCsvOut() throws DataAccessException {
	//		//CSV出力
	//		dao.cartCsvOut();
	//	}

	//サーバーに保存されているファイルを取得して、byte配列に変換する.
	//	public byte[] getFile(String fileName) throws IOException {
	//		//ファイルシステム（デフォルト）の取得
	//		FileSystem fs = FileSystems.getDefault();//ファイル取得
	//		Path p = fs.getPath(fileName);
	//		//ファイルをbyte配列に変換
	//		byte[] bytes = Files.readAllBytes(p);
	//		return bytes;
	//	}
}
