package com.service.order;

import java.util.List;

import com.qyt.pojo.TbItem;
import com.qyt.pojo.TbUser;

public interface OrderService {

	public List<TbItem>  getCartListByUserId(TbUser user);
}

