package com.digitalLab.Controller;

import com.digitalLab.Entity.Err_Msg;
import com.digitalLab.Service.ErrMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ErrMsgController
{
    @Autowired
    ErrMsgService errorService;

    @GetMapping("/err-message")
    @ResponseBody
    public Err_Msg getErrorMessage(@RequestParam int err_msg_id)
    {
        Err_Msg err = errorService.getErrorMessage(err_msg_id);

        return err;
    }
}