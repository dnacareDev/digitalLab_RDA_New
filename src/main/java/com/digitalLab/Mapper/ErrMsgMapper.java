package com.digitalLab.Mapper;

import com.digitalLab.Entity.Err_Msg;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ErrMsgMapper {
    public Err_Msg getErrorMessage(int err_msg_id);
}
