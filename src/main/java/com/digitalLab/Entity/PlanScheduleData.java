package com.digitalLab.Entity;

import lombok.Data;

import java.util.List;

@Data
public class PlanScheduleData {
    private int batch_index;

    private int step_id;
    private String step_info;

    private List<ScheduleManger> manager_list;
    private String managerList;
}
