package com.digitalLab.Entity;

import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Alias("Storage")
public class Storage {
	
	private int storage_id;
	private int place_id;
	private String storage_code;
	private String storage_comments;
	private String storage_file;
	private String storage_original_file;
	private String storage_use;
	private String create_date;
	private String modify_date;
	
	// 파일 업로드
	private MultipartFile file;
	
	// detail
	private String place_name;
	private String account;

	// update
	private Boolean isFileUpdate;

	public Storage() {
		
	}
}
