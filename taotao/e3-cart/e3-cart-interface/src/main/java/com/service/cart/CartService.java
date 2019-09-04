package com.service.cart;

import java.util.ArrayList;
import java.util.List;

import com.qyt.pojo.TbItem;
import com.qyt.utils.E3Result;

public interface CartService {
	
	E3Result mergeCart(Long userId,List<TbItem> list);
	
	
	List<TbItem> getCartList(Long userId);
	
	E3Result  deleteItemId(Long userId,Long itemId);
}
