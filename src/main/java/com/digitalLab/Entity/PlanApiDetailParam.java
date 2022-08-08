package com.digitalLab.Entity;

import lombok.Data;

import java.util.List;

@Data
public class PlanApiDetailParam {
    private int plan_id;

    List<PlateApiParam> batch_list;
    List<Step> step_list;
}
