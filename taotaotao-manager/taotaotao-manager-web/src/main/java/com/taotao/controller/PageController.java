package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
/**
 * 
 * @author zzy
 *
 */
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
	@RequestMapping("/")
	public String getIndex() {
		return "index";
	}

	@RequestMapping("/{page}")
	public String showpage(@PathVariable String page) {
		return page;
	}

}
