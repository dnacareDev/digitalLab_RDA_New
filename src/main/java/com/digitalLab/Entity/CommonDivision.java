package com.digitalLab.Entity;

import lombok.Data;

@Data
public class CommonDivision {
    private int division_id;
    private String division_depth1;
    private int division_depth1_id;
    private String division_depth2;
    private int division_depth2_id;
    private String division_depth3;
    private int division_depth3_id;
    private int division_parents;

    private String division;

}
