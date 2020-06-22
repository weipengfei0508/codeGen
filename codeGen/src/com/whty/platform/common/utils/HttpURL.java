package com.whty.platform.common.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

/**
 * HTTP通讯工具
 * 
 * @author 舒海洋
 * */
public class HttpURL {

	private static final String $req_method_post = "POST";
	private static final String $property_name_contentType = "Content-Type";
	private static final String $property_value_contentType = "application/x-www-form-urlencoded";
	private static final String $property_name_cache = "Cache-Control";
	private static final String $property_value_cache = "no-cache";

	private static final int _minute = 1000 * 60;
	private static final String $line_feed = System.getProperty("line.separator");

	/**
	 * post方式提交请求
	 * 
	 * @param strURL
	 *            . 请求地址
	 * @param argsMap
	 *            . 参数键值对
	 * @return 服务器返回
	 * @throws Exception
	 * */
	public String doPost(String strURL, Map<String, String> argsMap) throws Exception {
		StringBuffer sbReturn = new StringBuffer("");
		URL url = null;
		HttpURLConnection httpConnection = null;
		InputStream in = null;
		OutputStream out = null;
		BufferedReader br = null;

		try {
			url = new URL(strURL);
			httpConnection = (HttpURLConnection) url.openConnection();

			httpConnection.setRequestMethod($req_method_post);
			httpConnection.setRequestProperty($property_name_contentType, $property_value_contentType);
			httpConnection.setRequestProperty($property_name_cache, $property_value_cache);
			httpConnection.setDoInput(true);
			httpConnection.setDoOutput(true);
			httpConnection.setConnectTimeout(_minute);
			httpConnection.setReadTimeout(_minute);

			// 发送请求
			out = httpConnection.getOutputStream();
			byte[] b = this.parseParamMap(argsMap).getBytes();
			out.write(b, 0, b.length);
			out.flush();
			out.close();

			// 接收返回
			in = httpConnection.getInputStream();
			br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			String strRead = "";
			while ((strRead = br.readLine()) != null) {
				sbReturn.append(strRead);
				sbReturn.append($line_feed);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (Exception fx) {
				fx.printStackTrace();
			}
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception fx) {
				fx.printStackTrace();
			}
			try {
				if (br != null) {
					br.close();
				}
			} catch (Exception fx) {
				fx.printStackTrace();
			}
			try {
				if (httpConnection != null) {
					httpConnection.disconnect();
				}
			} catch (Exception fx) {
				fx.printStackTrace();
			}
		}
		return sbReturn.toString();
	}

	public String doGet(String strURL, Map<String, String> argsMap) throws Exception {
		return doPost(strURL, argsMap);
	}

	/**
	 * 解析请求参数键值对
	 * 
	 * @param argsMap
	 *            . 请求参数键值对
	 * @return http post请求参数字符串
	 * */
	private String parseParamMap(Map<String, String> argsMap) throws Exception {
		StringBuffer sbParams = new StringBuffer("");
		String param = "";
		boolean nofirst = false;
		Iterator<String> itor = argsMap.keySet().iterator();
		while (itor.hasNext()) {
			param = itor.next();
			if (nofirst)
				sbParams.append("&");

			sbParams.append(param);
			sbParams.append("=");
			sbParams.append(URLEncoder.encode(argsMap.get(param), "utf-8"));
			nofirst = true;
		}
		return sbParams.toString();
	}
}
