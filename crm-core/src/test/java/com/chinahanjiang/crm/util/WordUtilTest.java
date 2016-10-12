package com.chinahanjiang.crm.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Test;

public class WordUtilTest {

	@Test
	public void testWrite() {

		File file = new File(
				"C:\\Users\\tree\\git\\hj-crm-project\\crm-web\\src\\main\\webapp\\template\\[bak]Q-CN-HYY-160107-5.xml");
		BufferedReader read = null;
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(
					new FileWriter(
							"C:\\Users\\tree\\git\\hj-crm-project\\crm-web\\src\\main\\webapp\\template\\quote.ftl"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			read = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = read.readLine()) != null) {
				writer.append(tempString.trim());
				writer.flush();// 需要及时清掉流的缓冲区，万一文件过大就有可能无法写入了
			}
			read.close();
			writer.close();
			System.out.println("文件写入完成...");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testXmlSpace() {
		 
        String str =
            "<cc>\r\n" +
            "  <c code=\"3\">100</c>\r\n" +
            "  <c code=\"5\">100</c>\n" +
            "</cc>";
         
        System.out.println(str);
        System.out.println();
         
        str = str.replaceAll("(?<=>)\\s+(?=<)", "");
        System.out.println(str);
	}
}
