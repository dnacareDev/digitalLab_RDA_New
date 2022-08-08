package com.digitalLab.Entity;

import lombok.Data;

@Data
public class PlanSearchParam {
    int plan_status;

    public PlanSearchParam(){
        plan_status = -1;
    }
}
