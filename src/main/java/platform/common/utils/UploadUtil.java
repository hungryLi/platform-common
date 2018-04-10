package platform.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;

public class UploadUtil {
	private static String newhouse_upload_virPath=ConfigureUtil.getParameter("newhouse.upload.virPath");
	
	public static class UploadResult{
		private String relativePath;
		private String fileUrl;
		public String getRelativePath() {
			return relativePath;
		}
		public void setRelativePath(String relativePath) {
			this.relativePath = relativePath;
		}
		public String getFileUrl() {
			return fileUrl;
		}
		public void setFileUrl(String fileUrl) {
			this.fileUrl = fileUrl;
		}
	}
	
	/***
	 * 保存上传文件
	 * @param parentPath 保存文件的根目录
	 * @param fileName 文件名称 
	 * @param fileInput 文件流
	 * @return 被保存文件的属性对象
	 */
	public static UploadResult saveUploadFile(String parentPath,String fileName,InputStream fileInput,HttpServletRequest request){
		UploadResult result=new UploadResult();
		File file=new File(parentPath);
		String absolutePath=file.getAbsolutePath();
		String relativePath=buildRandomPath();
		File realFile=new File(absolutePath+relativePath+File.separator);
		if(!realFile.exists()){
			boolean create=realFile.mkdirs();
			inputStream2File(fileInput,new File(realFile.getAbsoluteFile()+File.separator+fileName));
		}
		String finalRelative=relativePath+File.separator+fileName;
		result.setRelativePath(finalRelative);
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/"+newhouse_upload_virPath+finalRelative.replace("\\", "/");
		result.setFileUrl(basePath);
		return result;
	}
	
	public static String buildRandomPath(){
		RandomGUID guid=new RandomGUID();
		String guidStr=guid.toString().substring(0, 5);
		String relativePath="";
		for (int i=0;i<5;i++) {
			relativePath=relativePath+File.separator+guidStr.charAt(i);
		}
		return relativePath;
	}
	
	public static String buildFileUrl(String relativePath,HttpServletRequest request){
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/"+newhouse_upload_virPath+relativePath.replace("\\", "/");
		return basePath;
	}
	
	private static JSONObject buildUpLoadFileJson(String[] files){
		JSONObject fileJson=new JSONObject();
		if(files.length>0){
			fileJson.put("files", Arrays.asList(files));
		}
		return fileJson;
	}
	
	public static List<UploadResult> buildUploadResultList(String fileJsonStr,HttpServletRequest request){
		List<UploadResult> result=new ArrayList<UploadResult>();
		if(!StringUtil.isEmpty(fileJsonStr)){
			JSONObject json=JSONObject.parseObject(fileJsonStr);
			String fileList=json.getString("files");
			String[] list=fileList.replace("[", "").replace("]", "").split(",");
			for (String string : list) {
				UploadResult one=new UploadResult();
				string=string.replaceAll("\"", "").replace("\\\\", "\\");
				one.setFileUrl(buildFileUrl(string,request));
				one.setRelativePath(string);
				result.add(one);
			}
		}
		return  result;
	}
	
	private static void inputStream2File(InputStream ins, File file) {
		OutputStream os = null;
		try {
			os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[1024];
			while ((bytesRead = ins.read(buffer, 0, 1024)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (ins != null) {
				try {
					ins.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}
}
