package com.melhc.util;

/*
 *  网路连接的回掉接口
 */
public interface HttpCallbackListener {
	void onFinish(String response);

	void onError(Exception e);
}
