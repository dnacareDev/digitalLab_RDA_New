package com.digitalLab.Entity;

import lombok.Data;

@Data
public class Plate {
    private int plate_id;
    private int plan_id;
    private int plate_index;
    private int plate_status;

    private String sch_contents;
    private String start_date;
    private String end_date;
}
