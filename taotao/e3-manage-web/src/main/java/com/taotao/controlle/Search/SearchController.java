package com.taotao.controlle.Search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qyt.utils.E3Result;

import cn.search.service.ItemSearch;

@Controller
public class SearchController {
	
	@Autowired
	ItemSearch  itemSearch;

	@RequestMapping(value="/index/item/import")
	@ResponseBody
	public E3Result importDate() {
		return itemSearch.importDate();
	}
}
