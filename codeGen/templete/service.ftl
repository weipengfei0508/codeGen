package ${packageName}.${moduleName}.service;

import java.util.List;
import ${packageName}.common.PageBean;
import ${packageName}.${moduleName}.entity.${ClassName};

/**
 * ${functionName}Service接口
 * @author ${classAuthor}
 */
public interface ${ClassName}Service  {

	/**
	 * 根据id获取信息
	 * @param entity
	 * @return
	 */
	 ${ClassName} getOne(${ClassName} entity);
   
   
   /**
	  * 得到记录条数
	  * @param entity
	  * @return
	  */
	 int getCount(${ClassName} entity);
	 
    /**
     * 分页查询
     * @param entity
     * @param pageBean
     * @return
     */
    PageBean findPageList(${ClassName} entity);
    
    

    /**
     * 查询记录
     * @param entity
     * @return
     */
    List<${ClassName}> findAllList(${ClassName} entity); 
    
       /**
     * 查询记录
     * @param entity
     * @return
     */
    List<${ClassName}> findList(${ClassName} entity); 
   
     /**
     * 插入数据
     *
     * @param entity
     * @return
     */
    int insert(${ClassName}  entity);

    /**
     * 更新数据
     *
     * @param entity
     * @return
     */
    int update(${ClassName}  entity);

  

    /**
     * 删除数据（一般为逻辑删除，更新del_flag字段为1）
     *
     * @param entity
     * @return
     */
    int delete(${ClassName} entity);
}