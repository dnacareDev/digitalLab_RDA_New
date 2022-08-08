package com.digitalLab.Mapper;

import com.digitalLab.Entity.Equipment;
import java.util.List;

import com.digitalLab.Entity.EquipmentEasyParam;
import com.digitalLab.Entity.SearchParam;
import com.digitalLab.Entity.Users;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EquipmentMapper {

	public List<Equipment> equipmentList(String words);
	public List<Equipment> searchEquipment(SearchParam search);
	
	public Equipment equipmentDetail(int equipment_id);
	public Equipment selectEquipmentById(int equipment_id);
	
	public int registEquipment(Equipment equipment);
	public int registEquipmentEasy(EquipmentEasyParam equipment);
	
	public int equipmentModify(Equipment equipment);
	public int equipmentModifyEasy(Equipment equipment);

	public Equipment checkDuplicateName(String equipment_name);
	public int getCountEquipment(SearchParam param);

	public String getCode(String group_code);
	
	public int getSeqId();

    int deleteEquipment(int equipment_id);
    
    int checkAuth(@Param("user") Users user , @Param("equipment_id") int equipment_id);
}
