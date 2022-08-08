package com.digitalLab.Mapper;

import com.digitalLab.Entity.SearchParam;
import com.digitalLab.Entity.Seed;
import com.digitalLab.Entity.SeedEasyParam;
import com.digitalLab.Entity.UserReport;
import com.digitalLab.Entity.Users;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SeedMapper {
	
	public int registSeed(Seed seed);
	
    public int registSeedEasy(SeedEasyParam seed);

    public int checkDuplicateName(String seed_name);

    public int getSeqId();

    public String getCode(String group_code);

    public List<Seed> selectSeedList(@Param("param") SearchParam param , @Param("user") Users user);

    public Seed selectSeedByGenetic(int genetic_id);

    public Seed selectSeedById(int seed_id);
    
    public List<Seed> selectSeedByReportId(@Param("user_report_id") int user_report_id , @Param("USEE_AT_CODE") int USEE_AT_CODE);
    
    public List<UserReport> selectReportSeedList(Users user);

    int deleteSeed(int seed_id);

    List<Integer> getSeedListByUserReportId(int user_report_id);

    int updateSeed(Seed item);

    int updateEasySeed(SeedEasyParam seed);
}
