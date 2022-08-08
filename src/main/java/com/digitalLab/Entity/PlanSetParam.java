package com.digitalLab.Entity;

import lombok.Data;

import java.util.List;

@Data
public class PlanSetParam {
    private int plan_id;
    private String scheduleList;
    private String batchList;
    private int plan_well;
    private int plan_batch;
    private int plan_sample;
    private int plan_aspect; //배치 방향
    private int plan_blank;  //
    private int plan_standard;  //
    private int plan_control;  //
    private String plan_contents;
    private String deleteScheduleList;  //','
}
