package ${packageName}.${moduleName}.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import ${packageName}.${moduleName}.entity.${ClassName};
import com.ztxy.dwps.utils.ResponseUtil;
import ${packageName}.${moduleName}.service.${ClassName}Service;

/**
 * ${functionName} Controller层
 * @return
 */
@Controller
public class ${ClassName}Controller {


	
	@Autowired 
	private ${ClassName}Service ${className}Service;
	
	 /**
	 * 详情信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/api/${moduleName}/${className}/get", method = RequestMethod.POST)
	@ResponseBody
	public Object get(@RequestBody ${ClassName} entity, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		return ResponseUtil.ok(${className}Service.get(entity));
	}
	
	 
	 /**
	 * 分页信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/api/${moduleName}/${className}/pageList", method = RequestMethod.POST)
	@ResponseBody
	public Object findPageList(@RequestBody ${ClassName} entity, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		return ResponseUtil.ok(${className}Service.findPage(entity));
	}
	
	
	
	/**
	 * 添加
	 * 
	 * @return
	 */
	@RequestMapping(value = "/api/${moduleName}/${className}/save", method = RequestMethod.POST)
	@ResponseBody
	public Object save(@RequestBody ${ClassName} entity, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		${className}Service.insert(entity);
		return ResponseUtil.ok();
	}

	/**
	 * 修改
	 * 
	 * @return
	 */
	@RequestMapping(value = "/api/${moduleName}/${className}/update", method = RequestMethod.POST)
	@ResponseBody
	public Object update(@RequestBody ${ClassName} entity, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		${className}Service.update(entity);
		return ResponseUtil.ok();
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	@RequestMapping(value = "/api/${moduleName}/${className}/delete", method = RequestMethod.POST)
	@ResponseBody
	public Object delete(@RequestBody ${ClassName} entity, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		${className}Service.delete(entity);
		return ResponseUtil.ok();
	}
    
}  
    

