package com.digitalLab.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.digitalLab.Entity.UserConnect;
import com.digitalLab.Entity.Users;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
	
	public List<Users> usersList(Users user);
	
	public Users getUser(String account);
	
	public int addUser(Users user);
	
	public int updateUser(Users user);
	
	public int totalCount(Users user);
	
	public Users selectUserByAccount(String account);
	
	public int updateHomeCheck(@Param("user") Users user , @Param("type") String type);
	
	public int authorityModify(Users user);
	
	// 유저 로그인 체크
	public int insertLoginCount(UserConnect userConnect);
	public int updateLoginCount(String account);
	public List<UserConnect> userLoginList(@Param("user") Users user, @Param("startDate") String startDate, @Param("endDate") String endDate);
	public UserConnect userLoginCheck(String account);
	public int getLoginSeqId();
	
	// 유저 연구자 리스트
	public List<UserConnect> userAnalysisStatus(@Param("user") Users user);
}
