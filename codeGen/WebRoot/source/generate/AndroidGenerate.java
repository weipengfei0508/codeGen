/**
 * Copyright &copy; 2012-2013 <a href="www.whty.com.cn">whty</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.whty.platform.generate;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;

import com.google.common.collect.Maps;
import com.whty.platform.common.utils.DateUtils;
import com.whty.platform.generate.codegen.FileUtil;
import com.whty.platform.generate.codegen.FileUtils;
import com.whty.platform.generate.codegen.FreeMarkers;
import com.whty.platform.generate.codegen.JdbcConfig;
import com.whty.platform.generate.codegen.MysqlProvider;
import com.whty.platform.generate.codegen.TableModel;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 代码生成器
 * 
 * @author 舒海洋
 * @version 2013-03-15
 */
public class AndroidGenerate {
	
	

	private static Logger logger = LoggerFactory.getLogger(AndroidGenerate.class);

	public static void main(String[] args) throws Exception {
		String packagename="com.whty.xmytktsm.cardbag.ui.biz";
		
		//圈存订单创建接口
		/*String []params={"tag","telephone","amount","pay_chanel"};
		String classname="TrapOrder"; //圈存
*/		
		
		//圈存订单创建接口
		String []params={"tag","telephone","amount","pay_chanel"};
		String classname="TrapOrder"; //圈存
		
		GenCode(packagename,classname,params);
	}

	public static void GenCode(String packagename,String classname,String []params) throws IOException {
		String separator = File.separator;
		String classPath = new DefaultResourceLoader().getResource("").getFile().getPath();
		System.out.println("classPath="+classPath);
//		String templatePath = classPath.replace(separator + "WebRoot"
//				+ separator + "WEB-INF" + separator + "classes", separator
//				+ "src" + separator + "com" + separator + "whty" + separator
//				+ "platform" + separator + "modules");
		String templatePath = classPath.replace(separator + "build"+ separator + "classes", separator
				+ "src" + separator + "com" + separator + "whty" + separator
				+ "platform" + separator + "modules");
		System.out.println("templatePath="+templatePath);
		
		// 代码模板配置
		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(new File(templatePath.replace(
				"modules", "generate" + separator + "template")));
		
		// 定义模板变量
		Map<String, Object> tbmodel = Maps.newHashMap();
		
		tbmodel.put("packageName", packagename);
		tbmodel.put("className", classname);
		tbmodel.put("params", params);
		
		//生成activityManager.java
		Template template = cfg.getTemplate("androidentity.ftl");
		String content = FreeMarkers.renderTemplate(template, tbmodel);
		String filePath =  "D://gencode//";
		filePath=filePath+classname+"ActivityManager.java";
		writeFile(content, filePath);
		logger.info(filePath);

	}

	/**
	 * 将内容写入文件
	 * 
	 * @param content
	 * @param filePath
	 */
	public static void writeFile(String content, String filePath) {
		try {
			if (FileUtils.createFile(filePath)) {
				FileWriter fileWriter = new FileWriter(filePath, true);
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
				bufferedWriter.write(content);
				bufferedWriter.close();
				fileWriter.close();
			} else {
				logger.info("生成失败，文件已存在！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
