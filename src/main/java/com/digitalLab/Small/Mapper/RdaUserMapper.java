package com.digitalLab.Small.Mapper;

import com.digitalLab.Entity.Users;

import java.util.List;
import java.util.Map;

public interface RdaUserMapper {
    public List<Users> selectUserList(String type);

    public Users selectUserByAccount(String account);
    
    public List<Map<String ,Object >> tableList();
}
