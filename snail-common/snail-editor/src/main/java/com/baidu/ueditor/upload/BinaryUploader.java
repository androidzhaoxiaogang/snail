package com.baidu.ueditor.upload;

import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.FileType;
import com.baidu.ueditor.define.State;
import com.snail.common.util.AliyunUtils;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class BinaryUploader {

	/*public static final State save(HttpServletRequest request,
			Map<String, Object> conf) {
		FileItemStream fileStream = null;
		boolean isAjaxUpload = request.getHeader( "X_Requested_With" ) != null;

		if (!ServletFileUpload.isMultipartContent(request)) {
			return new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT);
		}

		ServletFileUpload upload = new ServletFileUpload(
				new DiskFileItemFactory());

        if ( isAjaxUpload ) {
            upload.setHeaderEncoding( "UTF-8" );
        }

//		try {
//
//			FileItemIterator iterator = upload.getItemIterator(request);
//
//			while (iterator.hasNext()) {
//				fileStream = iterator.next();
//
//				if (!fileStream.isFormField())
//					break;
//				fileStream = null;
//			}
//
//			if (fileStream == null) {
//				return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);
//			}
//
//			String savePath = (String) conf.get("savePath");
//			String originFileName = fileStream.getName();
//			String suffix = FileType.getSuffixByFilename(originFileName);
//
//			originFileName = originFileName.substring(0,
//					originFileName.length() - suffix.length());
//			savePath = savePath + suffix;
//
//			long maxSize = ((Long) conf.get("maxSize")).longValue();
//
//			if (!validType(suffix, (String[]) conf.get("allowFiles"))) {
//				return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
//			}
//
//			savePath = PathFormat.parse(savePath, originFileName);
//
//			 String rootPath = conf.get("iamgeRootPath").toString();
//            if(StringUtils.isBlank(rootPath))
//           	rootPath = request.getServletContext().getRealPath("/");
//
//            String physicalPath = rootPath + savePath;
//
//			//String physicalPath = (String) conf.get("rootPath") + savePath;
//
//			InputStream is = fileStream.openStream();
//			State storageState = StorageManager.saveFileByInputStream(is,
//					physicalPath, maxSize);
//			is.close();
//
//			if (storageState.isSuccess()) {
//				storageState.putInfo("url", PathFormat.format(savePath));
//				storageState.putInfo("type", suffix);
//				storageState.putInfo("original", originFileName + suffix);
//			}
//
//			return storageState;
//		} catch (FileUploadException e) {
//			return new BaseState(false, AppInfo.PARSE_REQUEST_ERROR);
//		} catch (IOException e) {
//		}
        
        try {

        	MultipartHttpServletRequest wrapper = (MultipartHttpServletRequest) request;
        	MultipartFile file = wrapper.getFile("upfile");

//            String savePath = (String) conf.get("savePath");
//            String originFileName = UUID.randomUUID().toString() + ".jpg";
//            String suffix = FileType.getSuffixByFilename(originFileName);
//
//            originFileName = originFileName.substring(0,originFileName.length() - suffix.length());
//            savePath = savePath + suffix;
//
//            long maxSize = ((Long) conf.get("maxSize")).longValue();
//
//            if (!validType(suffix, (String[]) conf.get("allowFiles"))) {
//                return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
//            }
//
//            savePath = PathFormat.parse(savePath, originFileName);
//
//            String rootPath = conf.get("iamgeRootPath").toString();
//            if(StringUtils.isBlank(rootPath))
//            	rootPath = request.getServletContext().getRealPath("/");
//
//            String physicalPath = rootPath + savePath;
//
//            InputStream is = file.getInputStream();
//            State storageState = StorageManager.saveFileByInputStream(is,physicalPath, maxSize);
//            is.close();

			String savePath = (String) conf.get("savePath");
			String originFileName = file.getOriginalFilename();
			String suffix = FileType.getSuffixByFilename(originFileName);

			originFileName = originFileName.substring(0,
					originFileName.length() - suffix.length());
			savePath = savePath + suffix;

			long maxSize = ((Long) conf.get("maxSize")).longValue();

			if (!validType(suffix, (String[]) conf.get("allowFiles"))) {
				return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
			}

			savePath = PathFormat.parse(savePath, originFileName);

			String physicalPath = (String) conf.get("rootPath") + savePath;

			InputStream is = file.getInputStream();
			State storageState = StorageManager.saveFileByInputStream(is,
					physicalPath, maxSize);
			is.close();

            if (storageState.isSuccess()) {
                storageState.putInfo("url", PathFormat.format(savePath));
                storageState.putInfo("type", suffix);
                storageState.putInfo("original", originFileName + suffix);
            }

            return storageState;
        }catch (Exception e) {
            e.printStackTrace();
        }
        
		return new BaseState(false, AppInfo.IO_ERROR);
	}*/

	public static final State save(HttpServletRequest request, Map<String, Object> conf, int moduleType) {
		boolean isAjaxUpload = request.getHeader("X_Requested_With") != null;

		if (!ServletFileUpload.isMultipartContent(request)) {
			return new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT);
		}

		ServletFileUpload upload = new ServletFileUpload(
				new DiskFileItemFactory());

		if (isAjaxUpload) {
			upload.setHeaderEncoding("UTF-8");
		}

		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile file = multipartRequest.getFile("upfile");

			String originFileName = file.getOriginalFilename();
			String suffix = FileType.getSuffixByFilename(originFileName);

			originFileName = originFileName.substring(0,
					originFileName.length() - suffix.length());

			if (!validType(suffix, (String[]) conf.get("allowFiles"))) {
				return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
			}

			String url = null;
			try {
				AssumeRoleResponse.Credentials credential = AliyunUtils.doAssumeRole(null, null);

				String bucketName = moduleType == 1 ? "more-activity" : "more-info";

				url = AliyunUtils.postObject(credential, file.getInputStream(), bucketName, suffix);
			} catch (Exception e) {
				e.printStackTrace();
				return new BaseState(false, AppInfo.IO_ERROR);
			}

			State storageState = new BaseState(true);
			if (storageState.isSuccess()) {
				storageState.putInfo("url", url);
				storageState.putInfo("type", suffix);
				storageState.putInfo("original", originFileName + suffix);
			}

			return storageState;
		} catch (Exception e) {
			e.printStackTrace();
			return new BaseState(false, AppInfo.IO_ERROR);
		}

	}
	
	private static boolean validType(String type, String[] allowTypes) {
		List<String> list = Arrays.asList(allowTypes);

		return list.contains(type);
	}
}
