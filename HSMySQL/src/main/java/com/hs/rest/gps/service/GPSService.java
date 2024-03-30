package com.hs.rest.gps.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.hs.rest.gps.model.po.GPS;
import com.hs.rest.gps.repository.GPSRepository;
import com.hs.rest.gps.util.DistanceUtil;

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
	
//	// 新增(傳統表單寫法)
//	// GPSService 收到Controller傳送的參數，必須組裝成一個GPS物件
//	// 再將 GPS 物件傳送給 Impl 新增進資料庫
//	public Boolean addGPS(
//			Double latitude,
//			Double longitude,
//			Integer meter,
//			String location,
//			String locationName) {
//		// 組裝成一個 GPS 物件
//		GPS gps = new GPS();
//		// 將收到的參數塞進物件
//		gps.setLatitude(latitude);
//		gps.setLongitude(longitude);
//		gps.setMeter(meter);
//		gps.setLocation(locationName);
//		gps.setLocationName(locationName);
//		// 將 GPS 物件傳送給 gpsRepository(gps) 新增進資料庫
//		Boolean status = gpsRepository.addGps(gps);
//		return status;
//	}
	
	
	// 新增 (JSON 傳遞參數寫法
	public Boolean addGPS(GPS gps) {
		// 將 GPS 物件傳送給 gpsRepository(gps) 新增進資料庫
		Boolean status = gpsRepository.addGps(gps);
		return status;
	}
	
	
	
	// 修改
	// 將組合後的資訊傳給 GPSRepositoryImpl
	public Boolean updateGPS(GPS gps) {
		return gpsRepository.updateGps(gps);
	}
	
	
	// 刪除
	// 建立一個deleteGPS，來接收GPScontroller的刪除請求
	// 回復由GPSRepository 中 deleteGPSByID(Integer id)執行的結果
	public Boolean deleteGps(Integer id) {
		Boolean status = gpsRepository.deleteGPSByID(id);
		return status;
	}
	
	
	// 查詢兩點距離符合清單
	// *******************
	// 計算打卡電腦與機構兩點之間的距離
	// 做一個陣列，存放計算的參數
	// 準備一個存放符合條件的點的經位度容器
	// 如果距離符合設定的條件
	// 加入存放的容器
	public List<GPS> findGpsWithinDistance(Double lng, Double lat){
		List<GPS> matcingGPSList = new ArrayList<>();	// 準備一個存放符合條件的點的經位度容器
		// 用最前面查詢全部GPS的方法找出全部點的經緯度
		// 用迴圈計算出距離，準備一個存放符合條件的點的經位度容器
		for(GPS gps : queryAllGps()) {
			// 用計算兩點距離的公式DistanceUtil
			double distance = DistanceUtil.getDistance(gps.getLongitude(), gps.getLatitude(), lng, lat);
			if(distance <= gps.getMeter()) {	// 如果距離符合設定的條件
				matcingGPSList.add(gps);	// 符合設定的條件，加入存放的容器
			}
			// log
			System.out.printf("(%s, %s) (%s, %s) %.2f公尺 <= %s公尺%n", gps.getLongitude(), gps.getLatitude(), lng, lat, distance, gps.getMeter());
		}
		return matcingGPSList;
		
		
		
	}
	
	
	
	
	
	
}
