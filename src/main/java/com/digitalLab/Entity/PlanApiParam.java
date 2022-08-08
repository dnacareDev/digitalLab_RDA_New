package com.digitalLab.Entity;

import lombok.Data;

@Data
public class PlanApiParam {
    private String id;
    private String plan_code;
    private String analysis_item;
    private String start_date;
    private String end_date;
}
