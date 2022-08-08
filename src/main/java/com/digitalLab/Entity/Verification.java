package com.digitalLab.Entity;

import lombok.Data;

@Data
public class Verification {
    private int verification_id;
    private int report_id;
    private int division_id;
    private int plan_id;
    private String sample_data;
    private int result_number;
    private int result_blank;
    private int result_standard;
    private int result_control;
    private int result_min;
    private int result_max;
}
