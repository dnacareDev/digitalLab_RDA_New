package com.digitalLab.Service;

import java.util.List;

import com.digitalLab.Entity.DataLog;
import com.digitalLab.Entity.UserConnect;
import com.digitalLab.Entity.Users;

public interface UserService
{
	public List<Users> userList(Users user);
	
	public Users getUser(String account);
	
	public Users rdaUserLogin(String account);
	
	public Users userLogin(Users rdaUser);
	
	public Users selectUserByAccount(String account);

	public Users getLoginUser();
	
	public List<UserConnect> userLoginList(String startDate, String endDate);

	public List<UserConnect> userAnalysisStatus(String startDate, String endDate);
	
	public int userHomeCheck(String type , int checkvalue);
	
	public int authorityModify(Users user);
	
	public List<DataLog> selectDataLogList();
}