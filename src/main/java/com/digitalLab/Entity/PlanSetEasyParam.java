package com.digitalLab.Entity;

import lombok.Data;

@Data
public class PlanSetEasyParam {
    private int plan_id;
    private String manager_id;
    private String sub_manager_id;
    private String sch_comment;
    private String start_date;
    private String end_date;
    private String comment;
    private String plan_code;
}
