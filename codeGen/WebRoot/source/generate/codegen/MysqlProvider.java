package com.whty.platform.generate.codegen;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

/**
 * 针对Oracle的数据库信息提供者
 * @author 黄天政
 *
 */
public class MysqlProvider extends DbProvider {

	public MysqlProvider(Connection conn) {
		super(conn);
	}
	
	public MysqlProvider(JdbcConfig jdbcConfig) {
		super(jdbcConfig);
	}
	
	
	/**
	 * 根据表名和库名得到该表的所有列模型
	 * @param tableName 表名
	 * @param Schema　库名
	 * @return
	 * @throws IOException
	 */
	public List<TableModel> getTableSCRIPTModelList(String tableName,String Schema) throws IOException{
	
		Statement stmt = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		String sql = "select * from  Information_schema.columns where TABLE_NAME='"+tableName.toLowerCase()+"' and table_schema='"+Schema.toLowerCase()+"'";
		TableModel tb=null;
		List<TableModel> list=new ArrayList<TableModel>();
		try{
			stmt = getConn().createStatement();
			rs = stmt.executeQuery(sql);
			//rs1=stmt.executeQuery(sql);
			String commstr="";
			String priStr="";
			String sqlstr="-- Create table \r\n"
			+" create table "+tableName +"\r\n"
			+"(\r\n";
			boolean first=true;
			while(rs.next()){
				String column_name = rs.getString("column_name").toLowerCase(); //列名
				//System.out.println(column_name);
				String is_nullable = rs.getString("is_nullable").toLowerCase(); //是否允许为空
				//System.out.println(is_nullable);
				String column_key=rs.getString("column_key").toLowerCase(); //主键信息
				//System.out.println(column_key);
				String column_type = rs.getString("column_type").toLowerCase(); //数据类型
				//System.out.println(column_type);
				String column_comment = rs.getString("column_comment").toLowerCase(); //备注
				//System.out.println(column_comment);
				if(first==true){
					sqlstr=sqlstr +" \r\n";
					first=false;
				}else{
					sqlstr=sqlstr+", \r\n";
				}
			
				if(column_key.toLowerCase().equals("pri")){
					sqlstr=sqlstr+column_name+" number not null ";
					priStr=priStr+"alter table "+ tableName+"\r\n";
					priStr=priStr+" add constraint PK_"+tableName +"_ID primary key ("+column_name+");\r\n";
				}else{
					sqlstr=sqlstr+column_name+"  "+column_type.replace("bigint(20)", "number").replace("bigint", "number").replace("varchar", "varchar2").replace("int", "number").replace("datetime","date").replace("text", "CLOB").replace("double", "number");
					if(is_nullable.equals("no")){
						sqlstr=sqlstr+" not null ";
					}
				}
				
				//String pri=rs.getString("column_key").toLowerCase();//主键
				//String data_type = rs.getString("data_type").toLowerCase(); //数据类型
				//String character_maximum_length= rs.getString("character_maximum_length").toLowerCase(); //字符长度
				//String numeric_precision = rs.getString("numeric_precision").toLowerCase(); //数字长度
				//String numeric_scale  = rs.getString("numeric_scale").toLowerCase(); //小数位数
				//String extra=rs.getString("extra").toLowerCase(); //是否自增
				
//				String column_type_name;
//				String column_type_jname;
//				int column_type_num1=0;
//				int column_type_num2=0;
//				String []typesplit=column_type.split("\\(");
//				if(typesplit.length>1){
//					column_type_name=typesplit[0]; //类型名
//					String column_lengths=typesplit[1];
//					if(!column_lengths.equals("")){
//						String []column_length=column_lengths.split("\\)");
//						
//						if(column_length[0].split(",").length>1){
//							column_type_num1=Integer.parseInt(column_length[0].split(",")[0]);
//							column_type_num2=Integer.parseInt(column_length[0].split(",")[1]);
//						}else{
//							column_type_num1=Integer.parseInt(column_length[0]);
//						}
//						
//					}
//				}else{
//					column_type_name=column_type;
//				}
//				Properties po=FileUtil.initProperties("resource/datatype.properties");
//				column_type_jname=po.getProperty(column_type_name);
//				tb=new TableModel(column_name, is_nullable, extra, column_key, column_type, column_comment,column_type_name, column_type_jname, column_type_num1,column_type_num2);
//				list.add(tb);	
				commstr=commstr+"comment on column "+tableName+"."+column_name+" is '"+column_comment+"';\r\n";
			}
			sqlstr=sqlstr+" \r\n);";
			System.out.println(sqlstr);
			System.out.println("-- Add comments to the columns ");
			System.out.println(commstr);
			System.out.println(priStr);
			/*while(rs1.next()){
				String column_comment = rs1.getString("column_comment").toLowerCase(); //备注
				String column_name = rs1.getString("column_name").toLowerCase(); //列名
				System.out.println("comment on column "+tableName+"."+column_name+" is '"+column_name+"';");
			}*/
			return list;
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}finally{
			JdbcUtil.safelyClose(rs, stmt);
		}
	}
	
	
	/**
	 * 根据表名和库名得到该表的所有列模型
	 * @param tableName 表名
	 * @param Schema　库名
	 * @return
	 * @throws IOException
	 */
	public List<TableModel> getTableModelList(String tableName,String Schema) throws IOException{
	
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select * from  Information_schema.columns where TABLE_NAME='"+tableName.toUpperCase()+"' and table_schema='"+Schema.toUpperCase()+"'";
		TableModel tb=null;
		List<TableModel> list=new ArrayList<TableModel>();
		try{
			stmt = getConn().createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				String column_name = rs.getString("column_name").toLowerCase(); //列名
				String is_nullable = rs.getString("is_nullable").toLowerCase(); //是否允许为空
//				String data_type = rs.getString("data_type").toLowerCase(); //数据类型
//				String character_maximum_length= rs.getString("character_maximum_length").toLowerCase(); //字符长度
//				String numeric_precision = rs.getString("numeric_precision").toLowerCase(); //数字长度
//				String numeric_scale  = rs.getString("numeric_scale").toLowerCase(); //小数位数
				String extra=rs.getString("extra").toLowerCase(); //是否自增
				String column_key=rs.getString("column_key").toLowerCase(); //主键信息
				String column_type = rs.getString("column_type").toLowerCase(); //数据类型
				String column_comment = rs.getString("column_comment").toLowerCase(); //备注
				
				String column_type_name;
				String column_type_jname;
				int column_type_num1=0;
				int column_type_num2=0;
				String []typesplit=column_type.split("\\(");
				if(typesplit.length>1){
					column_type_name=typesplit[0]; //类型名
					String column_lengths=typesplit[1];
					if(!column_lengths.equals("")){
						String []column_length=column_lengths.split("\\)");
						
						if(column_length[0].split(",").length>1){
							column_type_num1=Integer.parseInt(column_length[0].split(",")[0]);
							column_type_num2=Integer.parseInt(column_length[0].split(",")[1]);
						}else{
							column_type_num1=Integer.parseInt(column_length[0]);
						}
						
					}
				}else{
					column_type_name=column_type;
				}
				Properties po=FileUtil.initProperties("resource/datatype.properties");
				column_type_jname=po.getProperty(column_type_name);
				tb=new TableModel(column_name, is_nullable, extra, column_key, column_type, column_comment,column_type_name, column_type_jname, column_type_num1,column_type_num2);
				list.add(tb);		
			}
			return list;
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}finally{
			JdbcUtil.safelyClose(rs, stmt);
		}
	}
	
	public List<Tables> getTablesList(String Schema) throws IOException{
		
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select  * from information_schema.tables t where t.table_schema='"+Schema.toLowerCase()+"'";
		Tables tb=null;
		List<Tables> list=new ArrayList<Tables>();
		try{
			stmt = getConn().createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				String table_name = rs.getString("table_name").toLowerCase(); //列名
				String table_comment = rs.getString("table_comment").toLowerCase(); //是否允许为空
				tb=new Tables(table_name, table_comment);
				list.add(tb);		
			}
			return list;
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}finally{
			JdbcUtil.safelyClose(rs, stmt);
		}
	}
	

}
