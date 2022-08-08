package com.digitalLab.Entity;

import java.util.List;

import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@Alias("Reagent")
public class Reagent {
	
	private int reagent_id;
	private String account;
	private String reagent_code;
	private String reagent_name;
	private String reagent_cas;
	private String reagent_standard;
	private String reagent_density;
	private String reagent_formula;
	private String reagent_nick_k;
	private String reagent_nick_e;
	private String reagent_production;
	private int reagent_amount;
	private int each_id;
	private String reagent_purpose;
	private String reagent_comment;
	private int place_id;
	private int reagent_status;
	private int USEE_AT_CODE;
	private String ATCH_FILE_NM;
	private String ORIG_FILE_NM;
	private String create_date;
	private String modify_date;
	private String reagent_use;
	
	// 추가
	private List<Reagent> reagentList;
	private String template_head;
	private String template_body;

	private String regist_type;
	
    private MultipartFile file;

	//view
	private int reagent_join_count;

	//update
	private Boolean isFileUpdate;


}
