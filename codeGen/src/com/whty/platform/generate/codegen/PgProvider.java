package com.whty.platform.generate.codegen;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 针对Oracle的数据库信息提供者
 * @author 黄天政
 *
 */
public class PgProvider extends DbProvider {

	public PgProvider(Connection conn) {
		super(conn);
	}
	
	public PgProvider(JdbcConfig jdbcConfig) {
		super(jdbcConfig);
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
		String sql = "select * from v_pg_columns where tablename='"+tableName.toLowerCase()+"'";
		TableModel tb=null;
		List<TableModel> list=new ArrayList<TableModel>();
		try{
			stmt = getConn().createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				 
				String column_name = rs.getString("colname").toLowerCase(); //列名
				String is_nullable = rs.getString("not_null").toLowerCase(); //是否允许为空
//				String data_type = rs.getString("data_type").toLowerCase(); //数据类型
//				String character_maximum_length= rs.getString("character_maximum_length").toLowerCase(); //字符长度
//				String numeric_precision = rs.getString("numeric_precision").toLowerCase(); //数字长度
//				String numeric_scale  = rs.getString("numeric_scale").toLowerCase(); //小数位数
				String extra=""; //是否自增
				String column_key="n"; //主键信息
				String column_type = rs.getString("type").toLowerCase(); //数据类型
				String column_comment = rs.getObject("comment")==null ?"":rs.getString("comment");//备注
				
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
				column_type_jname=po.getProperty(column_type_name.replaceAll(" ", ""));
				
				
				String javaColumn=column_name;
				String[] scolumn = column_name.split("_");
				int slen = scolumn.length;
				if (slen > 1) {
					javaColumn=scolumn[0].toLowerCase();
					for (int i = 1; i < slen; i++) {
						javaColumn = javaColumn+ GenFunctions.firstToUpperCase(scolumn[i].toLowerCase());
					}
				}
				tb=new TableModel(column_name, javaColumn,is_nullable, extra, column_key, column_type, column_comment,column_type_name, column_type_jname, column_type_num1,column_type_num2);
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
		String sql = "select * from pg_tables where tableowner='ef' and schemaname='public'";
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
