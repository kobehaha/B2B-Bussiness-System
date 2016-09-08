package com.taotao.service.Impl;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.service.PictureService;
import com.taotao.util.FtpUtil;
import com.taotao.util.IDUtils;
/**
 * 图片上传服务
 * @author IBM-Thinkpad
 *
 */
@Service
public class PictureServiceImpl implements PictureService {

	@Value("${FTP_ADDRESS}")
	String FTP_ADDRESS;

	@Value("${FTP_PORT}")
	Integer FTP_PORT;

	@Value("${FTP_USERNAME}")
	String FTP_USERNAME;

	@Value("${FTP_PASSWORD}")
	String FTP_PASSWORD;

	@Value("${FTP_BASE_PATH}")
	String FTP_BASE_PATH;

	@Value("${IMAGE_BASE_URL}")
	String IMAGE_BASE_URL;

	// 上传图片
	@Override
	public Map uploadPicture(MultipartFile file) {
		Map resultMap = new HashMap();
		// 判断文件是否为空
		if (file.isEmpty()) {
			resultMap.put("error", 1);
			resultMap.put("message", "无图片上传2");
			return resultMap;
		}

		try {
			// 获取原始名
			String name = file.getOriginalFilename();

			// 生成一个新的名字 1 UUID 2 System
			String newName = IDUtils.genImageName();
			// 获取图片后缀
			newName = newName + name.substring(name.lastIndexOf("."));
			// 图片path
			String imagePath = new DateTime().toString("/yyyy/MM/DD");
			// 图片上传
			Boolean result = FtpUtil.uploadFile(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, FTP_PASSWORD, FTP_BASE_PATH,
					imagePath, newName, file.getInputStream());
			if (!result) {
				resultMap.put("error", 1);
				resultMap.put("message", "imageServerShutDown");
				return resultMap;
			}
			resultMap.put("error", 0);
			resultMap.put("url", IMAGE_BASE_URL + imagePath + "/" + newName);
		} catch (Exception e) {
			resultMap.put("error", 1);
			resultMap.put("message", "文件上传发生异常24");
			return resultMap;
		}
		return resultMap;
	}

}
