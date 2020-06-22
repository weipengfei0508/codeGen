<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${packageName}.${moduleName}.dao.${ClassName}Dao">
	
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
	
	<select id="getCount" resultType="${packageName}.${moduleName}.entity.${ClassName}">
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
			<if test="${column.java_column_name} != null">
			${column.column_name},
			</if>
		</#list>
		</trim>	
		<trim prefix="values (" suffix=")" suffixOverrides=",">
		<#list tbmodel as column>
			<if test="${column.java_column_name} != null">
				${"#"}{${column.java_column_name}},
			</if>	
		</#list>
		</trim>	
	</insert>
	
	
	<update id="update">
		UPDATE ${tableName}  
		<trim prefix="set" suffixOverrides=","> 	
			<#list tbmodel as column>
				<if test="${column.java_column_name} != null and ${column.java_column_name} != ''">
					${column.column_name} = ${"#"}{${column.java_column_name}},
				</if>					
			</#list>
		</trim>	
		WHERE id = ${"#"}{id}
	</update>
	
	
	<delete id="delete">
		DELETE FROM ${tableName}
		WHERE id = ${"#"}{id}
	</delete>
</mapper>