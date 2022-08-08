package com.digitalLab.Entity;

import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter	
@Alias("Report")
public class Report {
	
	private int report_id;
	private String account;
	private String report_comment;
	private int USEE_AT_CODE;
	
	// 변경 과제
	private String rnd_phase_nm;
	private String kor_kywd;
	private String tot_rsch_end_dt;
	private String prj_type;
	private String prtcp_mp_dic_code_list;
	private String prj_dtl_no;
	private String tot_rsch_start_year;
	private String prj_dtl_nm;
	private String prtcp_mp_list;
	private int srownum;
	private String update_dt;
	private String main_rspr_top_dept_nm;
	private String sub_rspr_top_dept_nm;
	private String sub_pspr_nm;
	private String prj_no;
	private String rndco_tot_amt;
	private int year_cnt;
	private String main_rspr_dept_nm;
	private String prj_nm;
	private String prj_dtl_rspr_dic_code;
	private String tot_rsch_end_year;
	private String sub_rspr_dept_nm;
	private String main_rspr_nm;
	private String prj_type_nm;
	
	// detail
	private List<Seed> seedList;
	private String regist_type;
	private String genetic_depth1;
	private String genetic_depth2;
	private String place_name;
    private String seed_sender;
    private int seed_count;
    private String send_date;
    private String receive_date;
	
	public Report() {
		
	}
	
//	public Report(int USEE_AT_CODE) {
//		this.USEE_AT_CODE = USEE_AT_CODE;
//	}
}
