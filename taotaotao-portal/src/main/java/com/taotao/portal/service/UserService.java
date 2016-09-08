package com.taotao.portal.service;

import com.taotao.pojo.TbUser;

public interface UserService {
	public TbUser getUserByToken(String token);
}
