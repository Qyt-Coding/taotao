package com.service.cart.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.qyt.pojo.TbItem;
import com.qyt.pojo.TbUser;
import com.qyt.redis.JedisClient;
import com.qyt.utils.E3Result;
import com.qyt.utils.JsonUtils;
import com.service.cart.CartService;
import com.taotao.mapper.TbItemMapper;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	JedisClient jedisClient;

	@Autowired
	TbItemMapper itemMapper;

	@Value("${REDIS_CART_PRE}")
	String REDIS_CART_PRE;

	@Override
	public E3Result mergeCart(Long userId, List<TbItem> list) {

		for (TbItem item : list) {
			addItem(item, userId);
		}
		return E3Result.ok();
	}

	private E3Result addItem(TbItem item, Long userId) {
		boolean hexists = jedisClient.hexists(REDIS_CART_PRE + ":" + userId, item.getId() + "");
		// 判断是否存在
		if (hexists) {
			String json = jedisClient.hget(REDIS_CART_PRE + ":" + userId, item.getId() + "");
			TbItem itemRedis = JsonUtils.jsonToPojo(json, TbItem.class);
			// 把原来redis中的商品数量和原来的加到一起
			itemRedis.setNum(itemRedis.getNum() + item.getNum());

			jedisClient.hset(REDIS_CART_PRE + ":" + userId, itemRedis.getId() + "", JsonUtils.objectToJson(itemRedis));
			return E3Result.ok();
		}
		// item111这个是不存在的时候的item
		TbItem item111 = itemMapper.selectByPrimaryKey(item.getId());

		item111.setNum(item.getNum());
		String image = item.getImage();
		if (StringUtils.isNotBlank(image)) {
			item111.setImage(image.split(",")[0]);
		}
		
		jedisClient.hset(REDIS_CART_PRE + ":" + userId, item111.getId() + "", JsonUtils.objectToJson(item111));

		return E3Result.ok();

	}

	@Override
	public List<TbItem> getCartList(Long userId) {

		// 根据用户id查询购车列表
		List<String> jsonList = jedisClient.hvals(REDIS_CART_PRE + ":" + userId);
		List<TbItem> itemList = new ArrayList<>();
		for (String string : jsonList) {
			// 创建一个TbItem对象
			TbItem item = JsonUtils.jsonToPojo(string, TbItem.class);
			System.out.println(string + "----------------------string");
			
			item.setImage("");
			// 添加到列表
			itemList.add(item);
		}
		return itemList;
	}

	// 删除商品
	@Override
	public E3Result deleteItemId(Long userId, Long itemId) {
		jedisClient.hdel(REDIS_CART_PRE+":"+userId, itemId+"");
		return E3Result.ok();
	}

}
