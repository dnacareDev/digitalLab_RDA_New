package com.digitalLab.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Division {
    private int division_id;
    private String division;
    private int depth;
    private int division_parents;

    public Division(String division,int depth,int division_parents){
        this.division = division;
        this.depth = depth;
        this.division_parents = division_parents;
    }
}
