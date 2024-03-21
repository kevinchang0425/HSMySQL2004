package com.hs.rest.gps.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hs.rest.gps.model.GPS;
import com.hs.rest.gps.service.GPSService;

@RestController
@RequestMapping("/gps")
public class GPSController {

	@Autowired
	private GPSService gpsService;
	
	// GPS 的 CRUD
	// 新增
	@PostMapping
	public Boolean addGPS(
			// ?後面帶參數，或是網址後面帶參數，使用@RequestParam()
			@RequestParam("latitude") Double latitude,
			@RequestParam("longitude") Double longitude,
			@RequestParam("meter") Integer meter,
			@RequestParam("location") String location,
			@RequestParam("locationName") String locationName) {
		
		// 檢查參數
		// 對檢查內容的程式，暫略...
		// 傳送參數給 gpsService, 讓 gpsService 幫Controller新增
		Boolean status = gpsService.addGPS(latitude, longitude, meter, location, locationName);
		
		return status;
	}
	
	
	// 刪除
	// 瀏覽器執行要求一律都是Get，所以要建立一個Get接受瀏覽器的請求，不然會回報405錯誤
	// 沒建立@GetMapping，則只限於Controller內部使用
	// 其他的請求必須另外由程式控制
	// 建立一個Get接收瀏覽器傳送過來的請求
	// 因為前端葉面還沒建立，所以先建立一個List<GPS>清單回傳GPSService查詢的findAllGps()的結果
	@GetMapping
	public List<GPS> findAllGps(){
		return gpsService.findAllGps();
	}
	
	// 建立接受刪除請求
	// 使用{id}接受傳來的變數
	// 例如： .../gps/1 <- 刪除 id = 1 的紀錄
	// 例如： .../gps/3 <- 刪除 id = 3 的紀錄
	// 例如： .../gps/5 <- 刪除 id = 5 的紀錄
	// 路徑後面帶參數，使用@PathVariable()
	@DeleteMapping("/{id}")
	public Boolean deleteGPS(@PathVariable("id") Integer id) {
		Boolean status = gpsService.deleteGps(id);
		return status;
	}
	
	
}
