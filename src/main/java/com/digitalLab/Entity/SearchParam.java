package com.digitalLab.Entity;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@Alias("SearchParam")
public class SearchParam {
    
	private String searchType;
    private String searchKeyword;
    private String type;
    private String share_type;
    private String account;
    private String authority;
    private String user_code;
    private int USEE_AT_CODE;
    
    public SearchParam() {
        USEE_AT_CODE = -1;
    };
}
