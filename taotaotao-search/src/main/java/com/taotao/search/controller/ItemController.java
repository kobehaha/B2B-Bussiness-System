package com.taotao.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.search.pojo.Item;
import com.taotao.search.service.ItemService;
import com.taotao.util.TaotaoResult;

//url /search/manager/
@Controller
@RequestMapping("/manager")
public class ItemController {
	// 导入商品到索引库

	@Autowired
	private ItemService itemService;

	/**
	 * 导入商品数据到索引库
	 */
	@RequestMapping("/importall")
	@ResponseBody
	public TaotaoResult importAllItems() {
		TaotaoResult result = itemService.importAllItems();
		return result;
	}

	/**
	 * 导入一个商品到索引库
	 * @return
	 */
	@RequestMapping("/addItem")
	@ResponseBody
	public TaotaoResult addItem(Item item) {
		TaotaoResult result = itemService.importAllItems();
		return result;
	}
}