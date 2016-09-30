package com.chinahanjiang.crm.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.stereotype.Controller;

import com.chinahanjiang.crm.dto.FileUploadDto;
import com.chinahanjiang.crm.util.DataUtil;

@Controller
@ParentPackage("ajaxdefault")
@Namespace("/file")
@Results({ @Result(name = "error", location = "/error.jsp")})
@ExceptionMappings({ @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class FileAction extends BaseAction{

	private static final long serialVersionUID = 1L;

	private File file;
	
	private String fileName;
	
	private int taskId;
	
	private int itemId;
	
	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	@Action("upload")
	public void upload() throws IOException{
		
		FileUploadDto fud = new FileUploadDto();
		fud.setError("");
		
		String root = ServletActionContext.getServletContext().getRealPath("/uploadfile");
		String savePath = root + "\\" + this.taskId + "\\" + this.itemId;
		
        InputStream is = null;
        try{
        	
        	is = new FileInputStream(file);
        	
	        File nFile = new File(savePath, fileName);
	        OutputStream os = null;
	        try{
	        	
	        	os = new FileOutputStream(nFile);
		        fud.setPath("\\" + taskId + "\\" + itemId + "\\" + fileName);
				
		        byte[] buffer = new byte[500];
		        int length = 0;
		        
		        while(-1 != (length = is.read(buffer, 0, buffer.length))){
		            os.write(buffer);
		        }
		        os.close();
		        
	        }catch(FileNotFoundException e){
	        	
	        	/*服务器保存的路径找不到*/
	        	fud.setError("上传的路径不对，可能是选择的项目或任务出错!");
	        }
	        
	        is.close();
        }catch(FileNotFoundException e){
        	
        	/*传过来的文件找不到!*/
        	fud.setError("上传的文件没有传递过来!");
        }
        
        HttpServletResponse response=ServletActionContext.getResponse();
		response.setContentType("text/html");  
		PrintWriter out;  
		out = response.getWriter();
		String returnStr = DataUtil.convertFileUploadDtoToStr(fud);
		out.println(returnStr);  
		out.flush();  
		out.close();  
	}
}
