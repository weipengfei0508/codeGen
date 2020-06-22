<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${packageName}.${moduleName}.mapper.${ClassName}Mapper">

	
 <sql id="${className}Columns">
<#assign columnField>
<#list tbmodel as column>
A.${column.column_name} AS "${column.java_column_name}",
</#list>
</#assign>
${columnField?substring(0, columnField?last_index_of(","))}
 </sql>
	
<!-- 根据id查询信息 -->
<select id="get" resultType="${packageName}.${moduleName}.entity.${ClassName}">
SELECT
   <include refid="${className}Columns"/>  
	FROM
	${tableName} AS A 
	where  A.id=${"#"}{id}
</select>

<!-- 根据条件查出记录条数 -->
<select id="getCount" resultType="java.lang.Integer">
SELECT
   count(*)  
	FROM
	${tableName} AS A 
	where  A.id=${"#"}{id}
</select>
	
<!-- 查询信息 -->
<select id="findList" resultType="${packageName}.${moduleName}.entity.${ClassName}">
SELECT
   <include refid="${className}Columns"/>  
	FROM
	${tableName} AS A  
</select>
	
<!-- 插入 -->
<insert id="insert">
	INSERT INTO ${tableName}
	<trim prefix="(" suffix=")" suffixOverrides=",">	
	<#list tbmodel as column>
		<#if column.column_name!="id">
			<if test="${column.java_column_name} != null">
			${column.column_name},
			</if>
		</#if>		
	</#list>
	</trim>	
	<trim prefix="values (" suffix=")" suffixOverrides=",">
	<#list tbmodel as column>
		<#if column.column_name!="id">
			<if test="${column.java_column_name} != null">
				${"#"}{${column.java_column_name}},
			</if>
		</#if>	
	</#list>
	</trim>	
</insert>
	
<!-- 更新 -->	
<update id="update">
	UPDATE ${tableName}  
	<trim prefix="set" suffixOverrides=","> 	
		<#list tbmodel as column>
			<#if column.column_name!="id" && column.column_name!="create_date">
			<if test="${column.java_column_name} != null">
				${column.column_name} = ${"#"}{${column.java_column_name}},
			</if>
			</#if>				
		</#list>
	</trim>	
	WHERE id = ${"#"}{id}
</update>
	
<!-- 删除 -->		
<delete id="delete">
	DELETE FROM ${tableName}
	WHERE id = ${"#"}{id}
</delete>
</mapper>