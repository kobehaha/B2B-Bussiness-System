package com.taotao.rest.service;

import com.taotao.util.TaotaoResult;

public interface RedisService {
	public TaotaoResult syncContent(long contentCid);
}
