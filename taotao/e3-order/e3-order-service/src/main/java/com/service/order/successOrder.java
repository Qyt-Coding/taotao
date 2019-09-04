package com.service.order;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.order.pojo.OrderInfo;
import com.qyt.pojo.TbOrder;
import com.qyt.pojo.TbOrderItem;
import com.qyt.pojo.TbOrderShipping;
import com.qyt.redis.JedisClient;
import com.qyt.utils.E3Result;
import com.taotao.mapper.TbOrderItemMapper;
import com.taotao.mapper.TbOrderMapper;
import com.taotao.mapper.TbOrderShippingMapper;
@Service
public class successOrder implements InsertOrder {

	@Autowired
	JedisClient jedisClient;

	@Autowired
	TbOrderMapper orderMapper;
	
	@Autowired
	TbOrderShippingMapper orderShippingMapper;
	

	@Autowired
	TbOrderItemMapper orderItemMapper;

	@Value("${REDIS_INCR}")
	String REDIS_INCR;

	@Value("${REDIS_INCR_START}")
	String REDIS_INCR_START;

	@Value("${REDIS_INCR2}")
	String REDIS_INCR2;

	@Override
	public E3Result InsertAndSelectOrder(OrderInfo info) {

		if (!jedisClient.exists(REDIS_INCR)) {
			jedisClient.set(REDIS_INCR, REDIS_INCR_START);
		}
		Long orderId = jedisClient.incr(REDIS_INCR);
		info.setStatus(1);
		info.setCreateTime(new Date());
		info.setUpdateTime(new Date());

		info.setOrderId(orderId.toString());

		orderMapper.insert(info);
		List<TbOrderItem> orderItems = info.getOrderItems();
		for (TbOrderItem tbOrderItem : orderItems) {
			// 生成明细id
			String odId = jedisClient.incr(REDIS_INCR2).toString();
			// 补全pojo的属性
			tbOrderItem.setId(odId);
			tbOrderItem.setOrderId(orderId.toString());
			// 向明细表插入数据
			orderItemMapper.insert(tbOrderItem);
		}

		TbOrderShipping orderShipping = info.getOrderShipping();
		orderShipping.setOrderId(orderId.toString());
		orderShipping.setCreated(new Date());
		orderShipping.setUpdated(new Date());
		orderShippingMapper.insert(orderShipping);
		
		
		//通过orderId去查找Order对象
		TbOrder order=  orderMapper.selectByPrimaryKey(orderId.toString());
		return E3Result.ok(order);
	}

}
