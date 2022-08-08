package com.digitalLab.Entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class MethodEasyParam {
    private int method_id;
    private String account;
    private String method_code;
    private String method_stage;
    private String method_contents;
    private int division_id;
    private int method_share;

    //divsion input param
    private String division;
    private int division_parents;

    MultipartFile file;

    private int USEE_AT_CODE;
    private String ATCH_FILE_NM;
    private String ORIG_FILE_NM;
}
