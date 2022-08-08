package com.digitalLab.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.digitalLab.Entity.DataLog;
import com.digitalLab.Entity.Schedule;
import com.digitalLab.Entity.Users;
import com.digitalLab.Entity.schedule_manager;
import com.digitalLab.Service.ScheduleService;
import com.digitalLab.Util.AuthUtil;
import com.digitalLab.Util.DataLogUtil;

@Controller
public class ScheduleController
{	
	@Autowired
	private ScheduleService service;
	
	@Autowired
	private DataLogUtil logUtil;
	
	// 월간 일정
	@GetMapping("month/month_schedule")
	public ModelAndView MonthSchedule(ModelAndView mv)
	{	
		List<Users> userList = service.SelectUser();
		List<Schedule> scheduleList = service.selectMonthSchedule("month");
		
		mv.addObject("userList", userList);
		mv.addObject("scheduleList", scheduleList);
		mv.setViewName("month/month_schedule");
		
		return mv;
	}
	
	// 월간 일정
	@GetMapping("notice/noticeSchedule")
	@ResponseBody
	public List<Schedule> noticeSchedule()
	{	
		List<Schedule> scheduleList = service.selectMonthSchedule("notice");
			System.out.println("scheduleList : "+scheduleList);
		return scheduleList;
	}
	
	@ResponseBody
	@PostMapping("share/insertSchedule")
	public int InsertSchedule(@RequestBody Schedule schedule)
	{	
		Users user = AuthUtil.sessionUser();
		
		int sch_id = service.SelectScheduleId();
		
		schedule.setSch_id(sch_id);
		
		List<schedule_manager> managerList = new ArrayList<schedule_manager>();
		for(String acount : schedule.getSch_share()) {
			
			schedule_manager sch_manager = new schedule_manager();
			sch_manager.setAccount(acount);
			sch_manager.setMgr_type(3);
			sch_manager.setSch_id(sch_id);
			
			managerList.add(sch_manager);
		};
		schedule_manager sch_manager = new schedule_manager();
		sch_manager.setAccount(user.getAccount());
		sch_manager.setMgr_type(1);
		sch_manager.setSch_id(sch_id);
		managerList.add(sch_manager);
		
		schedule.setPlan_supervisor(user.getAccount());
		schedule.setManagerList(managerList);
		
		int result = service.InsertMonthSchedule(schedule);
		
		return result;
	}
	
	@ResponseBody
	@PostMapping("schedule/sample")
	public int sampleSchedule() {
		
		int result = -1;
		
		return result;
	}
	
	@GetMapping("schedule/{sch_id}/month")
	@ResponseBody
	public Map<String , Object> monthSchedule(@PathVariable int sch_id){
		
		return service.selectScheduleBySchId(sch_id);
	}
	
	@GetMapping("schedule/week_schedule")
	public ModelAndView WeekSchedule(ModelAndView mv)
	{	
		List<Users> userList = service.SelectUser();
		List<Schedule> scheduleList = service.selectMonthSchedule("month");
		mv.addObject("userList", userList);
		mv.addObject("scheduleList", scheduleList);
		mv.setViewName("week/week_schedule");

		return mv;
	}
	
	// 연구 노트
	@GetMapping("study_notes")
	public ModelAndView StudyNotes(ModelAndView mv)
	{	
		List<Users> userList = service.SelectUser();
		List<Schedule> scheduleList = service.selectMonthSchedule("sample");

		scheduleList.forEach(e ->{
			System.out.println(e);
		});

		mv.addObject("user", userList);
		mv.addObject("scheduleList", scheduleList);
		mv.setViewName("study_notes/study_notes");
		
		return mv;
	}
	
	@ResponseBody
	@GetMapping("selectNoteLog/{plan_id}")
	public List<DataLog> SelectNoteLog(@PathVariable int plan_id)
	{
		List<DataLog> result = logUtil.logList(plan_id, "note");
		
		return result;
	}
}