package com.digitalLab.Entity;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Alias("Users")
public class Users
{
	// 옹달샘 추가
	private String account;
	private String groupCode;
	private String groupName;
	private String name;
	private String positionCode;
	private String position;
	private String email;
	private String mobilePhone;
	private String title;
	private String titleCode;
	private String userType;
	private String digitalCode;
	private String updateDat;
	private String createDat;
	private String authority;
	
	private int analysis_check;
	private int method_check;
	private int storage_check;
	private int researcher_check;
	
	private int no;

}