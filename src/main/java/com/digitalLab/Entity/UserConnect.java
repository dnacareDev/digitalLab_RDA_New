package com.digitalLab.Entity;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Alias("UserConnect")
public class UserConnect {
	
	private int user_connect_id;
	private String account;
	private String connect_date;
	private int connect_count;
	private String name;
	
	private int status1;
	private int status2;
	private int status3;
	private int status4;
}
