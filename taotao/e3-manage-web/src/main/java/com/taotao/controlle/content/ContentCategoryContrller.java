package com.taotao.controlle.content;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qyt.EasyUITreeNode;
import com.qyt.pojo.TbContent;
import com.qyt.utils.E3Result;
import com.service.content.ContentCategeryService;
import com.service.content.ContentService;
import com.service.taotao.ItemCatServiece;

@Controller
public class ContentCategoryContrller {
	@Autowired
	ContentCategeryService contentCategoryServiceImpl;

	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<EasyUITreeNode> showCat(@RequestParam(name = "id", defaultValue = "0") long parentId) {

		List<EasyUITreeNode> lists = contentCategoryServiceImpl.showTree(parentId);
		for (EasyUITreeNode ea : lists) {
			System.out.println(ea);
		}
		return contentCategoryServiceImpl.showTree(parentId);
	}

	@RequestMapping("/content/category/create")
	@ResponseBody
	public E3Result createCategory(Long parentId, String name) {
		E3Result result = contentCategoryServiceImpl.addContentCategory(parentId, name);
		return result;
	}

	// 为什么这里一定要返回E3Result，我返回空也是可以的
	@RequestMapping("/content/category/update")
	@ResponseBody
	public E3Result updateCategory(long id, String name) {
		E3Result result = contentCategoryServiceImpl.updateContentCategory(id, name);
		return null;
	}

	// 为什么这里一定要返回E3Result，我返回空也是可以的
	@RequestMapping("/content/category/delete/")
	@ResponseBody
	public E3Result deleteCategory(long id) {
		E3Result result = contentCategoryServiceImpl.deleteContentCategory(id);
		return result;
	}

	@Autowired
	private ContentService contentService;

	@RequestMapping("/content/save")
	@ResponseBody
	public E3Result addContent(TbContent content) {
		E3Result result = contentService.addContent(content);
		return result;
	}
}
