package com.digitalLab.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.digitalLab.Entity.Place;
import com.digitalLab.Entity.Users;

@Mapper
public interface PlaceMapper {
	
	// 장소 리스트
	List<Place> getPlaceList(Users user);
	
	// 장소 상세보기
	Place getPlace(int place_id);
	
	// 장소 등록
	int insertPlace(Place place);
	
	// 장소 수정
	int modifyPlace(Place place);
	
	// 장소 등록 시퀀스값
	int getSeqId();
	
	// 장소 등록 일련코드
	String getCode(String group_code);
	
	// 징소 삭제
	int deletePlace(int place_id);
}
