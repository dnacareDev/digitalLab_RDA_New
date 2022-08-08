package com.digitalLab.Entity;

import lombok.Data;

import java.util.List;

@Data
public class PlanMethod {
    private int plan_method_id;
    private int plan_id;
    private int method_id;
    private int method_cycle;
    private int method_stage;
    private String method_comment;
    private List<Method> methodList;

    public PlanMethod(){
        plan_method_id = -1;
        plan_id = -1;
        method_id = -1;
        method_cycle = -1;
        method_comment = "";
    }
}
