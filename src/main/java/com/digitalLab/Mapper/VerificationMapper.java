package com.digitalLab.Mapper;

import com.digitalLab.Entity.Users;
import com.digitalLab.Entity.Verification;
import com.digitalLab.Entity.VerificationDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VerificationMapper {
    public int registVerification(Verification verification);

    public int modifyVerification(Verification verification);

    public List<VerificationDto> selectVerificationList(Users users);

    public VerificationDto selectVerificationById(int verification_id);
}
