package ${packageName}.${moduleName}.entity;

import com.ztxy.dwps.common.BaseEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * ${functionName}实体类
 * @author ${classAuthor}
 */
@Getter
@Setter
public class ${ClassName} extends BaseEntity {

	<#list tbmodel as column>
	/**
	 * ${column.column_comment}
	 */
	private ${column.column_type_jname} ${column.java_column_name};
	
	</#list>

	
	
}