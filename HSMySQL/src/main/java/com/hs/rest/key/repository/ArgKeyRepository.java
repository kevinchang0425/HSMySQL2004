package com.hs.rest.key.repository;

import com.hs.rest.gps.model.po.GPS;
import com.hs.rest.key.model.po.ArgKey;

public interface ArgKeyRepository {
	// 比對 key 是否正確
	public Boolean checkKey(String key);
	// 取得資料，id = 7 是固定的
	public ArgKey getArgKey();
	// 更新資料
	public Boolean update(ArgKey argKey);
	// 只更新Key
	public Boolean updateKey(String newkey);
	
}
