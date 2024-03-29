package cn.item.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qyt.pojo.TbItem;
import com.qyt.pojo.TbItemDesc;
import com.service.taotao.ItemService;

import org.springframework.stereotype.Controller;
import cn.item.pojo.Item;
@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;
	@RequestMapping("/item/{itemId}.html")
	public String showItemInfo(@PathVariable Long itemId, Model model) {
		// 调用服务取商品基本信息
		TbItem tbItem = itemService.getItemById(itemId);
		Item item = new Item(tbItem);
		// 取商品描述信息
		TbItemDesc itemDesc = itemService.getItemDescById(itemId);
		// 把信息传递给页面
		model.addAttribute("item", item);
		model.addAttribute("itemDesc", itemDesc);
		// 返回逻辑视图
		return "item";
	}

}
