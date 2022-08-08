package com.digitalLab.ServiceImpl;

import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.*;

import com.digitalLab.Entity.*;
import com.digitalLab.Service.BatchService;
import com.digitalLab.Service.UserService;
import com.digitalLab.Util.JsonUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalLab.Mapper.ScheduleMapper;
import com.digitalLab.Service.SampleService;
import com.digitalLab.Service.ScheduleService;
import com.digitalLab.Util.AuthUtil;

@Service
public class ScheduleServiceImpl implements ScheduleService
{
	@Autowired
	private ScheduleMapper mapper;
	
	@Autowired 
	private SampleService sampleService;

	@Autowired
	private BatchService batchService;

	@Autowired
	private UserService userService;

	private Gson gson = new GsonBuilder().setPrettyPrinting().create();

	private static final String path = "Schedule";

	public static final int SCHEDULE_SHARE = 1;

	public static final int SCHEDULE_PLAN = 2;

	public static final int SCHEDULE_NOTE = 3;

	@Override
	public List<Users> SelectUser()
	{	
		Users user = AuthUtil.sessionUser();
		
		return userService.userList(user);
	}

	@Override
	public int SelectScheduleId()
	{
		return mapper.SelectScheduleId();
	}

	@Override
	public int InsertMonthSchedule(Schedule schedule){
		int result = -1;
		int sch_id = mapper.SelectScheduleId();
		schedule.setSch_id(sch_id);;

		result = mapper.registMonthSchedule(schedule);
		schedule.getManagerList().forEach(e -> {
			e.setSch_id(sch_id);
			mapper.registScheduleManager(e);
		});

				//mapper.InsertMonthSchedule(schedule);
		return result;

	}

	@Override
	public List<Schedule> selectMonthSchedule(String type) {
		
		Users user = AuthUtil.sessionUser();
		
		List<Schedule> scheduleList = mapper.selectMonthSchedule(user , type);

		return scheduleList;
	}

	@Override
	public int registScheduleByPlan(List<Schedule> scheduleList, int schedule_id) {
		int result = -1;
		if(scheduleList.isEmpty()){
			return result;
		}

		for (int i = scheduleList.size() - 1; i >= 0; i--) {
			Schedule schedule = scheduleList.get(i);
			if (schedule.getSch_id() != -1) {
				int count = mapper.modifyScheduleByPlan(schedule);
				if (count == 1) {
					scheduleList.remove(schedule);
					mapper.deleteScheduleManager(schedule.getSch_id());
					for (int j = 0; j < schedule.getManagerList().size(); j++) {
						schedule.getManagerList().get(j).setSch_id(schedule.getSch_id());
						mapper.registScheduleManager(schedule.getManagerList().get(j));
					}
				}
			} else {
				int id = mapper.SelectScheduleId();
				mapper.dummySql();
				schedule.setSch_id(id);
				mapper.registScheduleByPlan(schedule);
				schedule.getManagerList().forEach(e -> {
					e.setSch_id(id);
					;
					mapper.registScheduleManager(e);
				});

			}
		}

		return 1;
	}
	
	@Override
	public int registScheduleByBatch(List<Schedule> scheduleList, int schedule_id) {
		int result = -1;
		if(scheduleList.isEmpty()){
			return result;
		}
		System.out.println("===========================================");
		scheduleList.forEach(schedule -> {
			System.out.println(schedule);
		});
		System.out.println("===========================================");
		int test = 0;

		for(int i = scheduleList.size() - 1; i >= 0; i--) {
			Schedule schedule = scheduleList.get(i);
			if(schedule.getSch_id() != -1){
				int count = mapper.modifyScheduleByPlan(schedule);
				if(count == 1){
					scheduleList.remove(schedule);
					mapper.deleteScheduleManager(schedule.getSch_id());
					for(int j = 0; j < schedule.getManagerList().size(); j ++){
						schedule.getManagerList().get(j).setSch_id(schedule.getSch_id());
						mapper.registScheduleManager(schedule.getManagerList().get(j));
					}
				}
			}
			else {
				System.out.println("aa");
				int id = mapper.SelectScheduleId();
				mapper.dummySql();
				schedule.setSch_id(id);
				mapper.registScheduleByPlan(schedule);

			}
		}

		return 1;
	}

	@Override
	public List<Schedule> selectScheduleByPlan(int plan_id) {
    		List<Schedule> list = mapper.selectScheduleByPlan(plan_id);
		return list;
	}

	@Override
	public int deleteScheduleList(String sch_id) {
		if(sch_id == null || sch_id.isEmpty()){
			return -1;
		}

		List<Integer> schList = new ArrayList<>();
		String[] strList =  sch_id.split(",");
		for(int i = 0; i < strList.length; i++){
			schList.add(Integer.parseInt(strList[i]));
		}
		if(schList.isEmpty()){
			return -1;
		}
		int result = mapper.deleteScheduleList(schList);

		return result;
	}

	@Override
	public Map<String, Object> selectScheduleBySchId(int sch_id) {
		
		Map<String , Object> map = new HashMap<String , Object>();
		
		Schedule schedule = mapper.selectScheduleById(sch_id);
		
		// 분석
		if(schedule.getSch_type() == SCHEDULE_NOTE) {
			Map<String,Object> result = sampleService.sampleDetail(schedule.getPlan_id() , schedule.getPlan_type());
			List<Batch> batchList = null;
			try {
				System.out.println("schedule.getATCH_FILE_NM() : "+schedule.getATCH_FILE_NM());
				
				String test = JsonUtil.readJsonFile(schedule.getATCH_FILE_NM());
				Type listType = new TypeToken<ArrayList<Batch>>(){}.getType();
				batchList = gson.fromJson(test , listType);
			}
			catch (FileNotFoundException ex){
				System.out.println("ex");
			}

			map.put("batch",batchList);
			map.put("plan", result);
			map.put("type", schedule.getSch_type());
		}
		
		// 일정
		if(schedule.getSch_type() == SCHEDULE_PLAN) {
			
			Map<String,Object> result = sampleService.sampleDetail(schedule.getPlan_id() , schedule.getPlan_type());
			
			map.put("schedule", schedule);
			map.put("plan", result);
			map.put("type", schedule.getSch_type());
			map.put("registType", schedule.getPlan_type());
		}
		
		// 공유
		if(schedule.getSch_type() == SCHEDULE_SHARE) {
			map.put("schedule", schedule);
			map.put("type", schedule.getSch_type());
		}
		
		return map;
	}

	@Override
	public int registNoteSchedule(int plan_id, String sch_contents) {
		NoteSchedule noteSchedule = mapper.selectNoteScheduleByPlan(plan_id);
		Users user = userService.getLoginUser();

		List<Batch> batchList = batchService.selectBatchByPlan(plan_id);
		String jsonText = gson.toJson(batchList);

		Date date = new Date();
		String ATCH_FILE_NM = JsonUtil.createJsonFile(Long.toString(date.getTime()), path, jsonText);

		if(noteSchedule == null){
			int result = -1;

			int sch_id =  mapper.getSeqId();
			List<schedule_manager> managerList = mapper.selectScheduleManagerByPlan(plan_id);

			NoteSchedule schedule = new NoteSchedule();
			schedule.setPlan_supervisor(user.getAccount());;
			schedule.setManagerList(managerList);
			schedule.setPlan_id(plan_id);
			schedule.setSch_id(sch_id);;
			schedule.setSch_contents(sch_contents);
			schedule.setPlan_supervisor(user.getAccount());

			schedule.setATCH_FILE_NM(ATCH_FILE_NM);

			result = mapper.registNoteSchedule(schedule);
			schedule.getManagerList().forEach(e ->{
				e.setSch_id(sch_id);
				mapper.registScheduleManager(e);
			});

			return result;
		}
		else {
			String orgFileName = noteSchedule.getATCH_FILE_NM();
			JsonUtil.deleteFile(orgFileName);

			noteSchedule.setATCH_FILE_NM(ATCH_FILE_NM);
			mapper.modifyNoteSchedule(noteSchedule);
			return 1;
		}
	}

	@Override
	public int deleteScheduleManagerByPlan(int plan_id) {
		int result = -1;
		mapper.deleteScheduleManagerByPlan(plan_id);
		return result;
	}

	@Override
	public int deleteSCheduleByPlan(int plan_id) {
		int result = -1;

		mapper.deleteScheduleManagerByPlan(plan_id);

		result = mapper.deleteScheduleByPlan(plan_id);
		return result;
	}
}