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
public class SqlServerProvider extends DbProvider {

	public SqlServerProvider(Connection conn) {
		super(conn);
	}
	
	public SqlServerProvider(JdbcConfig jdbcConfig) {
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
		//String sql = "select * from v_pg_columns where tablename='"+tableName.toLowerCase()+"'";
		String sql="select cast(A.NAME as nvarchar) as colname,cast(B.NAME as nvarchar) + (case when B.NAME ='numeric' then '('+cast(A.prec as nvarchar)+','+cast(A.scale as nvarchar)+')' else '' end) as column_type,cast(G.[VALUE] as nvarchar) as column_comment,"
         + " (SELECT 1  FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE Z  WHERE TABLE_NAME=D.NAME and A.NAME = Z.column_name  ) as is_pk "
         + " FROM   SYSCOLUMNS   A LEFT   JOIN   SYSTYPES   B   ON   A.XTYPE=B.XUSERTYPE INNER   JOIN   SYSOBJECTS   D   ON   A.ID=D.ID     AND   D.XTYPE='U'   AND     D.NAME!='DTPROPERTIES' "
         + " LEFT   JOIN   SYS.EXTENDED_PROPERTIES G   ON   A.ID=G.MAJOR_ID   AND   A.COLID=G.MINOR_ID "
         + "  LEFT   JOIN   SYS.EXTENDED_PROPERTIES F   ON   D.ID=F.MAJOR_ID   AND   F.MINOR_ID   =0 "
         + " where   D.NAME = '"
         +tableName.toLowerCase()
         +"' ORDER   BY   A.COLORDER ";
		TableModel tb=null;
		List<TableModel> list=new ArrayList<TableModel>();
		try{
			stmt = getConn().createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				 
				String column_name = rs.getString("colname").toLowerCase(); //列名
				String is_nullable = "f"; //是否允许为空
				String column_type = rs.getString("column_type").toLowerCase(); //数据类型
				String column_comment = rs.getObject("column_comment")==null ?"":rs.getString("column_comment");//备注
				String extra=""; //是否自增
				String column_key="n"; //主键信息
				String column_type_jname;
				
				Properties po=FileUtil.initProperties("resource/datatype.properties");
				column_type_jname=po.getProperty(column_type.replaceAll(" ", ""));
				
				int column_type_num1=0;
				int column_type_num2=0;
				String javaColumn=column_name;
				String[] scolumn = column_name.split("_");
				int slen = scolumn.length;
				if (slen > 1) {
					javaColumn=scolumn[0].toLowerCase();
					for (int i = 1; i < slen; i++) {
						javaColumn = javaColumn+ GenFunctions.firstToUpperCase(scolumn[i].toLowerCase());
					}
				}
				tb=new TableModel(column_name, javaColumn,is_nullable, extra, column_key, column_type, column_comment,column_type, column_type_jname, column_type_num1,column_type_num2);
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
		String sql = "SELECT cast(A.name as varchar(500)) AS table_name,cast(C.value as varchar(500)) AS table_comment FROM sys.tables A LEFT JOIN sys.extended_properties C ON C.major_id = A.object_id WHERE C.minor_id=0 group by A.name ,C.value";
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
