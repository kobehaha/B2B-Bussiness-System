package com.taotao.service;

import com.taotao.pojo.EUDDataResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.util.TaotaoResult;

public interface ItemService {
     TbItem getItemById(long id);
     EUDDataResult getItemList(int page,int rows);
     TaotaoResult addItem(TbItem item,String desc,String itemParam) throws Exception;
}
