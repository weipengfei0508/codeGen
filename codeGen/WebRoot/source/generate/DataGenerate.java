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
import com.whty.platform.generate.codegen.Tables;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 代码生成器
 * 
 * @author 舒海洋
 * @version 2013-03-15
 */
public class DataGenerate {

	private static Logger logger = LoggerFactory.getLogger(DataGenerate.class);

	public static void main(String[] args) throws Exception {

	

		// 读取数据库配置文件
		Properties po = FileUtil.initProperties("resource/application.properties");

		JdbcConfig jdbcConfig = new JdbcConfig(po.getProperty("jdbc.driver"),
				po.getProperty("jdbc.url"), po.getProperty("jdbc.username"),
				po.getProperty("jdbc.password"));

		String datebase = jdbcConfig.getUrl().split("//?")[2].split("\\?")[0]
				.toString(); // 数据库名

		

		MysqlProvider dbProvider = new MysqlProvider(jdbcConfig);
		
		String tablename = "cms_article"; // 表名
		datebase="ibsp2.0";
		List<Tables> tlist= dbProvider.getTablesList(datebase);
		for(int i=0;i<tlist.size();i++){
			Tables tb=tlist.get(i);
			List<TableModel> tblist = dbProvider.getTableSCRIPTModelList(tb.getTable_name(),datebase);
		}
	

	}

	public static void GenCode(String datebase, MysqlProvider dbProvider,
			String packageName, String moduleName, String subModuleName,
			String className, String classAuthor, String functionName,
			String tablename) throws IOException {
		if (StringUtils.isBlank(moduleName) || StringUtils.isBlank(moduleName)
				|| StringUtils.isBlank(className)
				|| StringUtils.isBlank(functionName)) {
			logger.error("参数设置错误：包名、模块名、类名、功能名不能为空。");
			return;
		}
		// 获取文件路径com.whty.platform
		String separator = File.separator;
		String classPath = new DefaultResourceLoader().getResource("")
				.getFile().getPath();
		String templatePath = classPath.replace(separator + "WebRoot"
				+ separator + "WEB-INF" + separator + "classes", separator
				+ "src" + separator + "com" + separator + "whty" + separator
				+ "platform" + separator + "modules");
		String javaPath = classPath.replace(
				separator + "WebRoot" + separator + "WEB-INF" + separator
						+ "classes",
				separator
						+ "src"
						+ separator
						+ (StringUtils.lowerCase(packageName)).replace(".",
								separator));
		String viewPath = classPath.replace(separator + "classes", separator
				+ "views");

		// 代码模板配置
		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(new File(templatePath.replace(
				"modules", "generate" + separator + "template")));
		// 从数据库中得到该表的所有字段
		List<TableModel> tblist = dbProvider.getTableSCRIPTModelList(tablename,
				datebase);

		// 定义模板变量
		Map<String, Object> tbmodel = Maps.newHashMap();
		tbmodel.put("tbmodel", tblist);
		tbmodel.put("packageName", StringUtils.lowerCase(packageName));
		tbmodel.put("adminPath", "${adminPath}");
		tbmodel.put("moduleName", StringUtils.lowerCase(moduleName));
		tbmodel.put(
				"subModuleName",
				StringUtils.isNotBlank(subModuleName) ? "."
						+ StringUtils.lowerCase(subModuleName) : "");
		tbmodel.put("className", StringUtils.uncapitalize(className));
		tbmodel.put("ClassName", StringUtils.capitalize(className));
		tbmodel.put("classAuthor",
				StringUtils.isNotBlank(classAuthor) ? classAuthor
						: "Generate Tools");
		tbmodel.put("classVersion", DateUtils.getDate());
		tbmodel.put("functionName", functionName);
		tbmodel.put(
				"tableName", StringUtils.lowerCase(tablename));
		tbmodel.put(
				"urlPrefix",
				tbmodel.get("moduleName")
						+ (StringUtils.isNotBlank(subModuleName) ? "/"
								+ StringUtils.lowerCase(subModuleName) : "")
						+ "/" + tbmodel.get("className"));
		tbmodel.put(
				"viewPrefix",
				StringUtils.substringAfterLast(
						(String) tbmodel.get("packageName"), ".")
						+ "/" + tbmodel.get("urlPrefix"));
		tbmodel.put(
				"permissionPrefix",
				tbmodel.get("moduleName")
						+ (StringUtils.isNotBlank(subModuleName) ? ":"
								+ StringUtils.lowerCase(subModuleName) : "")
						+ ":" + tbmodel.get("className"));

		// 生成 Entity
		Template template = cfg.getTemplate("dateentity.ftl");
		String content = FreeMarkers.renderTemplate(template, tbmodel);
		String filePath = javaPath + separator + tbmodel.get("moduleName")
				+ separator + "entity" + separator
				+ StringUtils.lowerCase(subModuleName) + separator
				+ tbmodel.get("ClassName") + ".java";
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
