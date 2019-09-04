package cn.search.web;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.qyt.pojo.SearchResult;

import cn.search.service.SearchItemAndShow;

@Controller
public class SearchWeb {

	@Autowired
	SearchItemAndShow searchItemAndShowImpl;

	@RequestMapping("/search.html")
	public String SearchItem(Model model, String keyword,@RequestParam(defaultValue="1") Integer page) throws Exception {
		// 需要转码
		keyword = new String(keyword.getBytes("iso8859-1"), "utf-8");
		// 调用Service查询商品信息
		Integer i=new Integer(60);
		SearchResult result = searchItemAndShowImpl.showItem(keyword, page, i);
		// 把结果传递给jsp页面
		model.addAttribute("query", keyword);
		model.addAttribute("totalPages", result.getTotalPages());
		model.addAttribute("recourdCount", result.getRecordCount());
		model.addAttribute("page", page);
		model.addAttribute("itemList", result.getItemList());
		// 返回逻辑视图
		return "search";
	}
}
