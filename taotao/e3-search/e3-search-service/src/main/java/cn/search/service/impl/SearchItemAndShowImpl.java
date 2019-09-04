package cn.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.qyt.pojo.SearchResult;

import cn.search.service.SearchItemAndShow;
import cn.search.service.dao.SearchDao;
@Service
public class SearchItemAndShowImpl implements SearchItemAndShow {

	@Autowired
	private SearchDao searchDao;

	/*@Value("${DEFAULT_FIELD}")*/
	private String DEFAULT_FIELD="item_keywords";

	@Override
	public SearchResult showItem(String keyword, int page, int rows) throws Exception {
		// 创建一个SolrQuery对象
		SolrQuery query = new SolrQuery();
		// 设置查询条件
		query.setQuery(keyword);
		// 设置分页条件
		query.setStart((page - 1) * rows);
		// 设置rows
		query.setRows(rows);
		// 设置默认搜索域
		query.set("df", DEFAULT_FIELD);
		// 设置高亮显示
		query.setHighlight(true);
		query.addHighlightField("item_title");
		query.setHighlightSimplePre("<em style=\"color:red\">");
		query.setHighlightSimplePost("</em>");
		// 执行查询
		SearchResult searchResult = searchDao.showItem(query);
		// 计算总页数
		/*long i=searchResult.getRecourdCount();*/
		int recourdCount = (int) searchResult.getRecordCount();
		int pages = recourdCount / rows;
		if (recourdCount % rows > 0)
			pages++;
		// 设置到返回结果
		searchResult.setTotalPages(pages);
		return searchResult;
	}
}
