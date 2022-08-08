package com.digitalLab.Entity;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@Alias("Instrument")
public class Instrument {
	
	private int instrument_id;
	private String account;
	private String instrument_code;
	private String instrument_name;
	private int instrument_status;
	private String create_date;
	private String modify_date;

	private String use_type;
	private String registrant;
	
	public Instrument() {
		// TODO Auto-generated constructor stub
	}

	private int instrument_join_count;

}
