package com.whty.platform.generate.codegen;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;


/**
 * 数据库信息提供者。该抽象类基于JDBC实现了与数据库方言无关的方法，把与数据库方言相关的操作分派给子类实现。
 * @author 黄天政 
 */
public abstract class DbProvider {
	private Connection conn;
	private JdbcConfig jdbcConfig;

	/**
	 * 根据数据库连接构造一个数据库信息提供者
	 * @param conn 数据库连接
	 */
	public DbProvider(Connection conn) {
		super();
		this.conn = conn;
	}
	
	/**
	 * 根据jdbc配置模型构造一个数据库信息提供者
	 * @param jdbcConfig jdbc配置模型
	 */
	public DbProvider(JdbcConfig jdbcConfig) {
		super();
		this.jdbcConfig = jdbcConfig;
	}
	
	/**
	 * @return 获取一个数据库连接
	 */
	protected Connection getConn() {
		if(conn==null){
			if(jdbcConfig==null){
				try {
					throw new Exception(this.getClass().getName()+"jdbcConfig和conn不能同时为null");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			conn = JdbcUtil.getConn(jdbcConfig);
		}
		return conn;
	}
	
	
}
