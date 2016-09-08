package com.taotao.portal.service;

import com.taotao.search.pojo.SearchResult;

public interface SearchService {
	public SearchResult search(String queryString, int page);
}
