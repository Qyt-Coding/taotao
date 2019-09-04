package com.taotao.controlle;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qyt.EasyUITreeNode;
import com.qyt.pojo.QueryVo;
import com.qyt.pojo.TbItem;
import com.qyt.utils.E3Result;
import com.service.content.ContentCategeryService;
import com.service.taotao.ItemService;

@Controller
public class ItemController {
	@Autowired
	private ItemService itemService;

	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TbItem getItemById(@PathVariable Long itemId) {
		TbItem tbItem = itemService.getItemById(itemId);
		return tbItem;
	}

	@RequestMapping("/item/qw")
	public String getItemById1() {
		Long itemId = (long) 844022;

		TbItem tbItem = itemService.getItemById(itemId);
		return "index";
	}

	@RequestMapping("/item/first")
	public String show() {

		return "index";
	}

	@RequestMapping(value = "/item/save", method = RequestMethod.POST)
	@ResponseBody
	public E3Result insertItem(TbItem tbItem, String desc) {
		return itemService.insertItem(tbItem, desc);
	}

	@RequestMapping(value = "/rest/item/delete")
	@ResponseBody
	public E3Result deleteItem(QueryVo vo) {
		return itemService.deleteItem(vo.getIds());
	}


}
