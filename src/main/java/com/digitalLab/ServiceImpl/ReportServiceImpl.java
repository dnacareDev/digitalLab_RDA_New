package com.digitalLab.ServiceImpl;

import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.digitalLab.Service.UserService;
import com.digitalLab.Util.AuthUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.digitalLab.Entity.Report;
import com.digitalLab.Entity.ReportManager;
import com.digitalLab.Entity.Seed;
import com.digitalLab.Entity.UserReport;
import com.digitalLab.Entity.Users;
import com.digitalLab.Mapper.ReportMapper;
import com.digitalLab.Service.ReportService;
import com.digitalLab.atis.prjdtlinfo.PrjDtlInfoProxy;
import com.digitalLab.atis.prjdtlinfo.PrjDtlInfoVo;

@Service
public class ReportServiceImpl implements ReportService {

	@Value("${prjDtlInfoVo.setSysId}")
	private String sysId;
	@Value("${prjDtlInfoVo.setSysPwd}")
	private String sysPwd;

	@Autowired
	private ReportMapper reportMapper;

	@Autowired
	private UserService userService;

	// 리포트 리스트
	@Override
	public Map<String, Object> reportList(String type) {

		Map<String, Object> result = new HashMap<String, Object>();

		Users user = AuthUtil.sessionUser();
		List<Report> list = reportMapper.selectReportList(user, type);

		if (list != null) {
			result.put("list", list);
			result.put("result", 1);
		} else {
			result.put("result", 1);
		}

		return result;
	}
	
	@Override
	public String selectMainManager(String type, String prj_dtl_no) {

		String manager_name = reportMapper.selectMainManager(type, prj_dtl_no);

		

		return manager_name;
	}

	// 리포트 상세보기
	@Override
	public Report selectReportById(int report_id) {
		return reportMapper.selectReportById(report_id);
	}

	// 과제연동 등록
	@Override
	public int addPrjDtl(String dic_code) throws RemoteException {

		int result = 1;

		Object[] reportArr = prjDtlList(dic_code);
		
		if(dic_code.equals("F123-23")) {
			
			List<Report> list = new ArrayList<Report>();
			
			Report report = new Report();
			report.setPrj_no("PJ015567");
			report.setPrj_nm("농경지 토양물리성 변동 평가 및 지표개발");
			report.setTot_rsch_start_year("2021");
			report.setTot_rsch_end_dt("20251231");
			report.setPrj_dtl_no("PJ01556709");
			report.setPrj_dtl_nm("농경지 토양물리성 변동 평가 및 지표개발(9공동)");
			report.setTot_rsch_start_year("2021");
			report.setTot_rsch_end_year("2025");
			report.setPrj_dtl_rspr_dic_code("F123-23");
			
			list.add(report);
			
			report = new Report();
			report.setPrj_no("PJ015567");
			report.setPrj_nm("바이오차의 온실가스 감축사업 적용 방법론 개발 및 최적 활용 조건 구명");
			report.setTot_rsch_start_year("2021");
			report.setTot_rsch_end_dt("20251231");
			report.setPrj_dtl_no("PJ01556801");
			report.setPrj_dtl_nm("바이오차의 온실가스 감축사업 적용 방법론 개발 및 최적 화용 조건 구명(1주관)");
			report.setTot_rsch_start_year("2021");
			report.setTot_rsch_end_year("2025");
			report.setPrj_dtl_rspr_dic_code("F123-23");
			
			list.add(report);
			
			for(Report re : list) {
				
				result = reportMapper.testReportInsert(re);
			}
			
		}else {
			
			for (Object item : reportArr) {
				
				HashMap<String, Object> map = (HashMap<String, Object>) item;
				
				Report checkReport = reportMapper.checkPrj((String) map.get("PRJ_DTL_NO"));
				
				if (checkReport == null) {
					
					int seq_id = reportMapper.getSeqId();
					map.put("report_id", seq_id);
					
					result = reportMapper.insertReport(map);
					
					System.out.println(result);
					if (result == 0) {
						System.out.println("과제 업로드 문제 발생");
						return 0;
					}
					
					result = addManager(map);
					
					if (result == 0) {
						System.out.println("담당자 업로드 문제 발생");
						return 0;
					}
					
					// 중복 체크 후 업데이트
				} else {
					reportMapper.updateReport(map);
				}
			}
		}
		

		return result;
	}

	// 담당자 등록
	@Override
	public int addManager(HashMap<String, Object> map) {

		String[] dicCodeArr = ((String) map.get("PRTCP_MP_DIC_CODE_LIST")).split(";");

		int result = 1;

		for (String dic_code : dicCodeArr) {
			ReportManager manager = new ReportManager();

			int seq_id = reportMapper.getManagerSeqId();

			manager.setReport_manager_id(seq_id);
			manager.setDic_code(dic_code);
			manager.setManager_type(2);
			manager.setReport_id((Integer) map.get("report_id"));
			if (dic_code.equals((String) map.get("PRJ_DTL_RSPR_DIC_CODE"))) {
				manager.setManager_type(1);
			}

			result = reportMapper.insertManager(manager);
		}

		return result;
	}

	// 과제 리스트
	@Override
	public Map<String, Object> selectRerpotListByPlan() {
		Map<String, Object> result = new HashMap<String, Object>();

		Users user = userService.getLoginUser();
		List<Report> list = reportMapper.selectReportListByPlan(user);

		result.put("list", list);
		result.put("result", 1);
		return result;
	}

	// 과제 연동
	private Object[] prjDtlList(String dic_code) throws RemoteException {

		PrjDtlInfoProxy prjDtlInfo = new PrjDtlInfoProxy();
		PrjDtlInfoVo prjDtlInfoVo = new PrjDtlInfoVo();

		/*******************
		 * 아이디 패스워드 입력부분
		 ********************/
		prjDtlInfoVo.setSysId(sysId);
		System.out.println("sysId : "+sysId);
		prjDtlInfoVo.setSysPwd(sysPwd);
		System.out.println("sysPwd : "+sysPwd);
		prjDtlInfoVo.setFindRowPerPage(100); // 뷰 카운트
		prjDtlInfoVo.setFindPrtcpMpDicCode(dic_code);
		/*******************
		 * 결과
		 ********************/
		prjDtlInfoVo = prjDtlInfo.getPrjDtlInfo(prjDtlInfoVo);
		System.out.println("====================");
		System.out.println("메세지코드=" + prjDtlInfoVo.getReaultFlag());
		System.out.println("메세지=" + prjDtlInfoVo.getResultMsg());
		System.out.println("리스트 수 =" + prjDtlInfoVo.getTotalRow());

		if (prjDtlInfoVo.getReaultFlag().equals("0")) {
			HashMap mp = new HashMap();
			System.out.println(prjDtlInfoVo.getPrjDtlInfoList().length);
			for (int i = 0; i < prjDtlInfoVo.getPrjDtlInfoList().length; i++) {
				//System.out.println("==============getPrjInfoList" + i + "=" + prjDtlInfoVo.getPrjDtlInfoList()[i]);
//				if(1 == i){
//					mp.putAll((HashMap)prjDtlInfoVo.getPrjDtlInfoList()[i]) ;
//				}
				mp.put("prjList", prjDtlInfoVo.getPrjDtlInfoList());
			}
		}

		return prjDtlInfoVo.getPrjDtlInfoList();
	}

	@Override
	public int seedReportCheck(Seed seed, int USEE_AT_CODE) {

		Users user = AuthUtil.sessionUser();

		int check_id = 0;
		int user_report_id = 0;

		// 유저 리포트 생성
		UserReport report = new UserReport();
		report.setAccount(user.getAccount());

		// 과제 연계
		if (!seed.isReport_chk()) {

			// 과제 등록 체크
			report.setReport_id(seed.getReport_id());
			report.setUSEE_AT_CODE(USEE_AT_CODE); // 0 : 일반 1: 간편 2: 템플릿
			UserReport check = reportMapper.checkUserReport(report);

			if (check == null) {
				user_report_id = reportMapper.getUserReportSeqId();

				report.setUser_report_id(user_report_id);
				report.setReport_comment(seed.getReport_comment());

				reportMapper.insertUserReport(report);
			} else {
				user_report_id = check.getUser_report_id();

				check.setReport_comment(seed.getReport_comment());
				reportMapper.updateUserReport(check);

			}

			// 과제 비연계
		} else {

			check_id = reportMapper.getSeqId();
			// 2021
			LocalDate now = LocalDate.now();
			int year = now.getYear();
			// N202112162052
			String prj_dtl_no = "N" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));

			// 비연계 과제 체크
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("report_id", check_id);
			map.put("TOT_RSCH_START_YEAR", Integer.toString(year));
			map.put("PRJ_DTL_NO", prj_dtl_no);
			map.put("PRJ_DTL_NM", "과제비연계");
			map.put("PRTCP_MP_DIC_CODE_LIST", user.getDigitalCode());
			map.put("PRJ_DTL_RSPR_DIC_CODE", user.getDigitalCode());
			reportMapper.insertReport(map);
			addManager(map);

			user_report_id = reportMapper.getUserReportSeqId();

			// 비연계 과제 등록
			report.setUser_report_id(user_report_id);
			report.setReport_id(check_id);
			report.setUSEE_AT_CODE(USEE_AT_CODE);
			report.setReport_comment(seed.getReport_comment());
			reportMapper.insertUserReport(report);

		}

		return user_report_id;
	}
	
	
//	
	public List<Report> testReport() {
		
		List<Report> list = new ArrayList<Report>();
		
		Report report = new Report();
		report.setPrj_no("PJ015567");
		report.setPrj_nm("농경지 토양물리성 변동 평가 및 지표개발");
		report.setTot_rsch_start_year("2021");
		report.setTot_rsch_end_dt("20251231");
		report.setPrj_dtl_no("PJ01556709");
		report.setPrj_dtl_nm("농경지 토양물리성 변동 평가 및 지표개발(9공동)");
		report.setTot_rsch_start_year("2021");
		report.setTot_rsch_end_year("2025");
		report.setPrj_dtl_rspr_dic_code("F123-23");
		
		list.add(report);
		
		report = new Report();
		report.setPrj_no("PJ015567");
		report.setPrj_nm("바이오차의 온실가스 감축사업 적용 방법론 개발 및 최적 활용 조건 구명");
		report.setTot_rsch_start_year("2021");
		report.setTot_rsch_end_dt("20251231");
		report.setPrj_dtl_no("PJ01556801");
		report.setPrj_dtl_nm("바이오차의 온실가스 감축사업 적용 방법론 개발 및 최적 화용 조건 구명(1주관)");
		report.setTot_rsch_start_year("2021");
		report.setTot_rsch_end_year("2025");
		report.setPrj_dtl_rspr_dic_code("F123-23");
		
		list.add(report);
		
		return list;
	}
	
	
}
