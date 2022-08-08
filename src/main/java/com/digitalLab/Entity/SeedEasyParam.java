package com.digitalLab.Entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SeedEasyParam {
	private boolean report_chk;

    private int seed_id;
    private String seed_code;
    private int user_report_id;
    private int report_id;
    private String report_comment;

    private int USEE_AT_CODE;
    private String ATCH_FILE_NM;
    private String ORIG_FILE_NM;

    private String create_date;
    private String modify_date;
    
    // 품종 관련
    private int genetic_id;
    private int genetic_type;
    private String genetic_top;
    private String genetic;
    private int genetic_parents;

    MultipartFile file;

    public SeedEasyParam(){
        this.user_report_id = -1;
    }


    //update
    private Boolean isFileUpdate;
}
