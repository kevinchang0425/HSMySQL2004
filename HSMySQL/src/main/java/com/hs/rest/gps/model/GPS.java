package com.hs.rest.gps.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 資料庫GPS
@Data	//加入lombok功能，自動Getter、Setter、toStrong、建構子、equals、hashCode
@AllArgsConstructor	//加入lombok功能，自動有參數建構子
@NoArgsConstructor	//加入lombok功能，自動無參數建構子
public class GPS {
	// 欄位
	private Integer id;
	private Double latitude;
	private Double longitude;
	private Integer meter;
	private String location;
	private String locationName;
}
