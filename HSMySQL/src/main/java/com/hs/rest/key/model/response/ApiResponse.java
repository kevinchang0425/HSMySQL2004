package com.hs.rest.key.model.response;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// API的制服物件

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {	// 可以用犯行<T>表示：取type的字首，指的是任意型態，並指定為泛型ApiResponse<T>
	private Boolean status;
	private String message;
	private String datetime = getNow();
	//private T data;		// 也可以用	private Object data; 表示
	private Object data;	// 也可以用	private Object data; 表示
	
	
	
	// 建立日期時間顯示格式
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
	
	// 取得現在時間
	private String getNow() {
		return sdf.format(new Date());
	}

	public ApiResponse(Boolean status, String message, Object data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}
	
}

