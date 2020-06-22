package ${packageName}.${moduleName}.entity${subModuleName};




public class ${ClassName}  implements Serializable  {
	
	
	
	<#list tbmodel as column>
		bean.set${column.column_name?cap_first}(${column.column_type_jname}); //${column.column_comment}
	</#list>
	
	
}


