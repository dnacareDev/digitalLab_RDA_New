package com.digitalLab.Entity;

import lombok.Data;

import java.util.List;

@Data
public class VerificationDto {
    private int verification_id;
    private int report_id;
    private String prj_dtl_nm;
    private String prj_dtl_no;

    private int division_id;
    private String division;

    private int plan_id;
    private String plan_code;
    private int method_result;
    private List<String> crop_list;
    private List<String> kind_list;

    private String sample_data;
    private int result_number;
    private int result_blank;
    private int result_standard;
    private int result_control;
    private int result_min;
    private int result_max;
    private String create_date;
    private String modify_date;

}
