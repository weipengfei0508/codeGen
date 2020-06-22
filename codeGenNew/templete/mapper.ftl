package ${packageName}.${moduleName}.mapper;

import org.apache.ibatis.annotations.Mapper;
import ${packageName}.core.CrudDao;
import ${packageName}.${moduleName}.entity.${ClassName};

/**
 * ${functionName}DAO接口
 */
@Mapper
public interface ${ClassName}Mapper  extends CrudDao<${ClassName}>{
	
	
}
