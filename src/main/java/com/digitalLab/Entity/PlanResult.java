package com.digitalLab.Entity;

import lombok.Data;

import java.util.List;

@Data
public class PlanResult {
    private int plan_id;
    private int method_result;
    private String plan_code;
    private List<String> crop_list;
    private List<String> kind_list;
}
