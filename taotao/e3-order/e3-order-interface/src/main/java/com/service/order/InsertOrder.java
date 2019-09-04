package com.service.order;

import com.order.pojo.OrderInfo;
import com.qyt.pojo.TbOrder;
import com.qyt.utils.E3Result;

public interface InsertOrder {
	
	public E3Result  InsertAndSelectOrder(OrderInfo info);
}
