package ${packageName}.${moduleName}.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ${packageName}.base.CrudService;
import ${packageName}.${moduleName}.entity.${ClassName};
import ${packageName}.${moduleName}.mapper.${ClassName}Mapper;


/**
 * ${functionName}Service 
 */
@Service("${ClassName}Service")
public class ${ClassName}Service extends CrudService<${ClassName}Mapper, ${ClassName}>{
	
	@Autowired
	private ${ClassName}Mapper mapper;
	
}
