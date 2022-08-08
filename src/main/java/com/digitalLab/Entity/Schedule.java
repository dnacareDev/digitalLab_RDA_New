package com.digitalLab.Entity;

import org.apache.ibatis.type.Alias;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@Alias("Schedule")
public class Schedule
{
	private int sch_id;
	private int plan_id;
	private int plan_type;
	private String plan_supervisor;  // (관리자)정
	private int plan_manager;
	private String start_date;
	private String end_date;
	private String plan_comment;
	private int step_id;
	private int batch_id;
	private String sch_title;
	private String sch_contents;
	
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private String sch_share[];
	
	private int sch_type;
	private String sch_data;
	private String create_date;
	private String modify_date;
	private String ATCH_FILE_NM;

	private String plan_code;
	private String name;
	private String division;
	private String division_parents;
	private String easy_division;
	private String easy_division_parents;
	
	private int batch_index;
	private List<schedule_manager> managerList; //관리자 (부)
	private String StepData;
	//plan_supervisor;
	//managerList "/" join
	
	private List<Seed> seedList;

	private int plate_index;
	private int plate_id;

	public Schedule(){
		this.sch_id = -1;
	}
	
	public String[] getSch_share() {

		if(this.sch_share != null){
			String[] tempData = new String[this.sch_share.length];
			
			System.arraycopy(this.sch_share, 0, tempData, 0, this.sch_share.length);

			return tempData;
		}else{
			return null;
		}
	}

	public void setSch_share(String[] sch_share) {

		if(sch_share != null){
			this.sch_share = new String[sch_share.length];
			System.arraycopy(sch_share, 0, this.sch_share, 0, sch_share.length);
		}else{
			this.sch_share = null;
		}
	}
}