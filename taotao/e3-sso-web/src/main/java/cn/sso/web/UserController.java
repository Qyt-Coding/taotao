package cn.sso.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qyt.pojo.TbUser;
import com.qyt.utils.CookieUtils;
import com.qyt.utils.E3Result;
import com.service.sso.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/user/check/{param}/{type}")
	@ResponseBody
	public E3Result checkData(@PathVariable String param, @PathVariable Integer type) {
		E3Result e3Result = userService.checkData(param, type);
		return e3Result;
	}
	
	
	@RequestMapping(value = "/register")
	public String register() {
		return "register";
	}
	
	@RequestMapping(value = "/user/register" ,method=RequestMethod.POST)
	@ResponseBody
	public E3Result registerSuccess(TbUser user) {
		E3Result e3Result =userService.registerUser( user);
		return e3Result;
	}
	
	
	
	@RequestMapping(value = "/page/login")
	public String login(String redirect,Model model) {
		if(StringUtils.isNotEmpty(redirect)) {
			model.addAttribute("redirect", redirect);
		}else {
			redirect="";
		}
		
		return "login";
	}
	

	@RequestMapping(value = "/")
	@ResponseBody
	public String ttt() {
		return "ok";
	}
	//注意这里要@ResponseBody
	@RequestMapping(value = "/user/login",method=RequestMethod.POST)
	@ResponseBody
	public E3Result loginUser(String username,String password,
			HttpServletRequest request, HttpServletResponse response) {
		E3Result e3Result=userService.loginUser(username,password);
		System.out.println(username+"-**username**--*-*");
		System.out.println(password+"-***password**--*-*");
		if(e3Result.getStatus()==200) {
			String token = e3Result.getData().toString();
			//如果登录成功需要把token写入cookie
			CookieUtils.setCookie(request, response, "token", token);
		}
		
		System.out.println(e3Result.getStatus()+"**-*-*-*-*-");
		return e3Result;
		
	}
	
}
