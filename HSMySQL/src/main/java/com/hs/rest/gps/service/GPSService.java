package com.hs.rest.gps.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.hs.rest.gps.model.po.GPS;
import com.hs.rest.gps.repository.GPSRepository;

@Service
public class GPSService {
	
	@Autowired
	// 如果有一個以上的實作方法，則必須細說名稱 
	// @Qualifier("GPSRepositoryImpl")
	private GPSRepository gpsRepository;

	// GPS 的 CRUD
	
	// 多筆查詢
	// 建立一個List<GPS>查詢清單，來接收GPScontroller的查詢請求
	// 回復由gpsRepository.findAllGps()查詢的結果
	public List<GPS> queryAllGps(){
			return gpsRepository.queryAllGps();
	}
	
	// 單筆查詢
	public GPS getGpsByID(Integer id) {
		// Service收到資料，可以先作一些判斷，如果id值是空的，提早結束
		if(id == null) {
			return null;
		}
		GPS gps = gpsRepository.getGpsByID(id);
		return gps;
	}
	
	// 新增
	// GPSService 收到Controller傳送的參數，必須組裝成一個GPS物件
	// 再將 GPS 物件傳送給 Impl 新增進資料庫
	public Boolean addGPS(
			Double latitude,
			Double longitude,
			Integer meter,
			String location,
			String locationName) {
		// 組裝成一個 GPS 物件
		GPS gps = new GPS();
		// 將收到的參數塞進物件
		gps.setLatitude(latitude);
		gps.setLongitude(longitude);
		gps.setMeter(meter);
		gps.setLocation(locationName);
		gps.setLocationName(locationName);
		// 將 GPS 物件傳送給 gpsRepository(gps) 新增進資料庫
		Boolean status = gpsRepository.addGps(gps);
		
		return status;
	}
	
	
	
	// 刪除
	// 建立一個deleteGPS，來接收GPScontroller的刪除請求
	// 回復由GPSRepository 中 deleteGPSByID(Integer id)執行的結果
	public Boolean deleteGps(Integer id) {
		Boolean status = gpsRepository.deleteGPSByID(id);
		return status;
	}
	
	
}
