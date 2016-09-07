package com.chinahanjiang.crm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.chinahanjiang.crm.dto.LocationDto;
import com.chinahanjiang.crm.dto.ProductDto;
import com.chinahanjiang.crm.pojo.Contact;
import com.chinahanjiang.crm.pojo.Customer;
import com.chinahanjiang.crm.pojo.Groups;
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
				
				HSSFCell name = hssfRow.getCell(1);
				if(name == null){
					continue;
				}
				String _name = getValue(name);
				if(!_name.equals("")){
					
					c = new Customer();
					c.setName(_name);
					
					HSSFCell code = hssfRow.getCell(2);
					String _code = "";
					if(code==null){
						_code = "";
					} else {
						_code=getValue(code);
					}
					 
					c.setCode(_code);
					
					//隶属集团
					HSSFCell group = hssfRow.getCell(3);
					String _group = "";
					if(group != null){
						_group = getValue(group);
					} else {
						_group = "其他";
					}
					
					Groups g = new Groups();
					g.setName(_group);
					c.setGroups(g);
					
					HSSFCell address = hssfRow.getCell(5);
					String _address = address==null ? "" : getValue(address);
					c.setAddress(_address);
					
					HSSFCell telephone = hssfRow.getCell(6);
					String _telephone = telephone==null ? "" : getValue(telephone);
					c.setTelephone(_telephone);
					
					HSSFCell fax = hssfRow.getCell(7);
					String _fax = fax==null ? "" : getValue(fax);
					c.setFax(_fax);
					
					HSSFCell remarks = hssfRow.getCell(8);
					String _remarks = remarks==null ? "" : getValue(remarks);
					c.setRemarks(_remarks);
					
					c.setCreateTime(createTime);
					
					HSSFCell area = hssfRow.getCell(13);
					String _area = getValue(area);
					Location loc = new Location();
					loc.setName(_area);
					c.setLocation(loc);
					
					cts = new ArrayList<Contact>();
					ct = getContact(hssfRow);
					if(ct!=null){
						
						ct.setCustomer(c);
						cts.add(ct);
						c.setContacts(cts);
						
						customers.add(c);
					}
					
					
				} else {
					
					cts = c.getContacts();
					if(ct!=null){
						
						ct = getContact(hssfRow);
						ct.setCustomer(c);
						cts.add(ct);
					}
					
				}
			}
		}
		return customers;
	}

	private static Contact getContact(HSSFRow hssfRow){
		
		Contact ct = new Contact();
		Timestamp createTime = new Timestamp(System.currentTimeMillis());
		
		HSSFCell name = hssfRow.getCell(15);
		if(name == null){
			
			return null;
		}
		String _name = getValue(name);
		ct.setName(_name);
		
		HSSFCell mobilephone = hssfRow.getCell(16);
		String _mobilephone = mobilephone==null ? "" :getValue(mobilephone);
		ct.setMobilePhone(_mobilephone);
		
		HSSFCell email = hssfRow.getCell(17);
		String _email = email==null ? "" : getValue(email);
		ct.setEmail(_email);
		
		HSSFCell sex = hssfRow.getCell(18);
		String _sex = sex==null ? "男" : getValue(sex);
		ct.setSex(_sex.equals("男") ? 1 : 0);
		
		HSSFCell duty = hssfRow.getCell(19);
		String _duty = duty==null ? "" : getValue(duty);
		ct.setDuty(_duty);
		
		HSSFCell remarks = hssfRow.getCell(20);
		String _remarks = remarks==null ? "" : getValue(remarks);
		ct.setRemarks(_remarks);
		
		ct.setCreateTime(createTime);
		
		return ct;
	}
	
	public static Map<String,String> roadGroupInfo(File f) throws IOException{
		
		InputStream is = new FileInputStream(f.getAbsolutePath());
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
		Map<String,String> groups = new HashMap<String,String>();
		
		for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
			if(hssfSheet == null){
				continue;
			}
			
			for(int rowNum = 2; rowNum <= hssfSheet.getLastRowNum(); rowNum++){
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				if(hssfRow==null){
					continue;
				}
				
				//读取数值
				HSSFCell name = hssfRow.getCell(1);
				if(name == null){
					continue;
				}
				String _name = getValue(name);
				if(_name.equals("")){
					continue;
				}
				
				HSSFCell gName = hssfRow.getCell(3);
				String _gName = "";
				if(gName==null){
					
					_gName = "其他";
				} else {
					
					_gName = getValue(gName);
				}
				HSSFCell gCode = hssfRow.getCell(4);
				
				String _gCode = "";
				if(gCode == null){
					
					_gCode = "OT";
				} else {
					
					_gCode = getValue(gCode);
				}
				
				boolean isR = groups.containsKey(_gName);
				if(!isR){
					
					groups.put(_gName, _gCode);
				}
			}
		}
		return groups;
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
				HSSFCell name = hssfRow.getCell(1);
				if(name == null){
					continue;
				}
				String _name = getValue(name);
				if(_name.equals("")){
					continue;
				}
				
				ld = new LocationDto();
				
				HSSFCell farea = hssfRow.getCell(9);
				String _farea = getValue(farea);
				ld.setfArea(_farea);
				
				HSSFCell fCode = hssfRow.getCell(10);
				String _fCode = getValue(fCode);
				ld.setfCode(_fCode);
				
				HSSFCell sarea = hssfRow.getCell(11);
				String _sarea = getValue(sarea);
				ld.setsArea(_sarea);
				
				HSSFCell sCode = hssfRow.getCell(12);
				String _sCode = getValue(sCode);
				ld.setsCode(_sCode);
				
				HSSFCell tarea = hssfRow.getCell(13);
				String _tarea = getValue(tarea);
				ld.settArea(_tarea);
				
				HSSFCell tCode = hssfRow.getCell(14);
				String _tCode = getValue(tCode);
				ld.settCode(_tCode);
				
				lds.add(ld);
			}
		}
		
		return lds;
	}
	
	public static List<List<String>> readProductCatalogInfo(File f) throws IOException {
	
		List<List<String>> catalogs = new ArrayList<List<String>>();
		
		InputStream is = new FileInputStream(f.getAbsolutePath());
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
		
		for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
			if(hssfSheet == null){
				continue;
			}
			
			List<String> catalog = null;
			int i = 0;
			for(int rowNum = 2; rowNum <= hssfSheet.getLastRowNum(); rowNum++){
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				if(hssfRow==null){
					continue;
				}
				
				catalog = new ArrayList<String>();
				
				for(;;){
					
					//读取数值
					HSSFCell cell = hssfRow.getCell(i);
					if(cell != null){
						
						String str = getValue(cell);
						catalog.add(str);
						i++;
						
					} else {
						i=0;
						break;
					}
				}
				
				catalogs.add(catalog);
			}
		}
		return catalogs;
	}
	
	public static List<ProductDto> readProductInfo(File f) throws IOException{
		
		List<ProductDto> pds = new ArrayList<ProductDto>();
		
		InputStream is = new FileInputStream(f.getAbsolutePath());
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
		
		for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
			if(hssfSheet == null){
				continue;
			}
			
			for(int rowNum = 2; rowNum <= hssfSheet.getLastRowNum(); rowNum++){
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				if(hssfRow==null){
					continue;
				}
				
				ProductDto pd = new ProductDto();
				
				HSSFCell name = hssfRow.getCell(0);
				pd.setName(name==null?"":getValue(name));
				
				HSSFCell shotCode = hssfRow.getCell(1);
				pd.setShortCode(shotCode==null?"":getValue(shotCode));
				
				HSSFCell parentCatalog = hssfRow.getCell(2);
				pd.setParentCatalog(parentCatalog==null?"":getValue(parentCatalog));
				
				HSSFCell catalog = hssfRow.getCell(3);
				pd.setProductCatalog(catalog==null?"":getValue(catalog));
				
				HSSFCell standardPrice = hssfRow.getCell(4);
				pd.setStandardPrice(standardPrice==null?Double.valueOf(0.0):Double.valueOf(getValue(standardPrice)));
				
				HSSFCell remarks = hssfRow.getCell(5);
				pd.setRemarks(remarks==null?"":getValue(remarks));
				
				pds.add(pd);
			}
		}
		
		return pds;
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
