package com.taotao.service;

import com.taotao.pojo.TbItemParam;
import com.taotao.util.TaotaoResult;

public interface ItemParamService {
	TaotaoResult getParamByCid(Long cid) throws Exception;
	TaotaoResult insertItemParam(TbItemParam itemParam) throws Exception;

}
