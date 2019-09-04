package cn.item.message;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import com.qyt.pojo.TbItem;
import com.qyt.pojo.TbItemDesc;
import com.service.taotao.ItemService;

import cn.item.pojo.Item;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreeMarkeMessageListener implements MessageListener {

	@Autowired
	private ItemService itemService;

	@Autowired
	FreeMarkerConfig freemarkerConfig;

	@Override
	public void onMessage(Message message) {
		try {
			TextMessage textMessage = (TextMessage) message;
			String text;
			text = textMessage.getText();
			System.out.println(text + "--------");
			Long itemId = new Long(text);

			// 等待事务提交
			Thread.sleep(1000);

			TbItem tbItem = itemService.getItemById(itemId);
			Item item = new Item(tbItem);
			// 取商品描述信息
			TbItemDesc itemDesc = itemService.getItemDescById(itemId);
			// 把信息传递给页面
			// model.addAttribute("item", item);
			// model.addAttribute("itemDesc", itemDesc);
			Configuration configuration = freemarkerConfig.getConfiguration();
			// 3、使用Configuration对象获得Template对象。
			Template template = configuration.getTemplate("item.ftl");

			Map dataModel = new HashMap<>();
			dataModel.put("item", item);
			dataModel.put("itemDesc", itemDesc);
			// 5、创建输出文件的Writer对象。
			Writer out = new FileWriter(new File("F:\\freemarke\\"+item.getId()+".html"));
			// 6、调用模板对象的process方法，生成文件。
			template.process(dataModel, out);
			// 7、关闭流。
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
