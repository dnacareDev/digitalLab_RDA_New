package com.digitalLab.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.digitalLab.Entity.Storage;
import com.digitalLab.Entity.Users;

@Mapper
public interface StorageMapper {
	
	// 저장고 온습도 리스트
	public List<Storage> selectStorageList(Users user);
	
	// 장소 상세보기
	public Storage getStorageById(int storage_id);
	
	// 저장고 온습도 등록
	public int insertStorage(Storage storage);
	
	// 저장고 온습도 수정
	public int modifyStorage(Storage storage);
	
	// 저장고 온습도 등록 시퀀스값
	public int getSeqId();
	
	// 저장고 온습도 등록 일련코드
	public String getCode(String group_code);
	
	// 저장고 온습도 삭제
	public int deleteStorage(int storage_id);

	// 저장고 CODE 불러오기 By PlaceId (최신 1개)
	String getStorageCodeByPlaceID(int place_id);
}
