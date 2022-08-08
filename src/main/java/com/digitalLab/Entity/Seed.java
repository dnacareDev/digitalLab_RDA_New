package com.digitalLab.Entity;

import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@Alias("Seed")
public class Seed {
	
	private boolean report_chk;
	
    private int seed_id;
    private String seed_code;
    private String seed_sender;
    private String send_date;
    private String seed_receiver;
    private String receive_date;
    private String seed_manager;
    private String prj_dtl_no;
    private String prj_nm;
    private int seed_amount;
    private int each_id;
    private String each;
    private int seed_type;
    private int place_id;
    private int user_report_id;
    private int report_id;
    private int seed_status;
    private String seed_comment;
    private String template_head;
    private String template_body;

    private int USEE_AT_CODE;
    private String ATCH_FILE_NM;
    private String ORIG_FILE_NM;

    private String create_date;
    private String modify_date;

    // 추가
    private List<Seed> seedList;
    private String report_comment;
    private String seed_temp; // 저장 온도/습도
    
    // 품종 관련
    private int genetic_id;
    private int genetic_type;
    private String genetic_top;
    private String genetic;
    private int genetic_parents;

    /***************외래키********************/
    private String sender_name;
    private String receiver_name;

    private String genetic_depth1;
    private String genetic_depth2;

    private String place_name;
    private String report_name;
    private String report_code;
    
    //등록 방법 변환
    private String regist_type;
    
    public Seed(){
        this.user_report_id = -1;
    }

    //view
    private int seed_join_count;
}
