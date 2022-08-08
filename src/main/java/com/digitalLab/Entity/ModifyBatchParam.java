package com.digitalLab.Entity;

import lombok.Data;

@Data
public class ModifyBatchParam {
    private int plan_id;
    private String batchJson;
    private boolean state;
    private String plan_code;
    private int plan_step;
    private int plan_plate;
    private String division;
    private String type;
    
    // 서브스텝 관련
    private int step_id;
}
