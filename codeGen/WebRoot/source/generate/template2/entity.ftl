package ${packageName}.${moduleName}.entity${subModuleName};

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.validator.constraints.Length;
import java.util.Date;


/**
 * ${functionName}Entity
 * @author ${classAuthor}
 * @version ${classVersion}
 */
@Entity
@Table(name = "${tableName}")
public class ${ClassName}  implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	<#list tbmodel as column>
	/**
	 * ${column.column_comment}
	 */
	private ${column.column_type_jname} ${column.column_name};
	
	</#list>
	
	
	public ${ClassName}() {
		super();
	}

	public ${ClassName}(String id){
		this();
		this.id = id;
	}
	
	<#list tbmodel as column>
	<#if column.is_nullable=="no"&&column.column_key!="pri"&&column.column_type_jname=="Integer">
	@NotNull
	</#if>
	<#if column.extra=="auto_increment"&&column.column_key=="pri">
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	</#if>
	<#if column.column_type_jname=="String"&&column.is_nullable=="no">
	@Length(min=1, max=${column.column_type_num1})
	</#if>
	<#if column.column_type_jname=="String"&&column.is_nullable!="no">
	@Length(min=0, max=${column.column_type_num1})
	</#if>
	public ${column.column_type_jname}  get${column.column_name?cap_first}(){
		return ${column.column_name?uncap_first};
	}

	public void set${column.column_name?cap_first}(${column.column_type_jname} ${column.column_name?uncap_first}){
		this.${column.column_name?uncap_first} = ${column.column_name?uncap_first};
	}
	
	</#list>
	
	
}


