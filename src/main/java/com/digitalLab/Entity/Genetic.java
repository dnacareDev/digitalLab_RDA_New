package com.digitalLab.Entity;

import lombok.Data;

@Data
public class Genetic {
	
    private int seed_id;
    private int genetic_id;
    private int genetic_parents;
    private int genetic_depth;
    private int genetic_type;
    private String seed_code;
    private String seed_amount;

    private String genetic;
    
    private String parents_name;
    
    public Genetic() {
    	this.genetic_parents = -1;
    	this.genetic_depth = 1;
    }
    
    public Genetic(int genetic_id , int genetic_parents , int genetic_type , String genetic , int genetic_depth) {
    	this.genetic_id = genetic_id;
    	this.genetic_parents = genetic_parents;
    	this.genetic_type = genetic_type;
    	this.genetic = genetic;
    	this.genetic_depth = genetic_depth;
    }
}
