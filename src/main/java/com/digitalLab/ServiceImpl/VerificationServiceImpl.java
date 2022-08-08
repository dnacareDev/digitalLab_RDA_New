package com.digitalLab.ServiceImpl;

import com.digitalLab.Entity.*;
import com.digitalLab.Mapper.VerificationMapper;
import com.digitalLab.Service.ReportService;
import com.digitalLab.Service.UserService;
import com.digitalLab.Service.VerificationService;
import com.digitalLab.Util.DataLogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VerificationServiceImpl implements VerificationService {
    @Autowired
    private VerificationMapper verificationMapper;

    @Autowired
    private DataLogUtil logUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private ReportService reportService;

    private static final String type = "verification";

    @Override
    public int registVerification(Verification verification) {
        int result = -1;

        Users user = userService.getLoginUser();
        
        result = verificationMapper.registVerification(verification);
        
        Report report = reportService.selectReportById(verification.getReport_id());
        logUtil.addDataLog(user.getAccount(), verification.getVerification_id(), DataLogUtil.ACTION_REGIST, report.getPrj_dtl_no(), type);
        return result;
    }

    @Override
    public Map<String, Object> selectVerificationList() {
        Map<String,Object> result = new HashMap<>();
        Users user = userService.getLoginUser();

        List<VerificationDto> verificationDtoList = verificationMapper.selectVerificationList(user);
        result.put("state", 1);
        result.put("list", verificationDtoList);

        return result;
    }

    @Override
    public int modifyVerification(Verification verification) {
        Users user = userService.getLoginUser();

        int result = -1;
        result = verificationMapper.modifyVerification(verification);

        Report report = reportService.selectReportById(verification.getReport_id());
        logUtil.addDataLog(user.getAccount(), verification.getVerification_id(), DataLogUtil.ACTION_MODIFY, report.getPrj_dtl_no(), type);
        return result;
    }

    @Override
    public Map<String,Object> selectVerificationById(int verification_id) {
        Map<String,Object> result = new HashMap<>();
        Users user = userService.getLoginUser();

        VerificationDto verificationDto = verificationMapper.selectVerificationById(verification_id);
        List<DataLog> logList = logUtil.logList(verification_id, type);

        result.put("logList", logList);
        result.put("state", 1);
        result.put("list", verificationDto);

        return result;
    }
}
