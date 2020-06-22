/**
 * There are <a href="http://www.whty.com.cn">whty</a> code generation
 */
package ${packageName}.${moduleName}.entity${subModuleName};

import java.util.Date;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.Length;
import com.whty.platform.common.persistence.DataEntity;


/**
 * ${functionName}Entity
 * @author ${classAuthor}
 * @version ${classVersion}
 */
@Entity
@Table(name = "${tableName}")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ${ClassName} extends DataEntity {
	
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

	public ${ClassName}(Long id){
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


