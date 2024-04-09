// 檢查key是否正確的函式
const checkArgsKey = async() => {	// 用函式的方式寫，才可以依序執行  || 非同步函式 = async()  ||  = ()是同步函式
	 // 取得 URL key 資料
	 // 例如: /HSMySQL/mvc/argkey/check?key=abc123
	 try{
		 const url1 = window.location.href;
		 const args = url1.split("?")[1];
		 const key = args.split("=")[1];
		 console.log("key=" + key);
		 
		 // 檢查key是否正確
		 // 到 /HSMySQL/mvc/argkey/check?key=abc123 檢查key是否正確
		 const url2 = "/HSMySQL/mvc/argkey/check?key=" + key;	//定義可改變行代的型別 var ，送出request 並取得response
		 const response = await fetch(url2);					//定義不可改變行代的型別 const ，將 response 轉 json
		 const result = await response.json();
		 //console.log("result" + result);
		 // 取得 status
		 const status = result.status;
		 console.log("status = " + status);
		 return status;
	}catch (e){
		console.log("No key");
		return false;
	} 
};