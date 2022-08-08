package com.digitalLab.Entity;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@Alias("Input")
public class Input {
	
	private int input_id;
	private int step_id;
	private String Input;
	private int each_id;
	private String each;
	
	public Input() {
		
	}
}
