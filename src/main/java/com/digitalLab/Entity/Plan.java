package com.digitalLab.Entity;

import lombok.Data;

import java.util.List;

@Data
public class Plan {
    private int plan_id;
    private String plan_code;
    private int plan_method;
    private int plan_seed;
    private int plan_status;
    private int plan_well;
    private int plan_batch;
    private int plan_aspect;
    private int plan_standard;
    private int plan_control;
    private int plan_blank;
    private int plan_sample;
    private String plan_contents;
    private int plan_cycle;
    private String approve_date;
    private int division_id;
    private String prj_dtl_no;
    private String prj_nm;
    private int plan_plate;
    private int plan_step;
    private String account;

    private int USEE_AT_CODE;
    private String ATCH_FILE_NM;
    private String ORIG_FILE_NM;

    private String create_date;
    private String modify_date;

    private String regist_type;

    private int report_id;
    private String report_code;
    private String report_name;

    private int genetic_id;
    private String genetic_depth_1; //작목명
    private String genetic_depth_2; // 품종/유전자원명
    private int genetic_depth_2_id;
    private int genetic_type;

    private int seed_ammount;
    private int method_stage;

    private String division_depth_1;
    private String division_depth_2;
    private String division_depth_3; //분석 항목
    private String division_depth_4;

    private String easy_division_depth_3;
    private String easy_report_code;
    private String easy_report_name;
    private String easy_genetic_depth_1;
    private String easy_genetic_depth_2;

    private String strPlanMethod;
    private List<PlanMethod> planMethodList;
    private List<Integer> seed_pk_list;
    private List<Seed> seedList;
    private int method_id;
    private int method_cycle;
    private int method_result;
    
    // 템플릿
    private String template_head;
    private String template_body;

    private String plan_atch_file_nm;
    private String plan_orig_file_nm;

}
