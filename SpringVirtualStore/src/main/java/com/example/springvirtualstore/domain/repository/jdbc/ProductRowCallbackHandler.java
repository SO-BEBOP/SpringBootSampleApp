package com.example.springvirtualstore.domain.repository.jdbc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowCallbackHandler;

public class ProductRowCallbackHandler implements RowCallbackHandler {
	@Override
	public void processRow(ResultSet rs) throws SQLException {
		try {
			File file = new File("sample.csv");
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			//取得件数分loop
			do {
				//ResultSetから値を取得してStringにセット
				String str = rs.getString("user_id")
						+ "," + rs.getString("password")
						+ "," + rs.getString("user_name")
						+ "," + rs.getDate("user_birthday")
						+ "," + rs.getString("user_gender")
						+ "," + rs.getInt("user_state")
						+ "," + rs.getString("user_role")
						+ "," + rs.getDate("create_at")
						+ "," + rs.getDate("updata_at");

				//ファイルに書き込み＆改行
				bw.write(str);
				bw.newLine();

			} while (rs.next());
			//強制的に書き込み＆ファイルクローズ
			bw.flush();
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
			throw new SQLException(e);
		}
	}
}
