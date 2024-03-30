package com.hs.rest.gps.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hs.rest.gps.model.po.GPS;
import com.hs.rest.gps.model.response.ApiResponse;
import com.hs.rest.gps.service.GPSService;

@RestController
@RequestMapping("/gps")
public class GPSController {

	@Autowired
	private GPSService gpsService;
	
	// 建立日期時間顯示格式
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
	
	// 取得現在時間
	private String getNow() {
		return sdf.format(new Date());
	}
	
	//*************************
	// GPS 的 CRUD
	//*************************
	// 多筆查詢
	// 習慣性把Get寫第一個：查詢，新增，修改，刪除
	// 瀏覽器執行要求一律都是Get，所以要建立一個Get接受瀏覽器的請求，不然會回報405錯誤
	// 沒建立@GetMapping，則只限於Controller內部使用
	// 其他的請求必須另外由程式控制
	// 建立一個Get接收瀏覽器傳送過來的請求
	// 因為前端葉面還沒建立，所以先建立一個List<GPS>清單回傳GPSService查詢的findAllGps()的結果
	@GetMapping
	// 多筆查詢 https://3.27.70.100:8443/HSMySQL/mvc/gps
//	public List<GPS> queryAllGps(){
//			 List<GPS> gpsList = gpsService.queryAllGps();
//			 return gpsList;
//		}
	// 改成response包裝模式
	public ApiResponse queryAllGps(){
		 List<GPS> gpsList = gpsService.queryAllGps();
		 // 建立回應物件
		 if (gpsList.size() == 0) {
			ApiResponse apiResponse = new ApiResponse(false, "無資料", getNow(), null);
			return apiResponse;
		}
		 ApiResponse apiResponse = new ApiResponse(true, "多筆查詢成功", getNow(), gpsList);
		 return apiResponse;
	}
	
	// 單筆查詢
	@GetMapping("/{id}")
	// 單筆查詢 https://3.27.70.100:8443/HSMySQL/mvc/gps/1
	// 單筆查詢 https://3.27.70.100:8443/HSMySQL/mvc/gps/...
//	public GPS getGpsByID(@PathVariable("id") Integer id) {
//		GPS gps = gpsService.getGpsByID(id);
//		return gps;
//	}
	// 改成response包裝模式
	public ApiResponse getGpsByID(@PathVariable("id") Integer id) {
		GPS gps = gpsService.getGpsByID(id);
		if (gps == null) {
			ApiResponse apiResponse = new ApiResponse(false, "無資料", getNow(), null);
			return apiResponse;
		}
		 ApiResponse apiResponse = new ApiResponse(true, "單筆查詢成功", getNow(), gps);
		 return apiResponse;
	}
	
	
	
	// 查詢兩點距離符合清單
	// *******************
	// 接收前端傳來的經緯度(lng, lat)
	// 將經緯度參數傳給GPSservice的findGPSWithinDistance()計算距離
	// GPSservice傳回符合距離範圍設定的地點清單
	// https://localhost:8443/HSMySQL/mvc/gps/findGpsWithinDistance?lng=${放入經度參數}&lat=${放入緯度參數}
	@GetMapping("/findGpsWithinDistance")
	public ApiResponse findGpsWithinDistance(@RequestParam Double lng, @RequestParam Double lat) {
		// 經由GPSservice查詢回傳的結果，得到符合設定距離的 GPS點位 清單
		List<GPS> matcingGPSList = gpsService.findGpsWithinDistance(lng, lat);
		if(matcingGPSList.size() == 0) { //如果傳回值為0，表示查詢無符合資料
			return new ApiResponse(false, "無符合距離的GPS資料", getNow(), null);	
		}
		return new ApiResponse(true, "有符合距離的GPS資料", getNow(), matcingGPSList);	
	}
	
	
	
	
	// 新增
	@PostMapping
//	public Boolean addGPS(
//			// ?後面帶參數，或是網址後面帶參數，使用@RequestParam()
//			@RequestParam("latitude") Double latitude,
//			@RequestParam("longitude") Double longitude,
//			@RequestParam("meter") Integer meter,
//			@RequestParam("location") String location,
//			@RequestParam("locationName") String locationName) {
//		// 檢查參數
//		// 對檢查內容的程式，暫略...
//		// 傳送參數給 gpsService, 讓 gpsService 幫Controller新增
//		Boolean status = gpsService.addGPS(latitude, longitude, meter, location, locationName);
//		return status;
//	}
	
//	// 新增 改成response包裝模式
//	public ApiResponse addGPS(
//			// ?後面帶參數，或是網址後面帶參數，使用@RequestParam()
//			@RequestParam("latitude") 		Double latitude,
//			@RequestParam("longitude")		Double longitude,
//			@RequestParam("meter") 			Integer meter,
//			@RequestParam("location") 		String location,
//			@RequestParam("locationName") 	String locationName) {
//		// 檢查參數
//		// 對檢查內容的程式，暫略...
//		// 傳送參數給 gpsService, 讓 gpsService 幫Controller新增
//
//		ApiResponse apiResponse = null;
//		try {
//			Boolean status = gpsService.addGPS(latitude, longitude, meter, location, locationName);
//			
//			if (status == false) {
//				apiResponse = new ApiResponse(false, "新增失敗", getNow(), null);
//			}
//			apiResponse = new ApiResponse(true, "新增成功", getNow(), status);
//
//		} catch (Exception e) {
//			apiResponse = new ApiResponse(false, "新增錯誤", getNow(), e.getMessage());
//		}
//		return apiResponse;
//	}
	
	
	// 新增 改成JSON接收參數，response包裝模式
	// 已經由前端組裝完成，到這邊只有一個JSON gps物件，不用再組裝
		public ApiResponse addGPS(@RequestBody GPS gps) {
			// 檢查參數
			// 對檢查內容的程式，暫略...
			// 傳送參數給 gpsService, 讓 gpsService 幫Controller新增

			ApiResponse apiResponse = null;
			try {
				Boolean status = gpsService.addGPS(gps);
				if (status == false) {
					apiResponse = new ApiResponse(false, "新增失敗", getNow(), null);
				}
				apiResponse = new ApiResponse(true, "新增成功", getNow(), status);

			} catch (Exception e) {
				apiResponse = new ApiResponse(false, "新增錯誤", getNow(), e.getMessage());
			}
			return apiResponse;
		}
	
	
	// 修改(動態)
	// 動態用PatchMapping (完整修改用PutMapping)
	@PatchMapping("/{id}")
	public ApiResponse updateGPS(@PathVariable("id") Integer id, @RequestBody GPS gps) {
		
		// id原本是路徑參數，在這邊注入物件，service就不用再注入
		gps.setId(id);
		Boolean status = gpsService.updateGPS(gps);
		ApiResponse apiResponse = null;
		if (status) {
			apiResponse = new ApiResponse(true, "修改成功", getNow(), status);
		} else {
			apiResponse = new ApiResponse(false, "修改失敗", getNow(), null);
		}
		return apiResponse;	
	}
	
	
	// 刪除
	// 使用{id}接受傳來的變數
	// 例如： .../gps/1 <- 刪除 id = 1 的紀錄
	// 例如： .../gps/3 <- 刪除 id = 3 的紀錄
	// 例如： .../gps/5 <- 刪除 id = 5 的紀錄
	// 路徑後面帶參數，使用@PathVariable()
	@DeleteMapping("/{id}")
//	public Boolean deleteGPS(@PathVariable("id") Integer id) {
//		Boolean status = gpsService.deleteGps(id);
//		return status;
//	}
	// 改成response包裝模式
	public ApiResponse deleteGPS(@PathVariable("id") Integer id) {
		ApiResponse apiResponse = null;
		Boolean status = gpsService.deleteGps(id);
		if(status) {
			apiResponse = new ApiResponse(true, "刪除成功", getNow(), status);
		} else {
			apiResponse = new ApiResponse(false, "刪除失敗", getNow(), status);
		}
		return apiResponse;	
	}
	
	
}
