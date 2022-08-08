package com.digitalLab.ServiceImpl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.digitalLab.Util.JsonUtil;
import com.digitalLab.Util.SessionUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalLab.Entity.Storage;
import com.digitalLab.Entity.Users;
import com.digitalLab.Mapper.StorageMapper;
import com.digitalLab.Service.FileService;
import com.digitalLab.Service.StorageService;
import com.digitalLab.Util.AuthUtil;
import com.digitalLab.Util.CodeUtil;
import com.digitalLab.Util.DataLogUtil;

@Service
public class StorageServiceImpl implements StorageService {

	// 일련 번호
	private static final String storageCode = "st";
	// 파일 경로
	private static final String type = "storage";
	
	@Autowired
	private StorageMapper storageMapper;

	@Autowired
	private FileService fileService;
	
	@Autowired
	private DataLogUtil logUtil;

	// 저장소 온/습도 리스트
	@Override
	public List<Storage> selectStorageList() {
		Users user = AuthUtil.sessionUser();
		
		return storageMapper.selectStorageList(user);
	}

	// 저장소 온/습도 상세보기
	@Override
	public Map<String, Object> storageDetail(int storage_id) {
		Map<String, Object> result = new HashMap<>();
		
		SessionUtil.setPkId(storage_id , type);
		
		Storage storage = storageMapper.getStorageById(storage_id);
		if (storage == null) {
			result.put("state", -1);
			return result;
		}
		result.put("logList", logUtil.logList(storage_id, type));
		result.put("storage", storage);
		result.put("state", 1);
		return result;
	}

	// 저장소 온/습도 등록
	@Override
	public int insertStorage(Storage storage) {
		
		Users user = AuthUtil.sessionUser();
		String code = codeCheck(user.getGroupCode());
		
		// upload File
		if (storage.getFile() == null) {
			return -23;
		}else if(fileService.extensionCheck(storage.getFile())){
			return -24;
		}

		try {
			JsonUtil.createStorageJson(code, storage.getFile());
		} catch (IOException e) {
			System.out.println("IOException");
			return -3;
		}catch (IllegalStateException e) {
			System.out.println("IllegalStateException");
			return -8;
		} catch (Throwable e) {
			System.out.println("Throwable");
			return -1;
		}

		String fileName = "";

		try {
			fileName = fileService.uploadAndGetFilePath(storage.getFile(), type);
		} catch (IOException e) {
			System.out.println("IOException");
			return -3;
		}
		
		int seq_id = storageMapper.getSeqId();

		storage.setStorage_file(fileName);
		storage.setStorage_original_file(storage.getFile().getOriginalFilename());
		storage.setStorage_id(seq_id);
		storage.setStorage_code(code);

		int result = storageMapper.insertStorage(storage);
		
		if(result == 0 ) {
			return -1;
		}
		
		logUtil.addDataLog(user.getAccount(), seq_id, 1, code, type);
		
		return result;
	}

	// 저장소 온/습도 수정
	@Override
	public int modifyStorage(Storage storage) {
		
		Users user = AuthUtil.sessionUser();
		
		if(storage.getStorage_id() != SessionUtil.getPkId(type)) {
			return -20;
		}

		// upload File
		String fileName = "";
		
		if (storage.getIsFileUpdate() != null && storage.getIsFileUpdate() && storage.getFile() != null) {
			
			// 확장자 체크
			if(fileService.extensionCheck(storage.getFile())){
				return -24;
			}
			
			try {
				JsonUtil.createStorageJson(storage.getStorage_code(), storage.getFile());
			} catch (IOException e) {
				System.out.println("IOException");
				return -3;
			}catch (IllegalStateException e) {
				System.out.println("IllegalStateException");
				return -8;
			} catch (Throwable e) {
				System.out.println("Throwable");
				return -1;
			}

			try {
				fileName = fileService.uploadAndGetFilePath(storage.getFile(), type);
			} catch (IOException e) {
				System.out.println("IOException");
				return -3;
			}
			storage.setStorage_file(fileName);
			storage.setStorage_original_file(storage.getFile().getOriginalFilename());
		}
		
		int result = storageMapper.modifyStorage(storage);
		
		if(result == 0 ) {
			return -1;
		}
		
		logUtil.addDataLog(user.getAccount(), storage.getStorage_id(), 2, storage.getStorage_code(), type);
		
		return result;	
	}

	private String codeCheck(String groupCode) {
		
		String code = storageCode+"-"+groupCode;
		
		code = storageMapper.getCode(code);
		String fullCode = CodeUtil.getFullCode(code, storageCode , groupCode);
		return fullCode;
	}

	@Override
	public int deleteStorage(int storage_id) {
		
		if(storage_id != SessionUtil.getPkId(type)) {
			return -20;
		}
		
		return storageMapper.deleteStorage(storage_id);
	}

	@Override
	public Map<String, Object> getPlaceJsonDataByPlaceId(int place_id){

		Map<String, Object> template = new HashMap<>();

		String storageCode = storageMapper.getStorageCodeByPlaceID(place_id);

		try {
			template = JsonUtil.readJson(storageCode);
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException");
		}finally {
			return template;
		}
	}

}
