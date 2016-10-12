package com.chinahanjiang.crm.util;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.chinahanjiang.crm.dto.LocationDto;
import com.chinahanjiang.crm.dto.ProductCatalogDto;
import com.chinahanjiang.crm.dto.ProductDto;
import com.chinahanjiang.crm.pojo.Customer;
import com.chinahanjiang.crm.pojo.Groups;
import com.chinahanjiang.crm.pojo.Location;
import com.chinahanjiang.crm.pojo.Product;
import com.chinahanjiang.crm.pojo.ProductCatalog;
import com.chinahanjiang.crm.pojo.ProductConfiguration;
import com.chinahanjiang.crm.service.CustomerService;
import com.chinahanjiang.crm.service.GroupsService;
import com.chinahanjiang.crm.service.LocationService;
import com.chinahanjiang.crm.service.ProductCatalogService;
import com.chinahanjiang.crm.service.ProductService;
import com.chinahanjiang.crm.service.impl.CustomerServiceImpl;
import com.chinahanjiang.crm.service.impl.GroupsServiceImpl;
import com.chinahanjiang.crm.service.impl.LocationServiceImpl;
import com.chinahanjiang.crm.service.impl.ProductCatalogServiceImpl;
import com.chinahanjiang.crm.service.impl.ProductServiceImpl;

public class ExcelUtilTest {

	private ApplicationContext context;

	@Before
	public void runContext() {

		context = new ClassPathXmlApplicationContext(
				new String[] { "applicationContext.xml" });
	}
	
	@Test
	public void testImportProduct() throws IOException{
		
		ProductService ps = (ProductServiceImpl) context
				.getBean("productService");
		ProductCatalogService pcs = (ProductCatalogServiceImpl) context
				.getBean("productCatalogService");
		
		Timestamp now = new Timestamp(System.currentTimeMillis());
		
		File f = new File("C:\\Users\\tree\\Desktop\\客户关系管理\\40基础资料\\4-products.xls");
		
		List<ProductDto> pds = ExcelUtil.readProductInfo(f);
		
		Iterator<ProductDto> it = pds.iterator();
		while(it.hasNext()){
			
			ProductDto pd = it.next();
			Product p = new Product();
			
			String pcCode = pd.getParentCatalog();
			String catalog = pd.getProductCatalog();
			
			ProductCatalog pc = pcs.findProductCatalogByCodeAndName(pcCode,catalog);
			if(pc!=null){
				
				p.setProductCatalog(pc);
				p.setName(pd.getName());
				p.setShortCode(pd.getShortCode());
				p.setCreateTime(now);
				p.setRemarks(pd.getRemarks());
				
				ps.save(p);
				
				List<ProductConfiguration> mixs = new ArrayList<ProductConfiguration>();
				ProductConfiguration pcf = new ProductConfiguration();
				pcf.setSproduct(p);
				pcf.setFproduct(p);
				pcf.setCreateTime(now);
				mixs.add(pcf);
				
				p.setProductMix(mixs);
				
				ps.save(p);
				
			} else {
				
				continue;
			}
		}
	}
	
	@Test
	public void testImportProductCatalog() throws IOException{
		
		ProductCatalogService pcs = (ProductCatalogServiceImpl) context
				.getBean("productCatalogService");
		
		Timestamp now = new Timestamp(System.currentTimeMillis());
		
		File f = new File("C:\\Users\\tree\\Desktop\\客户关系管理\\40基础资料\\3-productcatalog.xls");
		List<List<String>> catalogs = ExcelUtil.readProductCatalogInfo(f);
		Iterator<List<String>> it = catalogs.iterator();
		while(it.hasNext()){
			
			List<String> catalog = it.next();
			
			int len = catalog.size();
			System.out.println(len + "-" + catalog.get(len-2) + "-" + catalog.get(len-1));
			ProductCatalogDto pcd = new ProductCatalogDto();
			pcd.setCode(catalog.get(len-1));
			pcd.setName(catalog.get(len-2));
			
			if(len>2){
				
				pcd.setParentCode(catalog.get(len-3));
			}
			
			ProductCatalog pPc = null;
			ProductCatalog pc = new ProductCatalog();
			String pCode = pcd.getParentCode();
			if(pCode!=null){
				try{
					
					pPc = pcs.findProductCatalogByCode(pCode);
					
				} catch(Exception e){
					
					String ppCode = catalog.get(len-5);
					ProductCatalog ppPc = pcs.findProductCatalogByCode(ppCode);
					if(ppPc!=null){
						
						pPc = pcs.findProductCatalogByCode(pCode,ppPc);
					}
				}
			}
			pc.setName(pcd.getName());
			pc.setCode(pcd.getCode());
			pc.setCreateTime(now);
			
			if(pPc!=null){
				
				pc.setParentCatalog(pPc);
			}
			
			pcs.save(pc);
			
		}
	}
	
	@Test
	public void testImportCustomer() throws IOException{
		
		CustomerService cs = (CustomerServiceImpl) context
				.getBean("customerService");
		LocationService ls = (LocationServiceImpl) context
				.getBean("locationService");
		GroupsService gs = (GroupsServiceImpl) context
				.getBean("groupsService");
		
		File f = new File("C:\\Users\\tree\\Desktop\\客户关系管理\\40基础资料\\2-0 customer-国内客户资料.xls");
		List<Customer> customers = ExcelUtil.readCustomerInfo(f);
		
		Iterator<Customer> it = customers.iterator();
		while(it.hasNext()){
			
			Customer c = it.next();
			
			Location l = c.getLocation();
			Groups g = c.getGroups();
			
			if(l!=null){
				
				String area = l.getName();
				Location loc = ls.findByName(area);
				
				String gName = g.getName();
				Groups group = gs.findByName(gName);
				
				if(loc!=null){
					c.setLocation(loc);
				}
				if(group!=null){
					c.setGroups(group);
				}
			}
			cs.save(c);
		}
	}
	
	@Test
	public void testImportGroups() throws IOException{
		
		GroupsService gs = (GroupsServiceImpl) context.getBean("groupsService");
		
		Timestamp now = new Timestamp(System.currentTimeMillis());
		File f = new File("C:\\Users\\tree\\Desktop\\客户关系管理\\40基础资料\\2-0 customer-国内客户资料.xls");
		Map<String,String> maps = ExcelUtil.roadGroupInfo(f);
		Set<String> s = maps.keySet();
		Iterator<String> it = s.iterator();
		while(it.hasNext()){
			
			String name = it.next();
			String code = maps.get(name);
			
			Groups g = new Groups();
			g.setName(name);
			g.setCode(code);
			g.setCreateTime(now);
			
			gs.save(g);
		}
	}
	
	@Test
	public void testImportLocations() throws IOException{
		
		LocationService ls = (LocationServiceImpl) context
				.getBean("locationService");
		
		Timestamp now = new Timestamp(System.currentTimeMillis());
		
		File f = new File("C:\\Users\\tree\\Desktop\\客户关系管理\\40基础资料\\2-0 customer-国内客户资料.xls");
		List<LocationDto> eds = ExcelUtil.readLocationInfo(f);
		
		Location fl = new Location();
		fl.setName("区域分类");
		fl.setCode("All");
		fl.setCreateTime(now);
		fl.setIsDelete(1);
		List<Location> flls = new ArrayList<Location>();
		
		Map<Location,Map<Location,List<Location>>> locfMaps = new HashMap<Location,Map<Location,List<Location>>>();
		Map<Location,List<Location>> locsMaps = null;
		List<Location> lds = null;
		
		for(LocationDto d : eds){
			
			String farea = d.getfArea();
			String fCode = d.getfCode();
			String sarea = d.getsArea();
			String sCode = d.getsCode();
			String tarea = d.gettArea();
			String tCode = d.gettCode();
			
			Location fLoc = new Location();
			fLoc.setName(farea);
			fLoc.setCode(fCode);
			fLoc.setState("closed");
			
			boolean i = locfMaps.containsKey(fLoc);
			if(i){
				locsMaps = locfMaps.get(fLoc);
			} else {
				locsMaps = new HashMap<Location,List<Location>>();
			}
			
			Location sLoc = new Location();
			sLoc.setName(sarea);
			sLoc.setCode(sCode);
			sLoc.setState("closed");
			
			boolean j = locsMaps.containsKey(sLoc);
			if(j){
				lds = locsMaps.get(sLoc);
			} else {
				lds = new ArrayList<Location>();
			}
			
			Location tLoc = new Location();
			tLoc.setName(tarea);
			tLoc.setCode(tCode);
			
			boolean k = lds.contains(tLoc);
			if(!k){
				lds.add(tLoc);
			}
			
			locsMaps.put(sLoc, lds);
			locfMaps.put(fLoc, locsMaps);
		}
		
		Set<Location> s = locfMaps.keySet();
		Iterator<Location> it = s.iterator();
		while(it.hasNext()){
			
			Location floc = it.next();
			floc.setCreateTime(now);
			floc.setIsDelete(1);
			floc.setParentLoc(fl);
			flls.add(floc);
			
			List<Location> fcls = new ArrayList<Location>();
			
			Map<Location,List<Location>> map = locfMaps.get(floc);
			Set<Location> fset = map.keySet();
			Iterator<Location> fit = fset.iterator();
			while(fit.hasNext()){
				
				Location sloc = fit.next();
				sloc.setParentLoc(floc);
				sloc.setCreateTime(now);
				sloc.setIsDelete(1);
				
				fcls.add(sloc);
				List<Location> scls = new ArrayList<Location>();
				
				List<Location> slist = map.get(sloc);
				Iterator<Location> sit = slist.iterator();
				while(sit.hasNext()){
					
					Location tloc = sit.next();
					tloc.setParentLoc(sloc);
					tloc.setCreateTime(now);
					tloc.setIsDelete(1);
					
					scls.add(tloc);
					
				}
				
				sloc.setChildLocs(scls);
			}
			
			floc.setChildLocs(fcls);
		}
		
		fl.setChildLocs(flls);
		ls.save(fl);
	}
	
	@Test
	public void testHashCode(){
		
		Location l1 = new Location();
		l1.setName("中国");
		Location l2 = new Location();
		l2.setName("中国");
		
		System.out.println(l1.equals(l2));
	}
	
	@Test
	public void testPassword(){
		
	}
}
