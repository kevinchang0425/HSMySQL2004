package com.hs.rest.gps.repository;

import java.util.List;

import com.hs.rest.gps.model.po.GPS;

// 命名定義：getXXX 單筆
// 命名定義：queryXXX 多筆

// 資料庫要提供哪些服務，建立規格interface
public interface GPSRepository {
	
	// 查詢所有 GPS
	List<GPS> queryAllGps();
	// 根據 ID 查詢 GPS
	GPS getGpsByID(Integer id);
	// 根據 Location 查詢 GPS
	GPS getGpsByLocationName(String locationName);
	// 新增 GPS
	Boolean addGps(GPS gps);
	// 修改 GPS
	Boolean updateGps(GPS gps);
	// 刪除 GPS
	Boolean deleteGPSByID(Integer id);	
}