package com.digitalLab.Entity;

import lombok.Data;

@Data
public class PlateApiParam {
    private int batch_id;
    private int batch_index;
    private int sch_contents;
    private String start_date;
    private String end_date;
}
