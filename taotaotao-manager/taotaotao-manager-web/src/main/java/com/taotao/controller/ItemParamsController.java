package com.taotao.controller;
/**
 * 商品分类参数
 * @author IBM-Thinkpad
 *
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.pojo.TbItemParam;
import com.taotao.service.ItemParamService;
import com.taotao.util.TaotaoResult;

@Controller
@RequestMapping("/item/param")
public class ItemParamsController {

	@Autowired
	private ItemParamService itemParamService;

	@RequestMapping("/query/itemcatid/{itemCatId}")
	@ResponseBody
	private TaotaoResult getParamByCid(@PathVariable Long itemCatId) throws Exception {
		TaotaoResult result=itemParamService.getParamByCid(itemCatId);
		return result;
	}

	@RequestMapping("/save/{cid}")
	@ResponseBody
	private TaotaoResult insertParmaInfo(@PathVariable long cid,String paramData) throws Exception{
		TbItemParam itemParam=new TbItemParam();
		itemParam.setItemCatId(cid);
		itemParam.setParamData(paramData);
		TaotaoResult result=itemParamService.insertItemParam(itemParam);
		return result;
	}
}
