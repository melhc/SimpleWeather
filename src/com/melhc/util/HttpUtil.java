package com.melhc.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
public class HttpUtil {
	/*
	 * 从服务器端获取省县市的数据
	 */
	public static void sendHttpRequest(final String address,
			final HttpCallbackListener listener) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				HttpURLConnection connection = null;
				try {
					//创建一个url对象
					URL url = new URL(address);
					//通过url对象获取HTTPURLCONNCETION实例
					connection = (HttpURLConnection) url.openConnection();
					//设置http的请求所使用的方法为get方法
					connection.setRequestMethod("GET");
					//自由定制一些属性，比如设置连接超时，读取超时的毫秒数
					connection.setConnectTimeout(8000);
					connection.setReadTimeout(8000);
					//获得服务器返回的输入流
					InputStream in = connection.getInputStream();
					//将得到的输入流装换成String字符串
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(in, "utf-8"));
					StringBuffer response = new StringBuffer();
					String line;
					while ((line = reader.readLine()) != null) {
						response.append(line);
					}
					LogUtil.i("HTTPUtil",
							"------------------>" + response.toString());
					if (listener != null) {
						listener.onFinish(response.toString());
					}
				} catch (Exception e) {
					// TODO: handle exception
					if (listener != null) {
						listener.onError(e);
					}
				} finally {
					if (connection != null) {
						connection.disconnect();
					}
				}
			}
		}).start();
	}

}
