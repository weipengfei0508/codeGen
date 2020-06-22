package ${packageName}.${moduleName}.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import ${packageName}.common.PageBean;
import com.alibaba.fastjson.JSONObject;
import ${packageName}.${moduleName}.entity.${ClassName};
import ${packageName}.${moduleName}.service.${ClassName}Service;

@Controller
public class ${ClassName}Controller {

	
	private static Logger logger = LoggerFactory.getLogger(${ClassName}Controller.class);
	
	@Autowired 
	private ${ClassName}Service ${className}Service;
	
	 
	
	/**
	 * 分页信息
	 * @return
	 */
	@RequestMapping(value = "/api/${moduleName}/${className}/pageList",method = RequestMethod.POST)
	@ResponseBody
	public PageBean findPageList(@RequestBody ${ClassName} entity,HttpServletRequest request, HttpServletResponse response, Model model){
	
		return ${className}Service.findPageList(entity);
	}
	
	/**
	 * 添加
	 * 
	 * @return
	 */
	@RequestMapping(value = "/api/${moduleName}/${className}/save", method = RequestMethod.POST)
	@ResponseBody
	public String save(@RequestBody ${ClassName} entity, HttpServletRequest request, HttpServletResponse response,Model model) {
		JSONObject json = new JSONObject();
		${className}Service.insert(entity);
		json.put("retcode", 1);
		json.put("info", "操作成功!");
		return json.toJSONString();
	}
	
	
	/**
	 * 修改
	 * 
	 * @return
	 */
	@RequestMapping(value = "/api/${moduleName}/${className}/update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@RequestBody ${ClassName} entity, HttpServletRequest request, HttpServletResponse response,Model model) {
		JSONObject json = new JSONObject();
		${className}Service.update(entity);
		json.put("retcode", 1);
		json.put("info", "操作成功!");
		return json.toJSONString();
	}
	
	/**
	 * 删除
	 * 
	 * @return
	 */
	@RequestMapping(value = "/api/${moduleName}/${className}/delete", method = RequestMethod.POST)
	@ResponseBody
	public String delete(@RequestBody ${ClassName} entity, HttpServletRequest request, HttpServletResponse response,Model model) {
		JSONObject json = new JSONObject();
		${className}Service.delete(entity);
		json.put("retcode", 1);
		json.put("info", "操作成功!");
		return json.toJSONString();
	}
    
}  
    

