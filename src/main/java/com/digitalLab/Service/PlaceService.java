package com.digitalLab.Service;

import java.util.List;
import java.util.Map;

import com.digitalLab.Entity.Place;

public interface PlaceService {
	
	public List<Place> place_list();
	
	public Map<String , Object> placeDetail(int place_id);
	
	public int insertPlace(Place place);
	
	public int modifyPlace(Place place);
	
	public int placeDelete(int place_id);
	
}
