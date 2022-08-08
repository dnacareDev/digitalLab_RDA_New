package com.digitalLab.Entity;

import lombok.Data;

import java.util.List;

@Data
public class AnalysisShedule {
	
    private int plan_id;
    private String plan_code;
    private String supervisor;
    private String manager;
    private String analysis_item;
    private String start_date;
    private String end_date;
    private int regist_type;
    private int sch_type;
    private String title;
    private List<schedule_manager> managerList;
}
