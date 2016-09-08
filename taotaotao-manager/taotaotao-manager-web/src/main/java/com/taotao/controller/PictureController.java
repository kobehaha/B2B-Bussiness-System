package com.taotao.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.service.PictureService;
import com.taotao.util.JsonUtils;

@Controller
@RequestMapping("/pic")
public class PictureController {
	 
	@Autowired 
	private PictureService pictureService;
	
	@RequestMapping("/upload")
	@ResponseBody
	public String uploadPicture(MultipartFile uploadFile){
		Map<?, ?> resultMap=pictureService.uploadPicture(uploadFile);
		String resultJson=JsonUtils.objectToJson(resultMap);
		return resultJson;
	}

}
