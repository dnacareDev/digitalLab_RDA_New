package com.digitalLab.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DivisionChart {
    private String division;
    private int plan_status;
    private int division_count;
}
