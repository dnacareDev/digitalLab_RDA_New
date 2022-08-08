package com.digitalLab.Service;

import com.digitalLab.Entity.Equipment;
import com.digitalLab.Entity.EquipmentEasyParam;
import com.digitalLab.Entity.SearchParam;

import java.util.List;
import java.util.Map;

public interface EquipmentService {
	
	public int registEquipment(Equipment equipment);
	
	public int registEquipmentEasy(EquipmentEasyParam equipment);
	
	public Map<String , Object> registEquipmentMany(Equipment equipment);

	public int registEquipmentTemplate(Equipment equipment);

	public List<Equipment> searchEquipment(SearchParam searchParam);

	public Map<String, Object> selectEquipmentById(int equipment_id);
	
	public int equipmentModify(Equipment equipment);

    int equipmentDelete(int equipment_id);
}
