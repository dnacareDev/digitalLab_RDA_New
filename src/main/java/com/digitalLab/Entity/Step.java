package com.digitalLab.Entity;

import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@Alias("Step")
public class Step {
	
	private int step_id;
	private int method_id;
	
	private String ATCH_FILE_NM;
	
	private int step_index;
	private int step_type;
	private int step_depth;
	private int step_parent;
	private int step_seed;
	private String step_comment;
	
	private int seed_id;
	private int reagent_id;
	private int equipment_id;
	private int instrument_id;
	private String account;
	
	//
	private String step_name;
	private String step_nickName;
	private int data_id;
	
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private int[] step_each;
	
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	public String[] step_input;
	
	private List<Step> sub_step_list;
	private List<Input> inputList;
	
	public Step(){
	}
	
	public int[] getStep_each(){
		if(this.step_each != null){
			int[] tempData = new int[this.step_each.length];
			
			System.arraycopy(this.step_each, 0, tempData, 0, this.step_each.length);

			return tempData;
		}else{
			return null;
		}
	}

	public void setStep_each(int[] step_each) {

		if(step_each != null){
			this.step_each = new int[step_each.length];
			System.arraycopy(step_each, 0, this.step_each, 0, step_each.length);
		}else{
			this.step_each = null;
		}
	}
	
	public String[] getStep_input() {

		if(this.step_input != null){
			String[] tempData = new String[this.step_input.length];
			
			System.arraycopy(this.step_input, 0, tempData, 0, this.step_input.length);

			return tempData;
		}else{
			return null;
		}
	}

	public void setStep_input(String[] step_input) {

		if(step_input != null){
			this.step_input = new String[step_input.length];
			System.arraycopy(step_input, 0, this.step_input, 0, step_input.length);
		}else{
			this.step_input = null;
		}
	}
}
