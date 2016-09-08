package com.taotao.controller;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;

public class TestPageHelper {

	@Test
	public void testPageHelper() {
		// 创建一个spring容器，从其中获取mapper代理对象，执行查询，分页
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*");
		TbItemMapper itemMapper = applicationContext.getBean(TbItemMapper.class);
		TbItemExample example = new TbItemExample();
		PageHelper.startPage(1, 10);
        List<TbItem> items=itemMapper.selectByExample(example);
        for(TbItem item:items){
        	System.out.println("商品的标题为:"+item.getTitle());
        }
        PageInfo<TbItem> pageInfo=new PageInfo<>(items);
        long totoal=pageInfo.getTotal();
        System.out.println("一共有"+totoal+"条数据");
 
	}
}
