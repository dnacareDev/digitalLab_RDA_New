package com.digitalLab.Entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class StepImage {
    private int step_image_id;
    private int plan_step_id;
    private String image_comment;
    private String ORIG_FILE_NM;
    private String ATCH_AT_CODE;
    private String create_date;

    private MultipartFile img;
}
