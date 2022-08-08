package com.digitalLab.Entity;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@Alias("Place")
public class Place {
	
	private int place_id;
	private String account;
	private String place_code;
	private String place_name;
	private String place_temp;
	private String place_humidty;
	private String create_date;
	private String modify_date;
	
	public Place() {
		// TODO Auto-generated constructor stub
	}

	//view
	private int place_join_count;

}
