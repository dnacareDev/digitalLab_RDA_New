package com.digitalLab.Service;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import com.digitalLab.Entity.SearchParam;
import com.digitalLab.Entity.Storage;

public interface StorageService {
	
	public List<Storage> selectStorageList();
	
	public Map<String, Object> storageDetail(int storage_id);
	
	public int insertStorage(Storage storage);
	
	public int modifyStorage(Storage storage);
	
	public int deleteStorage(int storage_id);

	Map<String, Object> getPlaceJsonDataByPlaceId(int place_id);
}
