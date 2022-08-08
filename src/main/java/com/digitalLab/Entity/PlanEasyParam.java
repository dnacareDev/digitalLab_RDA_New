package com.digitalLab.Entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PlanEasyParam {
    private int plan_id;
    private int report_id;
    private String plan_code;
    private int plan_method;
    private int plan_seed;
    private int genetic_id;
    private int division_id;
    private String account;

    private String plan_contents;
    private int plan_status;

    private int USEE_AT_CODE;
    private String ATCH_FILE_NM;
    private String ORIG_FILE_NM;

    private String create_date;
    private String modify_date;

    private MultipartFile file;

    //update
    private Boolean isFileUpdate;

    private int genetic_depth_2_id;
    private String genetic_depth_2;
    private int genetic_type;

}
