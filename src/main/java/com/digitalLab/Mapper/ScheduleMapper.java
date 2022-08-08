package com.digitalLab.Mapper;

import java.util.List;

import com.digitalLab.Entity.*;
import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Param;

@Mapper
public interface ScheduleMapper
{
	int SelectScheduleId();

//	int InsertMonthSchedule(Schedule schedule);

	public List<Schedule> selectMonthSchedule(@Param("user") Users user , @Param("type") String type);
	
	public Schedule selectScheduleById(int sch_id);

//	public int registScheduleList(@Param("schedule_id") int schedule_id, @Param("list") List<Schedule> scheduleList);

	public int registMonthSchedule(Schedule schedule);

	public int registScheduleManager(schedule_manager manager);

	public int getSeqId();

	public List<Schedule> selectScheduleByPlan(int plan_id);

//	public int registScheduleManager(@Param("list")List<schedule_manager> managerList);

	public int deleteScheduleManager(int sch_id);

	public int modifyScheduleByPlan(Schedule schedule);

//	public int getSeqFunction(String seq_name);

	public int dummySql();

	public int registScheduleByPlan(Schedule schedule);

	public int deleteScheduleList(@Param("list") List<Integer> list);

	public int registNoteSchedule(NoteSchedule schedule);

	public NoteSchedule selectNoteScheduleByPlan(int plan_id);

	public List<schedule_manager> selectScheduleManagerByPlan(int plan_id);

	public int modifyNoteSchedule(NoteSchedule schedule);

	public int deleteScheduleManagerByPlan(int plan_id);

	public int deleteScheduleByPlan(int plan_id);
}