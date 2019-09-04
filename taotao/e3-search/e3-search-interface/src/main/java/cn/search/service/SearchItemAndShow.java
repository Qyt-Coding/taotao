package cn.search.service;

import com.qyt.pojo.SearchResult;

public interface SearchItemAndShow {

	public SearchResult showItem(String keyword, int page, int rows)  throws Exception;
}
