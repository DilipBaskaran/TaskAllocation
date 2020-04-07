package com.techmahindra.taskallocation.util;

import java.util.Base64;

public class EncodingUtil {
	
	public static String encode(String str) {
		return Base64.getEncoder() 
                .encodeToString( str.getBytes());
	}

	
	public static String decode(String str) {
		return Base64.getDecoder()
				.decode(str.getBytes()).toString();
	}
}
