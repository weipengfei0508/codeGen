/**
 * There are <a href="http://www.whty.com.cn">whty</a> code generation
 */
package ${packageName}.${moduleName}.dao${subModuleName};

import org.springframework.stereotype.Repository;

import com.whty.platform.common.persistence.BaseDao;
import com.whty.platform.common.persistence.Parameter;
import ${packageName}.${moduleName}.entity${subModuleName}.${ClassName};

/**
 * ${functionName}DAO接口
 * @author ${classAuthor}
 * @version ${classVersion}
 */
@Repository
public class ${ClassName}Dao extends BaseDao<${ClassName}> {
	
}
