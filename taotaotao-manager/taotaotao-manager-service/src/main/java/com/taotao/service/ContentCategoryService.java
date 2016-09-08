package com.taotao.service;

import java.util.List;

import com.taotao.pojo.EUTreeNode;
import com.taotao.pojo.TbContent;
import com.taotao.util.TaotaoResult;

public interface ContentCategoryService {
	public List<EUTreeNode> getCategoryList(long parentId);
	public TaotaoResult insertContentCategory(long parentId, String name);
}
