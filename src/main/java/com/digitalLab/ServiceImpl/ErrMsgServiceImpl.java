package com.digitalLab.ServiceImpl;

import com.digitalLab.Entity.Err_Msg;
import com.digitalLab.Mapper.ErrMsgMapper;
import com.digitalLab.Service.ErrMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ErrMsgServiceImpl implements ErrMsgService {
    @Autowired
    ErrMsgMapper mapper;

    @Override
    public Err_Msg getErrorMessage(int err_msg_id) {
        Err_Msg err = mapper.getErrorMessage(err_msg_id);
        if(err == null){
            err = Err_Msg.defaultError();
        }
        return err;
    }
}
