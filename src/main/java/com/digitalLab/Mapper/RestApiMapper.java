package com.digitalLab.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.digitalLab.Entity.AnalysisShedule;
import com.digitalLab.Entity.Equipment;
import com.digitalLab.Entity.Method;
import com.digitalLab.Entity.Reagent;
import com.digitalLab.Entity.Report;
import com.digitalLab.Entity.Users;

@Mapper
public interface RestApiMapper
{
	
	Users SelectUser(String user_username);
	
	// 시약 갯수 조회
	int SelectReagentCount();
	
	// 시약 목록 조회
	List<Reagent> SelectReagentList(Users user);

	// 시약 상세 조회
	Reagent SelectReagentDetail(String reagent_code);
	
	// 장비 갯수 조회
	int SelectEquipmentCount();

	// 장비 목록 조회
	List<Equipment> SelectEquipmentList(Users user);

	// 장비 상세 조회
	Equipment SelectEquipmentDetail(String equipment_code);

	// 메소드 갯수 조회
	int SelectMethodCount();

	// 메소드 목록 조회
	List<Method> SelectMethodList(String word);

	// 메소드 상세 조회
	Method SelectMethodDetail(String method_code);
	
	int SelectNoteid();

	Report SelectReport(int plan_id);

	Report SelectReportCode(int plan_id);
	
	// 분석 스케쥴 리스트
	public List<AnalysisShedule> analysisScheduleList(Users user);

}