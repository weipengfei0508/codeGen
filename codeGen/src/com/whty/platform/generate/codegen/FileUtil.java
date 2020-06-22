package com.whty.platform.generate.codegen;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;




/**
 * 文件操作工具类
 */
public class FileUtil {
	
	/**
	 * 根据键值获取properties文件的值
	 * @param key properties键值
	 * @param filepath  properties文件路径
	 * @return
	 */
	public static String getPropertiesByKey(String key,String filepath){
		Properties po=new Properties();
		FileInputStream inStream;
		try {
			inStream = new FileInputStream("resource/application.properties");
			po.load(inStream);
			return po.getProperty(key);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 初始化properties文件
	 * @param key
	 * @param filepath
	 * @return
	 */
	public static Properties initProperties(String filepath){
		Properties po=new Properties();
		FileInputStream inStream;
		try {
			inStream = new FileInputStream(filepath);
			po.load(inStream);
			return po;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
}
