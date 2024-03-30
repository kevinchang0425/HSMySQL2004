package com.hs.rest.gps.model.response;

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
	private String datetime;
	//private T data;		// 也可以用	private Object data; 表示
	private Object data;	// 也可以用	private Object data; 表示
}

