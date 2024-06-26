package com.hs.rest.key.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hs.rest.key.model.po.ArgKey;
import com.hs.rest.key.model.response.ApiResponse;
import com.hs.rest.key.service.ArgKeyService;

@RestController
@RequestMapping("/argkey")
public class ArgKeyController {

	@Autowired
	private ArgKeyService argKeyService;

	// 確認驗證碼
	@GetMapping("/check")
	// 使用範例： /argkey/check?key=abc123
	public ApiResponse checkKey(@RequestParam("key") String key) {
		Boolean result = argKeyService.checkKey(key);
		if (result) {
			return new ApiResponse(true, "驗證成功", result);
		} else {
			return new ApiResponse(false, "驗證失敗", result);
		}
	}

	// 取得驗證碼
	@GetMapping
	// 使用範例： https://localhost:8443/HSMySQL/mvc/argkey
	public ApiResponse getKey() {
		ArgKey argKey = argKeyService.getArgKey();
		if(argKey != null) {
			return new ApiResponse(true, "取得成功", argKey);
		} else {
			return new ApiResponse(false, "取得失敗", argKey);
		}
	}

	// 修改驗證碼(全部)
	@PutMapping
	// 使用範例： /argkey
	public ApiResponse update(@RequestBody ArgKey argKey) {
		Boolean result = argKeyService.update(argKey);
		if (result) {
			return new ApiResponse(true, "更新成功", result);
		} else {
			return new ApiResponse(false, "更新失敗", result);
		}
	}
	
	// 只修改驗證碼
	@PatchMapping
	// 使用範例： /argkey
	public ApiResponse updateKey(@RequestBody ArgKey argKey) {
		// 取得Key
		String key = argKey.getStrArg1();
		Boolean result = argKeyService.updateKey(key);
		if (result) {
			return new ApiResponse(true, "更新Key成功", result);
		} else {
			return new ApiResponse(false, "更新Key失敗", result);
		}
	}
	

}
