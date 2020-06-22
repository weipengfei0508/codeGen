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

-- Create table
create table ${tableName}
(
	<#list tbmodel as column>
  ${column.column_name}      INTEGER not null,
  CATEGORY_ID INTEGER not null,
  TITLE       VARCHAR2(255) not null,
  COLOR       VARCHAR2(50),
  IMAGE       VARCHAR2(255),
  KEYWORDS    VARCHAR2(255),
  DESCRIPTION VARCHAR2(255),
  WEIGHT      INTEGER default '0',
  WEIGHT_DATE DATE,
  HITS        INTEGER default '0',
  POSID       VARCHAR2(10),
  CREATE_BY   INTEGER,
  CREATE_DATE DATE,
  UPDATE_BY   INTEGER,
  UPDATE_DATE DATE,
  REMARKS     VARCHAR2(255),
  DEL_FLAG    CHAR(1) not null,
  CONTENT     VARCHAR2(4000)
  </#list>
)

-- Add comments to the columns 
comment on column CMS_ARTICLE.CATEGORY_ID
  is '栏目类别';
comment on column CMS_ARTICLE.TITLE
  is '标题';
comment on column CMS_ARTICLE.COLOR
  is '暂无用';
comment on column CMS_ARTICLE.IMAGE
  is '暂无用';
comment on column CMS_ARTICLE.KEYWORDS
  is '暂无用';
comment on column CMS_ARTICLE.DESCRIPTION
  is '暂无用';
comment on column CMS_ARTICLE.WEIGHT
  is '暂无用';
comment on column CMS_ARTICLE.WEIGHT_DATE
  is '暂无用';
comment on column CMS_ARTICLE.HITS
  is '暂无用';
comment on column CMS_ARTICLE.POSID
  is '暂无用';
comment on column CMS_ARTICLE.CREATE_BY
  is '创建人';
comment on column CMS_ARTICLE.CREATE_DATE
  is '创建时间';
comment on column CMS_ARTICLE.UPDATE_BY
  is '最后更新人';
comment on column CMS_ARTICLE.UPDATE_DATE
  is '最后更新时间';
comment on column CMS_ARTICLE.REMARKS
  is '备注';
comment on column CMS_ARTICLE.DEL_FLAG
  is '删除标记';
comment on column CMS_ARTICLE.CONTENT
  is '文章内容 ';
-- Create/Recreate primary, unique and foreign key constraints 
alter table CMS_ARTICLE
  add constraint PK_ARTICLE_ID primary key (ID)
  using index 
  tablespace PTMS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );


