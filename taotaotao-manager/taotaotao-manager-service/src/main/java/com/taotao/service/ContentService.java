package com.taotao.service;

import java.util.List;

import com.taotao.pojo.TbContent;
import com.taotao.util.TaotaoResult;

public interface ContentService {
	public TaotaoResult insertContent(TbContent content);

	public List<TbContent> getContentList(long contentCid);

	
}
