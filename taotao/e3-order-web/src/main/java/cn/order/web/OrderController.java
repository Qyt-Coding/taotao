package cn.order.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.order.pojo.OrderInfo;
import com.qyt.pojo.TbItem;
import com.qyt.pojo.TbOrder;
import com.qyt.pojo.TbUser;
import com.qyt.utils.E3Result;
import com.service.order.InsertOrder;
import com.service.order.OrderService;

@Controller
public class OrderController {
	@Autowired
	public OrderService orderService;

	@RequestMapping("/order/order-cart.html")
	public String showOrder(HttpServletRequest request) {
		TbUser user = (TbUser) request.getAttribute("user");
		List<TbItem> cartList = orderService.getCartListByUserId(user);
		System.out.println("+++++" + cartList);
		request.setAttribute("cartList", cartList);
		return "order-cart";
	}
	
	
	@Autowired
	InsertOrder  successOrder;
	
	@RequestMapping("/order/create.html")
	public  String   zhiFu(OrderInfo info,Model model) {
		//插入Order
		E3Result re= successOrder.InsertAndSelectOrder(info);
		//返回一个order对象
		TbOrder order=(TbOrder) re.getData();
		model.addAttribute("order", order);
		return "success";
	}
	
	
}
