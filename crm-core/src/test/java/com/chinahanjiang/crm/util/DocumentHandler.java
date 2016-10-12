package com.chinahanjiang.crm.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class DocumentHandler {

	private Configuration configuration = null;
	
	public DocumentHandler(){
		
		configuration = new Configuration();
		configuration.setDefaultEncoding("utf-8");
	}
	
	public void craeteDoc(Map<String,Object> dataMap,String fileName){
		
		configuration.setClassForTemplateLoading(this.getClass(), "/template");
		Template t = null;
		
		try {
			t = configuration.getTemplate("quote.ftl");
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		File outFile = new File(fileName);
		Writer out = null;
		FileOutputStream fos = null;
		
		try {
			fos = new FileOutputStream(outFile);
			
			OutputStreamWriter oWriter = new OutputStreamWriter(fos,"utf-8");
			//out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));
			out = new BufferedWriter(oWriter);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			
		}
		
		try {
			t.process(dataMap, out);
			out.close();
			fos.close();
			
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
