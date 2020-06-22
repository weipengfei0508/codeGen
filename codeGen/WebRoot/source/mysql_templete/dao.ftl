package ${packageName}.${moduleName}.dao;


import org.apache.ibatis.annotations.Mapper;
import com.ztxy.dwps.common.CrudDao;
import ${packageName}.${moduleName}.entity.${ClassName};


/**
 * ${functionName}DAO接口
 * @author ${classAuthor}
 */
@Mapper
public interface ${ClassName}Dao extends CrudDao<${ClassName}> {

   
}
