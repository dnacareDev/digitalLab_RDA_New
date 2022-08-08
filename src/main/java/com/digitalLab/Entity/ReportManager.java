package com.digitalLab.Entity;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Alias("ReportManager")
public class ReportManager {
	
	private int report_manager_id;
	private String dic_code;
	private int manager_type;
	private int report_id;
}
