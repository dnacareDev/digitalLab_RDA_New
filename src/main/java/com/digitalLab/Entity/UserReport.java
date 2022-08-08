package com.digitalLab.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserReport {
	
	private int user_report_id;
	private int report_id;
	private int report_status;
	private int USEE_AT_CODE;
	private String ATCH_FILE_NM;
	private String ORIG_FILE_NM;
	private String template_head;
	private String template_body;
	private String report_comment;
	private String account;

	// 등록 부분
	private int genetic_type;
	private int genetic_parents;
	private int genetic_id;
	private String genetic;
	
	// 시료 리스트 부분 
	private String genetic_depth1;
	private String genetic_depth2;
	private String seed_sender;
	private String place_name;
	private int seed_count;
	private String send_date;
	private String receive_date;
	private String regist_type;
	private String prj_dtl_no;
	private String prj_dtl_nm;

	//detail
	private String rnd_phase_nm;
}
