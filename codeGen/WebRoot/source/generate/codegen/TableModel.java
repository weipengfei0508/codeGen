package com.whty.platform.generate.codegen;

import java.io.Serializable;

public class TableModel implements Serializable{
	
	
	private String column_name; //列名
	private String is_nullable; //是否允许为空
	private String extra; //是否自增 auto_increment
	private String column_key; //主键信息 PRI
	private String column_type; //数据类型 decimal(10,2), datetime
	private String column_comment; //备注 
	
	
	private String column_type_name;
	private String column_type_jname;
	private int column_type_num1;
	private int column_type_num2;
	

	public TableModel() {
		super();
		// TODO Auto-generated constructor stub
	}


	
	public TableModel(String columnName, String isNullable, String extra,
			String columnKey, String columnType, String columnComment,
			String columnTypeName, String columnTypeJname, int columnTypeNum1,
			int columnTypeNum2) {
		super();
		column_name = columnName;
		is_nullable = isNullable;
		this.extra = extra;
		column_key = columnKey;
		column_type = columnType;
		column_comment = columnComment;
		column_type_name = columnTypeName;
		column_type_jname = columnTypeJname;
		column_type_num1 = columnTypeNum1;
		column_type_num2 = columnTypeNum2;
	}



	public String getColumn_name() {
		return column_name;
	}
	public void setColumn_name(String columnName) {
		column_name = columnName;
	}
	public String getIs_nullable() {
		return is_nullable;
	}
	public void setIs_nullable(String isNullable) {
		is_nullable = isNullable;
	}
	public String getExtra() {
		return extra;
	}
	public void setExtra(String extra) {
		this.extra = extra;
	}
	public String getColumn_key() {
		return column_key;
	}
	public void setColumn_key(String columnKey) {
		column_key = columnKey;
	}
	public String getColumn_type() {
		return column_type;
	}
	public void setColumn_type(String columnType) {
		column_type = columnType;
	}
	public String getColumn_comment() {
		return column_comment;
	}
	public void setColumn_comment(String columnComment) {
		column_comment = columnComment;
	}

	public String getColumn_type_name() {
		return column_type_name;
	}

	public void setColumn_type_name(String columnTypeName) {
		column_type_name = columnTypeName;
	}

	public String getColumn_type_jname() {
		return column_type_jname;
	}

	public void setColumn_type_jname(String columnTypeJname) {
		column_type_jname = columnTypeJname;
	}

	public int getColumn_type_num1() {
		return column_type_num1;
	}

	public void setColumn_type_num1(int columnTypeNum1) {
		column_type_num1 = columnTypeNum1;
	}

	public int getColumn_type_num2() {
		return column_type_num2;
	}

	public void setColumn_type_num2(int columnTypeNum2) {
		column_type_num2 = columnTypeNum2;
	}
	
	
	
}
