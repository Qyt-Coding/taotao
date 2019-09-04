package cn.qytTest;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.qyt.EasyUITreeNode;
import com.qyt.pojo.SearchItem;
import com.taotao.mapper.TbItemCatMapper;

import cn.search.service.Mapper.ItemMapper;
import cn.search.service.dao.SearchDao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
////指定创建容器时使用哪个配置文件
@ContextConfiguration("classpath:spring/applicationContext-*.xml")
public class Test123 {

	/*
	 * @Autowired TbItemCatMapper itemCatServiece;
	 * 
	 * 
	 * @Test public void JdbcTest3() { int i =itemCatServiece.selectCount();
	 * System.out.println(i+"---**-*-*-*-*-*"); }
	 * 
	 */
	@Autowired
	ItemMapper itemMapper;

	@Test
	public void JdbcTest4() {
//		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
//				"classpath:spring/applicationContext-*.xml");

		List<SearchItem> list = itemMapper.getListItem();

	  Long itemId = new Long("155965588010696");
		SearchItem sea = itemMapper.getItemId(itemId);
		System.out.println(sea + "sea    -*-*-*-*");
		System.out.println(list + "---**-*-*-*-*-*");
		System.out.println(itemMapper + "---**-*-*-*-*-*");

	}
	
}
