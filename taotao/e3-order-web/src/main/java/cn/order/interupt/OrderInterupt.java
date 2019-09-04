package cn.order.interupt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.qyt.pojo.TbItem;
import com.qyt.pojo.TbUser;
import com.qyt.utils.CookieUtils;
import com.qyt.utils.E3Result;
import com.qyt.utils.JsonUtils;
import com.service.cart.CartService;
import com.service.sso.tokenService;

public class OrderInterupt implements HandlerInterceptor{
	
	@Autowired
	tokenService tKService;
	
	@Value("${SSO_URL}")
	String  SSO_URL;
	
	@Autowired
	CartService cartServiceimpl;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// true：放行  false：拦截
		//获得token
		String token=CookieUtils.getCookieValue(request, "token");
		
		if(StringUtils.isBlank(token)) {
			//转发
			//如果token不存在，未登录状态，跳转到sso系统的登录页面。用户登录成功后，跳转到当前请求的url
			response.sendRedirect(SSO_URL + "/page/login?redirect=" + request.getRequestURL());
			return false;
		}
		
		E3Result re=  tKService.getUserByToken(token);
		
		if(re.getStatus()!=200) {
			//转发
			//如果token不存在，未登录状态，跳转到sso系统的登录页面。用户登录成功后，跳转到当前请求的url
			response.sendRedirect(SSO_URL + "/page/login?redirect=" + request.getRequestURL());
			return false;
		}
		TbUser user =(TbUser) re.getData();
		request.setAttribute("user", user);
		//这里一定要转码。你点开网站你会发现cookie中的cart是一串看不懂的字符(因为之前存的时候就是编码后的),所以要解码
		String jsonCardList=CookieUtils.getCookieValue(request, "cart",true);
		if(StringUtils.isNotEmpty(jsonCardList)) {
			cartServiceimpl.mergeCart(user.getId(),JsonUtils.jsonToList(jsonCardList, TbItem.class) );
		}
		//通过request.getAttribute（"user"）;
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
