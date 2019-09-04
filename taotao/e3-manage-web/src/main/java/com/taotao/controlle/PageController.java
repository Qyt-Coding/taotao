package com.taotao.controlle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qyt.EasyUIDataGridResult;
import com.service.taotao.ItemService;

@Controller
public class PageController {
	@Autowired
	ItemService itemService;

	@RequestMapping(value = "/")
	public String showIndex() {
		return "index";
	}

	@RequestMapping(value = "/{page}")
	public String showPage(@PathVariable String page) {
		return page;
	}

	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
		EasyUIDataGridResult result = itemService.getItemList(page, rows);
		return result;
	}
}
