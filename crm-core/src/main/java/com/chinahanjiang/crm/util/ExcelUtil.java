package com.chinahanjiang.crm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.chinahanjiang.crm.dto.LocationDto;
import com.chinahanjiang.crm.pojo.Contact;
import com.chinahanjiang.crm.pojo.Customer;
import com.chinahanjiang.crm.pojo.Location;

public class ExcelUtil {

	private static SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	private static DateFormat df = new SimpleDateFormat("yy-MM-dd");

	public static List<Customer> readCustomerInfo(File f) throws IOException {

		InputStream is = new FileInputStream(f.getAbsolutePath());
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
		List<Customer> customers = new ArrayList<Customer>();
		Timestamp createTime = new Timestamp(System.currentTimeMillis());

		for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
			if(hssfSheet == null){
				continue;
			}
			
			Customer c = null;
			List<Contact> cts = null;
			Contact ct = null;
			
			for(int rowNum = 2; rowNum <= hssfSheet.getLastRowNum(); rowNum++){
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				if(hssfRow==null){
					continue;
				}
				
				//读取数值
				HSSFCell id = hssfRow.getCell(0);
				if(id == null){
					continue;
				}
				
				HSSFCell code = hssfRow.getCell(1);
				if(code != null){
					
					if(c!=null){
						
						c.setContacts(cts);
						customers.add(c);
					}
					
					c = new Customer();
					String _code = getValue(code);
					c.setCode(_code);
					
					HSSFCell name = hssfRow.getCell(2);
					String _name = getValue(name);
					c.setName(_name);
					
					HSSFCell address = hssfRow.getCell(3);
					String _address = getValue(address);
					c.setAddress(_address);
					
					HSSFCell telephone = hssfRow.getCell(4);
					String _telephone = getValue(telephone);
					c.setTelephone(_telephone);
					
					HSSFCell fax = hssfRow.getCell(5);
					String _fax = getValue(fax);
					c.setFax(_fax);
					
					HSSFCell remarks = hssfRow.getCell(6);
					String _remarks = getValue(remarks);
					c.setRemarks(_remarks);
					
					c.setCreateTime(createTime);
					
					HSSFCell area = hssfRow.getCell(9);
					String _area = getValue(area);
					Location loc = new Location();
					loc.setName(_area);
					c.setLocation(loc);
					
					cts = new ArrayList<Contact>();
					ct = getContact(hssfRow);
					cts.add(ct);
					
					
				} else {
					
					ct = getContact(hssfRow);
					cts.add(ct);
				}
			}
		}
		return customers;
	}

	private static Contact getContact(HSSFRow hssfRow){
		
		Contact ct = new Contact();
		Timestamp createTime = new Timestamp(System.currentTimeMillis());
		
		HSSFCell name = hssfRow.getCell(10);
		String _name = getValue(name);
		ct.setName(_name);
		
		HSSFCell mobilephone = hssfRow.getCell(11);
		String _mobilephone = getValue(mobilephone);
		ct.setMobilePhone(_mobilephone);
		
		HSSFCell email = hssfRow.getCell(12);
		String _email = getValue(email);
		ct.setEmail(_email);
		
		HSSFCell sex = hssfRow.getCell(13);
		String _sex = getValue(sex);
		ct.setSex(_sex.equals("男") ? 1 : 0);
		
		HSSFCell duty = hssfRow.getCell(14);
		String _duty = getValue(duty);
		ct.setDuty(_duty);
		
		HSSFCell remarks = hssfRow.getCell(15);
		String _remarks = getValue(remarks);
		ct.setRemarks(_remarks);
		
		ct.setCreateTime(createTime);
		
		return ct;
	}
	
	public static List<LocationDto> readLocationInfo(File f) throws IOException {

		InputStream is = new FileInputStream(f.getAbsolutePath());
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
		List<LocationDto> lds = new ArrayList<LocationDto>();

		for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
			if(hssfSheet == null){
				continue;
			}
			
			LocationDto ld = null;
			
			for(int rowNum = 2; rowNum <= hssfSheet.getLastRowNum(); rowNum++){
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				if(hssfRow==null){
					continue;
				}
				
				//读取数值
				HSSFCell code = hssfRow.getCell(1);
				if(code == null){
					continue;
				}
				String _code = getValue(code);
				if(_code.equals("")){
					continue;
				}
				
				ld = new LocationDto();
				
				HSSFCell farea = hssfRow.getCell(7);
				String _farea = getValue(farea);
				ld.setfArea(_farea);
				
				HSSFCell sarea = hssfRow.getCell(8);
				String _sarea = getValue(sarea);
				ld.setsArea(_sarea);
				
				HSSFCell tarea = hssfRow.getCell(9);
				String _tarea = getValue(tarea);
				ld.settArea(_tarea);
				
				lds.add(ld);
			}
		}
		
		return lds;
	}
	
	@SuppressWarnings("static-access")
	private static String getValue(HSSFCell hssfCell) {
		if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(hssfCell.getBooleanCellValue()).trim();
		} else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
			return String.valueOf(hssfCell.getNumericCellValue()).trim();
		} else {
			return String.valueOf(hssfCell.getStringCellValue()).trim();
		}

	}
}
