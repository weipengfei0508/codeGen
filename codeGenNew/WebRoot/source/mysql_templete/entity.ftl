package ${packageName}.${moduleName}.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ztxy.dwps.common.BaseEntity;

/**
 * ${functionName}实体类
 * @author ${classAuthor}
 */
public class ${ClassName} extends BaseEntity {

	<#list tbmodel as column>
	/**
	 * ${column.column_comment}
	 */
	private ${column.column_type_jname} ${column.java_column_name};
	
	</#list>

	
	<#list tbmodel as column>
	
	public ${column.column_type_jname}  get${column.java_column_name?cap_first}(){
		return ${column.java_column_name?uncap_first};
	}

	public void set${column.java_column_name?cap_first}(${column.column_type_jname} ${column.java_column_name?uncap_first}){
		this.${column.java_column_name?uncap_first} = ${column.java_column_name?uncap_first};
	}
	
	</#list>
	

	
}