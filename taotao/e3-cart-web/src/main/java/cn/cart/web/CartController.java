package cn.cart.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.support.Parameter;
import com.qyt.pojo.TbItem;
import com.qyt.pojo.TbUser;
import com.qyt.utils.CookieUtils;
import com.qyt.utils.E3Result;
import com.qyt.utils.JsonUtils;
import com.service.cart.CartService;
import com.service.taotao.ItemService;

@Controller
public class CartController {

	@Autowired
	ItemService itemService;
	//这个是添加商品的时候做的地方
	@RequestMapping("/cart/add/{itemId}")
	public String addCart(@PathVariable Long itemId, Integer num, HttpServletRequest request,
			HttpServletResponse response) {
		List<TbItem> cartList = getCartList(request);
		// 1、从cookie中查询商品列表。
		// 2、判断商品在商品列表中是否存在。
		
		
		
		boolean flag = false;
		for (TbItem tbItem : cartList) {
			// 对象比较的是地址，应该是值的比较
			if (tbItem.getId() == itemId.longValue()) {
				// 3、如果存在，商品数量相加。
				tbItem.setNum(tbItem.getNum() + num);
				flag = true;
				break;
			}
		}
		if (!flag) {
			TbItem tbItem = itemService.getItemById(itemId);
			String image = tbItem.getImage();
			if (StringUtils.isNoneBlank(image)) {
				String[] images = image.split(",");
				tbItem.setImage(images[0]);
			}
			// 设置购买商品数量
			tbItem.setNum(num);
			// 5、把商品添加到购车列表。
			cartList.add(tbItem);
		}
		TbUser user=(TbUser) request.getAttribute("user");
		if(user!=null) {
			cartService.mergeCart(user.getId(), cartList);
			System.out.println("+++++"+user);
			System.out.println(cartList.toString()+"---------------cartList");
			return "cartSuccess";
		}

		CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList), 1800, true);
		return "cartSuccess";
	}

	// 这里是获得cookie中的value值
	private List<TbItem> getCartList(HttpServletRequest request) {
		// 取购物车列表
		String json = CookieUtils.getCookieValue(request, "cart", true);
		// 判断json是否为null
		if (StringUtils.isNotBlank(json)) {
			// 把json转换成商品列表返回
			List<TbItem> list = JsonUtils.jsonToList(json, TbItem.class);
			return list;
		}
		return new ArrayList<>();
	}

	/**
	 * 展示购物车列表
	 * <p>
	 * Title: showCatList
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param request
	 * @return
	 */
	
	@Autowired 
	CartService cartService;
	
	//展示购物车
	@RequestMapping("/cart/cart")
	public String showCatList(HttpServletRequest request, HttpServletResponse response) {
		// 从cookie中取购物车列表
		List<TbItem> cartList = getCartList(request);
		// 判断用户是否为登录状态
		TbUser user = (TbUser) request.getAttribute("user"); // 如果是登录状态
		if (user != null) {
			//通过了说明登录了
			// 从cookie中取购物车列表
			// 如果不为空，把cookie中的购物车商品和服务端的购物车商品合并。
			cartService.mergeCart(user.getId(), cartList);
			//cartService.mergeCart(user.getId(), cartList);
			// 把cookie中的购物车删除
			CookieUtils.deleteCookie(request, response, "cart");
			//CookieUtils.deleteCookie(request, response, "cart"); 
			// 从服务端取购物车列表
			cartList =cartService.getCartList(user.getId());

		} // 把列表传递给页面
		request.setAttribute("cartList", cartList);
		// 返回逻辑视图
		return "cart";
	}

	@RequestMapping("/cart/update/num/{itemId}/{num}")
	@ResponseBody
	public E3Result updateIncrement(@PathVariable Long itemId, @PathVariable Integer num, HttpServletRequest request,
			HttpServletResponse response) {
		// 获得一个购物车
		List<TbItem> cartList = getCartList(request);

		for (TbItem item : cartList) {
			if (item.getId() == itemId.longValue()) {
				item.setNum(num);
				break;
			}
		}
		CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList), 1800, true);
		return E3Result.ok();
	}

	@RequestMapping("/cart/delete/{id}" + ".html")
	public String deleteProduct(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
		TbUser user=(TbUser) request.getAttribute("user");
		if(user!=null) {
			cartService.deleteItemId(user.getId(), id);
			return "redirect:/cart/cart.html";
		}
		// 获得购物车
		List<TbItem> cartList = getCartList(request);
		//
		for (TbItem item1 : cartList) {
			if (item1.getId() == id.longValue()) {
				cartList.remove(item1);
				break;
			}
		}
		CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList), 1800, true);
		return "redirect:/cart/cart.html";
	}
}
