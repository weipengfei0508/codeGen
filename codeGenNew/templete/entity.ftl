package ${packageName}.${moduleName}.entity;

import java.util.Date;
import ${packageName}.core.DataEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * ${functionName}实体类
 * @author ${classAuthor}
 */
@Getter
@Setter
public class ${ClassName} extends DataEntity  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	<#list tbmodel as column>
	<#if column.column_name!="create_date" && column.column_name!="update_date" && column.column_name!="id">
	/**
	 * ${column.column_comment}
	 */
	private ${column.column_type_jname} ${column.java_column_name};
	</#if>
	</#list>

	
	
}