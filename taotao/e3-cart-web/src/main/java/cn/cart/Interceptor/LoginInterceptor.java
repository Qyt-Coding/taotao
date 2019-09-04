package cn.cart.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.qyt.pojo.TbUser;
import com.qyt.utils.CookieUtils;
import com.qyt.utils.E3Result;
import com.service.sso.tokenService;

public class LoginInterceptor implements HandlerInterceptor {
	
	@Autowired
	tokenService tkService;
	

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 前处理，执行handler之前执行此方法。
		// 返回true，放行 false：拦截
		// 1.从cookie中取token
		
		System.out.println("interceptor+++++");
		String token=CookieUtils.getCookieValue(request, "token");
		//2.如果没有token，未登录状态，直接放行
		System.out.println("token+++++"+token);
		if(StringUtils.isBlank(token))
		{
			return true;
		}
		//3.取到token，需要调用sso系统的服务，根据token取用户信息
		E3Result re=tkService.getUserByToken(token);
		
		TbUser user=(TbUser) re.getData();
		//4.没有取到用户信息。登录过期，直接放行。
		if(re.getStatus()!=200) {
			//这样的话，就意味着过期了
			return true;
		}
		//5.取到用户信息。登录状态。
		
		//6.把用户信息放到request中。只需要在Controller中判断request中是否包含user信息。放行s
		request.setAttribute("user", user);
		System.out.println("request+++++");
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
