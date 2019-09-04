package cn.search.service.Mapper;

import java.util.List;

import com.qyt.pojo.SearchItem;

public interface ItemMapper {

	List<SearchItem>  getListItem();
	SearchItem getItemId(Long id);
}
