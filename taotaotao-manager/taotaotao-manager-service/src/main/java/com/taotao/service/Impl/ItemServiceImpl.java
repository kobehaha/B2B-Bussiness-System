package com.taotao.service.Impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.EUDDataResult;
import com.taotao.pojo.Item;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemExample.Criteria;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.service.ItemService;
import com.taotao.util.HttpClientUtil;
import com.taotao.util.IDUtils;
import com.taotao.util.JsonUtils;
import com.taotao.util.TaotaoResult;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private TbItemParamItemMapper itemParamMapper;
	@Autowired
	private SolrServer solrServer;

	@Override
	public TbItem getItemById(long id) {
		// TbItem item=itemMapper.selectByPrimaryKey(id);
		TbItemExample example = new TbItemExample();
		// 添加查询条件
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(id);
		List<TbItem> list = itemMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			TbItem item = list.get(0);
			return item;
		}

		return null;
	}

	/**
	 * 获取查询
	 */
	@Override
	public EUDDataResult getItemList(int page, int rows) {
		TbItemExample example = new TbItemExample();
		// 分页
		PageHelper.startPage(page, rows);
		List<TbItem> items = itemMapper.selectByExample(example);
		// 设置返回值对象
		EUDDataResult result = new EUDDataResult();
		result.setRows(items);
		PageInfo<TbItem> pageInfo = new PageInfo<>(items);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	/**
	 * 添加商品
	 */
	@Override
	public TaotaoResult addItem(TbItem item, String desc, String itemParam) throws Exception {
		if (item.equals(null)) {
			return null;
		}
		item.setId(IDUtils.genItemId());
		item.setCreated(new Date());
		item.setStatus((byte) 1);
		item.setUpdated(new Date());
		// 插入数据库
		itemMapper.insert(item);
		TaotaoResult result = insertItemDesc(item.getId(), desc);
		if (result.getStatus() != 200) {
			throw new Exception();
		}
		// 插入参数
		result = insertItmeParams(item.getId(), itemParam);
		if (result.getStatus() != 200) {
			throw new Exception();
		}
		result = insertIntoSolr(item, desc);
        if(result.getStatus()!=200){
        	throw new Exception();
        }
		return TaotaoResult.ok();
	}

	private TaotaoResult insertIntoSolr(TbItem item, String desc) throws Exception {
		/** category_name; private String item_des; 添加商品到索引库中 */
		Item indexItem = new Item();
		indexItem.setId(String.valueOf(item.getId()));
		indexItem.setPrice(item.getPrice());
		indexItem.setTitle(item.getTitle());
		indexItem.setImage(item.getImage());
		indexItem.setItem_des(desc);
		indexItem.setSell_point(item.getSellPoint());
		indexItem.setCategory_name("");
		// 创建一个SolrInputDocument对象
		SolrInputDocument document = new SolrInputDocument();
		document.setField("id", indexItem.getId());
		document.setField("item_title", indexItem.getTitle());
		document.setField("item_sell_point", indexItem.getSell_point());
		document.setField("item_price", indexItem.getPrice());
		document.setField("item_image", indexItem.getImage());
		document.setField("item_category_name", indexItem.getCategory_name());
		document.setField("item_desc", indexItem.getItem_des());
		// 写入索引库
		solrServer.add(document);
		solrServer.commit();
		return TaotaoResult.ok();

	}

	/*
	 * 添加商品描述
	 */
	public TaotaoResult insertItemDesc(Long itemId, String descString) {
		TbItemDesc desc = new TbItemDesc();
		desc.setCreated(new Date());
		desc.setItemId(itemId);
		desc.setItemDesc(descString);
		itemDescMapper.insert(desc);
		return TaotaoResult.ok();

	}

	/**
	 * 添加商品规格
	 */
	public TaotaoResult insertItmeParams(Long itemId, String params) {
		TbItemParamItem param = new TbItemParamItem();
		param.setItemId(itemId);
		param.setCreated(new Date());
		param.setUpdated(new Date());
		param.setParamData(params);
		itemParamMapper.insert(param);
		return TaotaoResult.ok();
	}

}
