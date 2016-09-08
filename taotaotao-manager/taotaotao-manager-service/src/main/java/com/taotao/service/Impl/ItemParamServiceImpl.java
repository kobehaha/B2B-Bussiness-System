package com.taotao.service.Impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamExample;
import com.taotao.pojo.TbItemParamExample.Criteria;
import com.taotao.service.ItemParamService;
import com.taotao.util.TaotaoResult;

/*
 * 商品类别service
 */
@Service
public class ItemParamServiceImpl implements ItemParamService {

	@Autowired
	private TbItemParamMapper itemParamMapper;

	@Override
	public TaotaoResult getParamByCid(Long cid) {
		TbItemParamExample itemParmas = new TbItemParamExample();
		Criteria criteria = itemParmas.createCriteria();
		criteria.andItemCatIdEqualTo(cid);
		//包含文本列
		List<TbItemParam> params = itemParamMapper.selectByExampleWithBLOBs(itemParmas);
		if (params != null && params.size() > 0) {
			return TaotaoResult.ok(params.get(0));
		}
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult insertItemParam(TbItemParam itemParam) throws Exception {
		//补全pojo
		itemParam.setCreated(new Date());
		itemParam.setUpdated(new Date());
		//insert
		itemParamMapper.insert(itemParam);
		return TaotaoResult.ok();
	}

}
