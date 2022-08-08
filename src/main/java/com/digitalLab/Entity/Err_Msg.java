package com.digitalLab.Entity;

import lombok.Data;

@Data
public class Err_Msg {
    private int err_msg_id;
    private String msg;

    public static Err_Msg defaultError(){
        Err_Msg err = new Err_Msg();
        err.setErr_msg_id(-123);
        err.setMsg("에러코드가 존재하지 않습니다");

        return err;
    }
}
