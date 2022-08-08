package com.digitalLab.Entity;

import lombok.Data;

@Data
public class DivisionSearchParam {
	
    private int division_id;
    private int division_depth;
    
    // method 관련
    private String authority;
    private String account;
    private String user_code;
    private int USEE_AT_CODE;

    public DivisionSearchParam(){
        this.division_id = -1;
        this.division_depth = 1;
    }
}
