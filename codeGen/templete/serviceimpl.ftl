package ${packageName}.${moduleName}.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import ${packageName}.common.PageBean;
import ${packageName}.common.baseServiceImpl;
import ${packageName}.${moduleName}.dao.${ClassName}Dao;
import ${packageName}.${moduleName}.entity.${ClassName};
import ${packageName}.${moduleName}.service.${ClassName}Service;

@Service
public class ${ClassName}ServiceImpl extends baseServiceImpl<${ClassName}> implements ${ClassName}Service {
    @Resource
    private ${ClassName}Dao ${className}Dao;
    
    /**
     * 分页查询用户信息
     */
	@Override
	public PageBean findPageList(${ClassName} entity) {
   	   Page<${ClassName}> page =PageHelper.startPage(entity.getCurent(),entity.getLimit());
   	   ${className}Dao.findList(entity);
   	    return initPageBean(page);
	}
	
	@Override
	public List<${ClassName}> findList(${ClassName} entity) {
		return ${className}Dao.findList(entity);
	}
	
	
	@Override
	public List<${ClassName}> findAllList(${ClassName} entity) {
		return ${className}Dao.findAllList(entity);
	}
	
	
	@Override
	public int insert(${ClassName} entity) {
		return ${className}Dao.insert(entity);
	}

	@Override
	public int update(${ClassName} entity) {
		return ${className}Dao.update(entity);
	}

	@Override
	public int delete(${ClassName} entity) {
		return ${className}Dao.delete(entity);
	}
	
	@Override
	public ${ClassName} getOne(${ClassName} entity) {
		return ${className}Dao.get(entity);
	}
	
	@Override
	public int getCount(${ClassName} entity) {
		return ${className}Dao.getCount(entity);
	}
	

}