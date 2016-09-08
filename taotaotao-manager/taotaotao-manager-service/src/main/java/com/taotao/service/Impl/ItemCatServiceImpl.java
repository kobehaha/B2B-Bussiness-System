package com.taotao.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.EUTreeNode;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.service.ItemCatService;
@Service
public class ItemCatServiceImpl implements ItemCatService{
	
	@Autowired
	TbItemCatMapper catMapper;
    /**
     * 获取类别
     */
	@Override
	public List<EUTreeNode> getCatList(long parentId) {
		//创建查询条件
		TbItemCatExample example=new TbItemCatExample();
		Criteria criteria=example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		//查询封装对象
		List<TbItemCat> list=catMapper.selectByExample(example);
		List<EUTreeNode> nodes=new ArrayList<>();
		for(TbItemCat item:list){
			EUTreeNode node=new EUTreeNode();
			node.setId(item.getId());
			node.setText(item.getName());
			node.setState(item.getIsParent()?"closed":"open");
			nodes.add(node);
		}
		return nodes;
	}

}
