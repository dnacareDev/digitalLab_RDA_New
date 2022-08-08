package com.digitalLab.Service;

import java.util.List;
import java.util.Map;

import com.digitalLab.Entity.Schedule;
import com.digitalLab.Entity.Users;

public interface ScheduleService
{

	List<Users> SelectUser();

	int SelectScheduleId();

	int InsertMonthSchedule(Schedule schedule);

	public List<Schedule> selectMonthSchedule(String type);

	public int registScheduleByPlan(List<Schedule> scheduleList, int schedule_id);
	
	public int registScheduleByBatch(List<Schedule> scheduleList, int schedule_id);

	public List<Schedule> selectScheduleByPlan(int plan_id);

	public int deleteScheduleList(String sch_id);
	
	Map<String , Object> selectScheduleBySchId(int sch_id);

	public int registNoteSchedule(int plan_id, String sch_contents);

	public int deleteScheduleManagerByPlan(int plan_id);

	public int deleteSCheduleByPlan(int plan_id);
}