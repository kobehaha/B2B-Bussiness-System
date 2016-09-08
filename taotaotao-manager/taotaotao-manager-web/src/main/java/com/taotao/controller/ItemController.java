package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.pojo.EUDDataResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import com.taotao.util.TaotaoResult;

@Controller
public class ItemController {
	/**
	 * 查询商品
	 */
      @Autowired 
      private ItemService itemService;
      
      
      @RequestMapping("/item/{itemId}")
      @ResponseBody
      public TbItem getItemById(@PathVariable Long itemId){
    	  TbItem tbItem=itemService.getItemById(itemId);
    	  return tbItem;
      }
      /**
       * 查询列表
       */
      @RequestMapping("/item/list")
      @ResponseBody
      public EUDDataResult getItemList(Integer page,Integer rows){
    	  EUDDataResult result=itemService.getItemList(page, rows);
    	  return result;
      }
      /**
       * 添加商品
       */
      @RequestMapping(value="/item/save",method=RequestMethod.POST)
      @ResponseBody
      public TaotaoResult crateItem(TbItem item,String desc,String itemParams) throws Exception{
    	  TaotaoResult result=itemService.addItem(item,desc,itemParams);
    	  return result;
      }
      
}
