package com.baidu.ueditor.upload;

import com.baidu.ueditor.define.State;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class Uploader {
	private HttpServletRequest request = null;
	private Map<String, Object> conf = null;
	private int moduleType = 0;

	public Uploader(HttpServletRequest request, Map<String, Object> conf, int moduleType) {
		this.request = request;
		this.conf = conf;
		this.moduleType = moduleType;
	}

	public final State doExec() {
		String filedName = (String) this.conf.get("fieldName");
//		State state = null;
//
//		if ("true".equals(this.conf.get("isBase64"))) {
//			state = Base64Uploader.save(this.request.getParameter(filedName),
//					this.conf);
//		} else {
//			state = BinaryUploader.save(this.request, this.conf, moduleType);
//		}
		// 统一调用阿里云接口
		State state = BinaryUploader.save(this.request, this.conf, moduleType);;

		return state;
	}
}
