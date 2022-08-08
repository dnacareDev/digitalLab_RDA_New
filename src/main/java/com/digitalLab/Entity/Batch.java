package com.digitalLab.Entity;

import lombok.Data;

import java.util.List;

@Data
public class Batch {
    private int batch_id;
    private int plan_id;
    private int genetic_id;
    private int seed_id;
    private int batch_index;
    private String batch_sample;
    private String batch_test;
    private String batch_well;
    private int user_id;
    private String batch_data;
    private String batch_result;

    private int plate_id;
    private int plate_index;
    private int batch_type;
    private String genetic_depth_1;
    private String genetic_depth_2;
    
    // 서브스텝관련
    private int step_id;
    private int batch_sub_id;
}
