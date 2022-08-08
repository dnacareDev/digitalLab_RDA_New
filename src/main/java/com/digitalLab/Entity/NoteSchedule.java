package com.digitalLab.Entity;

import lombok.Data;

import java.util.List;

@Data
public class NoteSchedule {
    private int plan_id;
    private int sch_id;
    private int sch_type;
    private String sch_contents;
    private String start_date;
    private String end_date;
    private String create_date;
    private String modify_date;
    private String ATCH_FILE_NM;
    private String plan_supervisor;
    private List<schedule_manager> managerList; //관리자 (부)
}
