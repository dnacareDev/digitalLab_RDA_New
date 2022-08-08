package com.digitalLab.Service;

import com.digitalLab.Entity.Each;
import com.digitalLab.Entity.Report;
import com.digitalLab.Entity.SearchParam;
import com.digitalLab.Entity.Seed;

import java.util.List;
import java.util.Map;

import com.digitalLab.Entity.SeedEasyParam;
import com.digitalLab.Entity.UserReport;

public interface SeedService {
	
	public int registSeed(Seed seed);
	
    public int registSeedEasy(SeedEasyParam seed);
    
    public int registSeedMany(Seed seed);
    
    public int registSeedTemplate(Seed seed);

    public List<Seed> selectSeedList(SearchParam param);
    
    public List<UserReport> selectReportSeedList(SearchParam param);
    
    public List<Each> seedEachList();

    public Seed selectSeedByGenetic(int genetic_id);

//    public Map<String,Object> selectSeedById(int seed_id);
    
    public Map<String,Object> selectSeedByReportId(int report_id);
    
    public int modifyManySeed(Seed seed);

    int seedDelete(int seed_id);

    int seedDeleteAll(int user_report_id);

    int modifyEasySeed(SeedEasyParam seed);

    int modifyTemplateSeed(Seed seed);
}
