
package com.hs.rest.key.model.po;

import lombok.Data;

// 對應的資料表：args
// 使用方式 ?key=abc123 給特殊全縣的人使用，每次使用後必須更新
// id 一律都是7
// id, name, strArg1,	memo

@Data
public class ArgKey {
    public static final Integer id = 7;	// 固定值，在資料庫args表格中id是7，並設定為開放資源，不可變更(常數)
    private String name;
    private String strArg1;			// 放登入URL的特殊登入kye
    private String memo;
    
}
