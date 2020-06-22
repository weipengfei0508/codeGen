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
import com.whty.platform.common.utils.FileUtils;
import com.whty.platform.common.utils.FreeMarkers;
import com.whty.platform.generate.codegen.FileUtil;
import com.whty.platform.generate.codegen.GenFunctions;
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
public class Generate {

	private static Logger logger = LoggerFactory.getLogger(Generate.class);

	public static void main(String[] args) throws Exception {

		// 是否启用生成工具
		Boolean isEnable = true;

		// 是否生成所有代码
		Boolean isGenerateAll = false;

		// 生成代码所在的包
		String packageName = "com.ztxy.dwps";

		// 读取数据库配置文件
		Properties po = FileUtil.initProperties("resource/application.properties");

		JdbcConfig jdbcConfig = new JdbcConfig(po.getProperty("jdbc.driver"),po.getProperty("jdbc.url"), po.getProperty("jdbc.username"),po.getProperty("jdbc.password"));

		String datebase = jdbcConfig.getUrl().split("//?")[2].split("\\?")[0].toString(); // 数据库名

		if (!isEnable) {
			logger.error("请启用代码生成工具，设置参数：isEnable = true");
			return;
		}

		MysqlProvider dbProvider = new MysqlProvider(jdbcConfig);
		String classAuthor = "魏鹏飞"; // 类作者，例：ThinkGem
		if (isGenerateAll) {
			// 批量表生成代码
			List<Tables> tblist = dbProvider.getTablesList(datebase);
			int tlen = tblist.size();
			for (int j = 0; j < tlen; j++) {
				Tables tables = (Tables) tblist.get(j);
				String tablename = tables.getTable_name(); // 表名
				String functionName = tables.getTable_comment(); // 功能名，例：用户
				if (functionName == null || functionName.equals("")) {
					functionName = "未设置表标记信息";
				}
				String subModuleName = ""; // 子模块名（可选）
				String moduleName = "test";
				String className = "test";
				String[] stable = tablename.split("_");
				int slen = stable.length;
				if (slen > 1) {
					moduleName = stable[0].toLowerCase();
					className = "";
					for (int i = 1; i < slen; i++) {
						className = className
								+ GenFunctions.firstToUpperCase(stable[i]
										.toLowerCase());
					}
				}

				GenCode(datebase, dbProvider, packageName, moduleName,
						subModuleName, className, classAuthor, functionName,
						tablename);
			}

		} else {
			// 单张表生成代码
			String moduleName = "postion"; // 模块名，例：sys
			String subModuleName = ""; // 子模块名（可选）
			String functionName = "城市预警设置表"; // 功能名，例：用户
			String tablename = "base_alarm_city"; // 表名
			String className = "";
			
			String[] stable = tablename.split("_");
			int slen = stable.length;
			if (slen > 1) {
				moduleName = stable[0].toLowerCase();
				for (int i = 1; i < slen; i++) {
					className = className+ GenFunctions.firstToUpperCase(stable[i].toLowerCase());
				}
			}
			GenCode(datebase, dbProvider, packageName, moduleName,subModuleName, className, classAuthor, functionName,tablename);
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

		String separator = File.separator;

		String classPath = new DefaultResourceLoader().getResource("")
				.getFile().getPath();
		String projectPath = classPath.replace(separator + "WebRoot"
				+ separator + "WEB-INF" + separator + "classes", "");
		String templatePath = projectPath + "\\templete";
		String javaPath = projectPath + "\\code";

		// 代码模板配置
		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(new File(templatePath));
		// 从数据库中得到该表的所有字段
		List<TableModel> tblist = dbProvider.getTableModelList(tablename,
				datebase);

		// 定义模板变量
		Map<String, Object> tbmodel = Maps.newHashMap();
		tbmodel.put("tbmodel", tblist);
		tbmodel.put("packageName", StringUtils.lowerCase(packageName));
		tbmodel.put("moduleName", StringUtils.lowerCase(moduleName));
		tbmodel.put("subModuleName",StringUtils.isNotBlank(subModuleName) ? "."+ StringUtils.lowerCase(subModuleName) : "");
		tbmodel.put("className", StringUtils.uncapitalize(className));
		tbmodel.put("ClassName", StringUtils.capitalize(className));
		tbmodel.put("classAuthor",StringUtils.isNotBlank(classAuthor) ? classAuthor : "Generate Tools");
		tbmodel.put("classVersion", DateUtils.getDate());
		tbmodel.put("functionName", functionName);
		tbmodel.put("tableName", tablename);
		
	

		// 生成 Entity
		Template template = cfg.getTemplate("entity.ftl");
		String content = FreeMarkers.renderTemplate(template, tbmodel);
		String filePath = javaPath + separator + tbmodel.get("ClassName")
				+ ".java";
		writeFile(content, filePath);
		logger.info(filePath);

		// 生成 Dao
		template = cfg.getTemplate("dao.ftl");
		content = FreeMarkers.renderTemplate(template, tbmodel);
		filePath = javaPath + separator + tbmodel.get("ClassName")
				+ "DAO.java";
		writeFile(content, filePath);
		logger.info(filePath);

		// 生成 Controller
		template = cfg.getTemplate("controller.ftl");
		content = FreeMarkers.renderTemplate(template, tbmodel);
		filePath = javaPath + separator  + tbmodel.get("ClassName") + "Controller.java";
		writeFile(content, filePath);
		logger.info(filePath);

		// 生成 IService
		template = cfg.getTemplate("Iservice.ftl"); 
		content =FreeMarkers.renderTemplate(template, tbmodel); 
		filePath =javaPath + separator + tbmodel.get("ClassName") +"Service.java"; 
		writeFile(content, filePath);
		logger.info(filePath);
		 

		// 生成 ServiceImpl
		template = cfg.getTemplate("serviceimpl.ftl");
		content = FreeMarkers.renderTemplate(template, tbmodel);
		filePath = javaPath + separator +  tbmodel.get("ClassName") + "ServiceImpl.java";
		writeFile(content, filePath);
		logger.info(filePath);
		 
		// 生成 ServiceImpl
		template = cfg.getTemplate("mapper.ftl");
		content = FreeMarkers.renderTemplate(template, tbmodel);
		filePath = javaPath + separator +  tbmodel.get("ClassName") + "Dao.xml";
		writeFile(content, filePath);
		logger.info(filePath);


		logger.info(tablename + "代码生成成功！");
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
