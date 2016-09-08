package com.taotao.service.Impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.pojo.TbItemParamItemExample.Criteria;
import com.taotao.service.ItemParamItemService;
import com.taotao.util.JsonUtils;
@Service
public class ItemParamItemServiceImpl implements ItemParamItemService {

	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;

	@Override
	public String getItemParam(long itemId) {
		// 根据商品id查询规格参数
		TbItemParamItemExample example = new TbItemParamItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		// 执行查询
		List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
		if (list == null || list.size() == 0) {
			return "";
		}
		// 取规格参数信息
		TbItemParamItem itemParamItem = list.get(0);
		String paramData = itemParamItem.getParamData();
		// 生成html
		// 把规格参数json数据转换成java对象
		List<Map> jsonList = JsonUtils.jsonToList(paramData, Map.class);
		StringBuffer sb = new StringBuffer();
		sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"1\" class=\"Ptable\">\n");
		sb.append(" <tbody>");
		// 封装HTML 从网上爬下来，然后在navicat查询窗口中复制java
		for (Map m : jsonList) {
			sb.append("<tr>");
			sb.append("<th class=\"tdTitle\" colspan=\"2\">" + m.get("group") + "</th>");
			sb.append("</tr>");
			List<Map> list2 = (List<Map>) m.get("params");
			for (Map m2 : list2) {
				sb.append(" <tr>" + "<td class=\"tdTitle\">" + m2.get("k") + "</td>" + "<td>" + m2.get("v") + "</td>"
						+ "</tr>\n");
			}
		}
		sb.append("</tbody></table>");
		return sb.toString();

	}

}
