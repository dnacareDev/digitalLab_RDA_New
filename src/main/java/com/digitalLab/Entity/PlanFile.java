package com.digitalLab.Entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PlanFile {
    private int plan_file_id;
    private int plan_id;
    private String ATCH_FILE_NM;
    private String ORIG_FILE_NM;
    private int plan_type;

    private String plan_code;

    private MultipartFile file;
}
