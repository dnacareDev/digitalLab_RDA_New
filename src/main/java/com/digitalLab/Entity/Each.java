package com.digitalLab.Entity;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@Alias("Each")
public class Each {
	
	private int each_id;
	private String each;
	private int each_type;
	private int each_title;
	
	public Each() {
		// TODO Auto-generated constructor stub
	}

}
