package com.digitalLab.Entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class EquipmentEasyParam {
    private int equipment_id;
    private String account;
    private String equipment_code;
    private String equipment_name;

    private int USEE_AT_CODE;
    private String ATCH_FILE_NM;
    private String ORIG_FILE_NM;

    private String equipment_date;
    private String create_date;
    private String modify_date;

    private MultipartFile file;
}
