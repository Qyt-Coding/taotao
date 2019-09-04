package com.service.order;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.order.pojo.TbItemExtend;
import com.qyt.pojo.TbItem;
import com.qyt.pojo.TbUser;
import com.qyt.redis.JedisClient;
import com.qyt.utils.JsonUtils;
@Service
public class OrderServiceImpl implements OrderService {

	@Value("${REDIS_CART_PRE}")
	String REDIS_CART_PRE;
	
	
	@Autowired
	JedisClient jedisClient;
	

	@Override
	public List<TbItem> getCartListByUserId(TbUser user) {
		List<String> jsonList = jedisClient.hvals(REDIS_CART_PRE + ":" + user.getId());
		System.out.println("jsonList++++++++++"+jsonList);
		List<TbItem> cartList = new ArrayList<>();
		for (String string : jsonList) {
			// 创建一个TbItem对象
			TbItem item = JsonUtils.jsonToPojo(string, TbItemExtend.class);
			System.out.println(string + "----------------------string");
			// 添加到列表
			cartList.add(item);
		}
		
		int i =0;
		return cartList;
	}

}
