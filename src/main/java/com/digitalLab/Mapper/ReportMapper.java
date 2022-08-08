package com.digitalLab.Mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.digitalLab.Entity.Report;
import com.digitalLab.Entity.ReportManager;
import com.digitalLab.Entity.UserReport;
import com.digitalLab.Entity.Users;

@Mapper
public interface ReportMapper {
	
	// 연동 과제
	public List<Report> selectReportList(@Param("user") Users user , @Param("type") String type);
	
	public Report selectReportById(int report_id);
	
	public int insertReport (HashMap<String,Object> map);
	
	public int updateReport (HashMap<String,Object> map);
	public Report checkPrj(String prj_no);
	public int getSeqId();
	
	// 과제 담당자
	public int insertManager(ReportManager reportManager);
	public int getManagerSeqId();
	
	// 유저 과제
	public int insertUserReport(UserReport userReport);
	public int getUserReportSeqId();
	public int updateUserReport(UserReport userReport);
	public UserReport selectUserReportById(int user_report_id);
	public UserReport checkUserReport(UserReport userReport);

	public List<Report> selectReportListByPlan(Users user);


	int deleteUserReport(int seed_id);

	int deleteReportAssignments(int seed_id);

	int getUserReportIdCount(int seed_id);

	int updateReportComment(Report report);
	
	int testReportInsert(Report report);
	
	public String selectMainManager(@Param("type") String type, @Param("prj_dtl_no") String prj_dtl_no);
	
}
