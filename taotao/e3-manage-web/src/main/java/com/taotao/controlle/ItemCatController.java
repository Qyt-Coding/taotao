package com.taotao.controlle;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qyt.EasyUITreeNode;
import com.service.taotao.ItemCatServiece;

@Controller
public class ItemCatController {

	@Autowired 
	ItemCatServiece itemCatServiece;
	
	
	
	@RequestMapping("/item/cat/list")
	@ResponseBody
	public  List<EasyUITreeNode> showCat(@RequestParam(name="id",defaultValue="0")long parentId){
		return itemCatServiece.EasyTree(parentId);
	}
	
}
