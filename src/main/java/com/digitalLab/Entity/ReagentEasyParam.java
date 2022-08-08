package com.digitalLab.Entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ReagentEasyParam {
    private int reagent_id;
    private String account;
    private String reagent_code;
    private String reagent_name;
    private String reagent_comment;

    private int USEE_AT_CODE;
    private String ATCH_FILE_NM;
    private String ORIG_FILE_NM;

    private String create_date;
    private String modify_date;

    private MultipartFile file;
}
