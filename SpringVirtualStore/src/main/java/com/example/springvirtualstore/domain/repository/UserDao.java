package com.example.springvirtualstore.domain.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.springvirtualstore.domain.model.LoginUserDetails;
import com.example.springvirtualstore.domain.model.UserMst;

public interface UserDao {

	public int count() throws DataAccessException;

	public int insertOne(UserMst userMst) throws DataAccessException;

	public UserMst selectOne(String userId) throws DataAccessException;

	public LoginUserDetails selectLoginUser(String userName) throws DataAccessException;

	public List<UserMst> selectMany() throws DataAccessException;

	public int updateOne(UserMst userMst) throws DataAccessException;

	public int updateInfo(UserMst userMst) throws DataAccessException;

	public int deleteOne(String userId) throws DataAccessException;

	public void userCsvOut() throws DataAccessException;

}
