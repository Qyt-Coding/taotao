package cn.sso.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qyt.redis.JedisClient;
import com.qyt.utils.E3Result;
import com.qyt.utils.JsonUtils;
import com.service.sso.tokenService;

@Controller
public class jsonpController {

	@Autowired
	tokenService serviceToken;

	/*
	 * @RequestMapping(value = "/user/token/{token}")
	 * 
	 * @ResponseBody public String getUsernameByToken(@PathVariable String token,
	 * String callback) { //取出token的value E3Result
	 * e3=serviceToken.getUserByToken(token);
	 * if(!"".equals(callback)||callback!=null) { return callback +"("
	 * +JsonUtils.objectToJson(e3) + ");"; } return JsonUtils.objectToJson(e3);
	 * 
	 * }
	 */

	@RequestMapping(value = "/user/token/{token}")
	@ResponseBody
	public Object getUserByToken(@PathVariable String token, String callback) {
		E3Result result = serviceToken.getUserByToken(token);
		// 响应结果之前，判断是否为jsonp请求
		if (StringUtils.isNotBlank(callback)) {
			// 把结果封装成一个js语句响应
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;
		}
		return result;
	}

}
