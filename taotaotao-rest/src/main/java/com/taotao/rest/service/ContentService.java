package com.taotao.rest.service;

import java.util.List;

import com.taotao.pojo.TbContent;

public interface ContentService {
	public List<TbContent> getContentList(long contentCid);
}
