package com.whty.platform.generate.codegen;

import java.io.Serializable;

public class Tables implements Serializable{
	
	/**
	 * 表名
	 */
	private String table_name;
	
	/**
	 * 表评论
	 */
	private String table_comment;

	public Tables() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Tables(String tableName, String tableComment) {
		super();
		table_name = tableName;
		table_comment = tableComment;
	}

	public String getTable_name() {
		return table_name;
	}

	public void setTable_name(String tableName) {
		table_name = tableName;
	}

	public String getTable_comment() {
		return table_comment;
	}

	public void setTable_comment(String tableComment) {
		table_comment = tableComment;
	}
	
	
	
}
