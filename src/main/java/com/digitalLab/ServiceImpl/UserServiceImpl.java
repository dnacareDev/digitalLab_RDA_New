package com.digitalLab.ServiceImpl;

import java.rmi.RemoteException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalLab.Entity.DataLog;
import com.digitalLab.Entity.UserConnect;
import com.digitalLab.Entity.Users;
import com.digitalLab.Mapper.DataLogMapper;
import com.digitalLab.Mapper.UserMapper;
import com.digitalLab.Service.ReportService;
import com.digitalLab.Service.UserService;
import com.digitalLab.Util.AuthUtil;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper usersMapper;
	
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private DataLogMapper dataLogMapper;

	// 유저 리스트
	@Override
	public List<Users> userList(Users user) {
		
		List<Users> list = usersMapper.usersList(user);
		
		for(Users data : list) {
			
			if(data.getUserType() != null) {
				data.setUserType(userTypeParse(data.getUserType()));
			}else {
				data.setUserType("-");
			}
		}
		
		return list;
	}

	// 유저 상세보기
	@Override
	public Users getUser(String account) {
		
		Users user = usersMapper.getUser(account);
		
		user.setUserType(userTypeParse(user.getUserType()));

		return user;
	}
	
	@Override
	public Users rdaUserLogin(String account) {
		return usersMapper.getUser(account);
	}
	
	// 유저 로그인
	@SuppressWarnings("finally")
	@Override
	public Users userLogin(Users rdaUser) {
		Users checkUser = usersMapper.getUser(rdaUser.getAccount());
		
		if(checkUser != null) {
			System.out.println("업데이트사용자");
			usersMapper.updateUser(rdaUser);
			
			//checkUser = usersMapper.getUser(rdaUser.getAccount());
			
			try {
				if(rdaUser.getAccount().equals("admin01") || rdaUser.getAccount().equals("user01") || rdaUser.getAccount().equals("user02")) {					
					reportService.addPrjDtl(rdaUser.getDigitalCode());
					System.out.println("과제등록1");
					
				}else {
					reportService.addPrjDtl(rdaUser.getDigitalCode());
					System.out.println("과제등록2");
				}
			} catch (RemoteException e) {
				System.out.println("과제 정보를 업데이트 하지 못했습니다");
			}finally {
				checkUser = usersMapper.getUser(rdaUser.getAccount());
			}
			
		}else {
			System.out.println("추가");
			usersMapper.addUser(rdaUser);
			
			try {
				if(rdaUser.getAccount().equals("admin01") || rdaUser.getAccount().equals("user01") || rdaUser.getAccount().equals("user02")) {
					
					reportService.addPrjDtl(rdaUser.getDigitalCode());
					
				}else {
					reportService.addPrjDtl(rdaUser.getDigitalCode());
				}
			} catch (RemoteException e) {
				System.out.println("과제 정보를 업데이트 하지 못했습니다");
			}finally {
				checkUser = usersMapper.getUser(rdaUser.getAccount());
			}
		}
		
		if(checkUser != null) {
			
			UserConnect loginCheck = usersMapper.userLoginCheck(checkUser.getAccount());
			
			if(loginCheck == null) {
				
				UserConnect userConnect = new UserConnect();
				userConnect.setAccount(checkUser.getAccount());
				userConnect.setUser_connect_id(usersMapper.getLoginSeqId());
				usersMapper.insertLoginCount(userConnect);
				
			}else {
				
				usersMapper.updateLoginCount(checkUser.getAccount());
			}
			
		}
		
		return checkUser;
	}

	@Override
	public Users selectUserByAccount(String account) {
		return usersMapper.selectUserByAccount(account);
	}
		
	public Users getLoginUser() {
		Users user = AuthUtil.sessionUser();
		return  user;
	}

	@Override
	public List<UserConnect> userLoginList(String startDate, String endDate) {
		Users user = AuthUtil.sessionUser();
		
		return usersMapper.userLoginList(user, startDate, endDate);
	}
	
	@Override
	public List<UserConnect> userAnalysisStatus(String startDate, String endDate) {
		// TODO Auto-generated method stub
		
		Users user = AuthUtil.sessionUser();
		
		return usersMapper.userAnalysisStatus(user);
	}

	@Override
	public int userHomeCheck(String type, int checkvalue) {

		Users user = AuthUtil.sessionUser();
		
		System.out.println(type);
		System.out.println(checkvalue);
		
		if(type.equals("1")) {
			user.setAnalysis_check(checkvalue);
		} else if(type.equals("2")) {
			user.setMethod_check(checkvalue);
		} else if(type.equals("3")) {
			user.setStorage_check(checkvalue);
		} else if(type.equals("4")) {
			user.setResearcher_check(checkvalue);
		}
		
		return usersMapper.updateHomeCheck(user, type);
	}

	@Override
	public int authorityModify(Users user) {
		
		Users sessionUser = AuthUtil.sessionUser();
		
		int result = 0;
		
		if(sessionUser.getAuthority().equals("ADMIN") || (sessionUser.getAuthority().equals("MANAGER") && sessionUser.getGroupCode().equals(user.getGroupCode())) ) {
			result = usersMapper.authorityModify(user);
		}
		
		return result;
	}
	
	public String userTypeParse(String userType) {
		
		switch (userType) {
		case "G": userType = "정규직";
			break;
		case "T": userType = "계약직";
			break;
		default : userType = "-";
			break;
		}
		return userType;
	}
	
	@Override
	public List<DataLog> selectDataLogList() {
		
		Users user = AuthUtil.sessionUser();
		
		List<DataLog> list = dataLogMapper.selectDataLogList(user);
		
		for(DataLog log : list) {
			
			switch (log.getData_type()) {
			case "analysis":
				log.setData_type("분석");
				break;
			case "equipment":
				log.setData_type("장비");
				break;
			case "instrument":
				log.setData_type("기구");
				break;
			case "method":
				log.setData_type("실험방법");
				break;
			case "place":
				log.setData_type("장소");
				break;
			case "reagent":
				log.setData_type("시약");
				break;
			case "seed":
				log.setData_type("시료");
				break;
			case "storage":
				log.setData_type("저장고");
				break;
			case "verification":
				log.setData_type("결과");
				break;
			case "note":
				log.setData_type("연구노트");
				break;
			case "repository":
				log.setData_type("레포지터리");
				break;
			}
			
			switch (log.getAction_type()) {
				case 1:
					log.setAction("등록");
					break;
				case 2:
					log.setAction("수정");
					break;
				case 3:
					log.setAction("삭제");
					break;
				case 4:
					if(log.getData_type().equals("analysis")) {
						log.setAction("분석 설정");
					}else {
						log.setAction("추가");
					}
					break;
				case 5:
					log.setAction("시료 분석");
					break;
				case 6:
					log.setAction("결과 등록");
					break;
				case 7:
					log.setAction("연구노트 전송 성공");
					break;
				case 8:
					log.setAction("연구노트 전송 실패");
					break;
				case 9:
					log.setAction("리포지터리 전송 성공");
					break;
			}
		}
		return list;
	}
	
}