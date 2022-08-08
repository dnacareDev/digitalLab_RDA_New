package com.digitalLab.Service;

import com.digitalLab.Entity.Verification;
import com.digitalLab.Entity.VerificationDto;
import org.springframework.stereotype.Service;

import java.util.Map;


public interface VerificationService {
    public int registVerification(Verification verification);

    public Map<String,Object> selectVerificationList();

    public int modifyVerification(Verification verification);

    public Map<String,Object> selectVerificationById(int verification_id);
}
