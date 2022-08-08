package com.digitalLab.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneticChart {
    private String genetic;
    private int plan_status;
    private int genetic_count;
}
