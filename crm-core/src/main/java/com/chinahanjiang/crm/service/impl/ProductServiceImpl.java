package com.chinahanjiang.crm.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.chinahanjiang.crm.dao.ProductDao;
import com.chinahanjiang.crm.dto.MessageDto;
import com.chinahanjiang.crm.dto.ProductDto;
import com.chinahanjiang.crm.dto.SearchResultDto;
import com.chinahanjiang.crm.pojo.Item;
import com.chinahanjiang.crm.pojo.Product;
import com.chinahanjiang.crm.pojo.ProductCatalog;
import com.chinahanjiang.crm.pojo.ProductConfiguration;
import com.chinahanjiang.crm.pojo.Task;
import com.chinahanjiang.crm.pojo.Unit;
import com.chinahanjiang.crm.service.ProductCatalogService;
import com.chinahanjiang.crm.service.ProductConfigurationService;
import com.chinahanjiang.crm.service.ProductService;
import com.chinahanjiang.crm.service.UnitService;
import com.chinahanjiang.crm.util.DataUtil;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

@Service("productService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class ProductServiceImpl implements ProductService {

	@Resource
	private ProductDao productDao;

	@Resource
	private ProductCatalogService productCatalogService;
	
	@Resource
	private ProductConfigurationService productConfigurationService;
	
	@Resource
	private UnitService unitService;
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean save(Product p){
		
		return productDao.save(p);
	}
	
	@Override
	public SearchResultDto searchAndCount(int productCatalogId, String order,
			String sort, int page, int row) {
		
		Search search = new Search();
		search.addFilterEqual("isDelete", 1);
		
		ProductCatalog pc = productCatalogService.findById(productCatalogId);
		if(pc!=null){
			
			List<ProductCatalog> childPcs = DataUtil.getAllPcChildren(pc);
			search.addFilterIn("productCatalog", childPcs);
		}
		
		search.setMaxResults(row);
		search.setPage(page - 1 < 0 ? 0 : page - 1);
		SearchResult<Product> result = searchAndCount(search);
		List<Product> ps = result.getResult();
		
		/*配置product的组合*/
		Iterator<Product> it = ps.iterator();
		while(it.hasNext()){
			
			Product p = it.next();
			
			List<ProductConfiguration> pcfs = productConfigurationService.findByFProduct(p);
			p.setProductMix(pcfs);
		}

		List<ProductDto> pds = DataUtil.convertProductToDto(ps);
		int records = result.getTotalCount();

		SearchResultDto srd = new SearchResultDto();
		srd.getRows().clear();
		srd.getRows().addAll(pds);
		srd.setTotal(records);
		
		return srd;
	}

	@Override
	public SearchResult<Product> searchAndCount(Search search) {
		
		return productDao.searchAndCount(search);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MessageDto update(ProductDto pd) {
		
		MessageDto md = new MessageDto();
		
		Timestamp now = new Timestamp(System.currentTimeMillis());
		int pcId = pd.getProductCatalogId();
		ProductCatalog pc = productCatalogService.findById(pcId);
		int unitId = pd.getUnitId();
		Unit unit = unitService.findById(unitId);
		int pId = pd.getId();
		Product p = null;
		if(pId==0){
			
			p = new Product();
			p.setCreateTime(now);
		} else {
			
			p = findById(pId);
			
			if(p!=null)
				
				p.setUpdateTime(now);
		}
		
		if(p!=null){
			
			p.setName(pd.getName());
			p.setEname(pd.getEname());
			p.setCode(pd.getCode());
			try{
				
				p.setStandardPrice(Double.parseDouble(pd.getStandardPrice()));
			}catch(NumberFormatException e){
				
				p.setStandardPrice(0.0);
			}
			
			p.setRemarks(pd.getRemarks());
			p.setProductCatalog(pc);
			p.setUnit(unit);
			
			boolean isT = save(p);
			if(isT){
				
				/*产品默认组合为其本身*/
				List<ProductConfiguration> prodctMix 
					= new ArrayList<ProductConfiguration>();
				ProductConfiguration pcfg = new ProductConfiguration();
				pcfg.setFproduct(p);
				pcfg.setSproduct(p);
				pcfg.setCreateTime(now);
				p.setProductMix(prodctMix);
				save(p);
				
				md.setIntF(p.getId());
				md.setT(true);
				md.setMessage("产品添加成功！");
			} else {
				
				md.setT(true);
				md.setMessage("产品修改成功！");
			}
			
		} else {
			
			md.setT(false);
			md.setMessage("产品找不到！");
			
		}
		
		return md;
	}

	@Override
	public Product findById(int pId) {
		
		return productDao.find(pId);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MessageDto delete(ProductDto pd) {

		Timestamp now = new Timestamp(System.currentTimeMillis());
		MessageDto md = new MessageDto();
		
		String ids = pd.getIds();
		
		if(ids!=null){
			
			String[] arrs = ids.split(",");
			
			for(String i : arrs){
				
				Product c = findById(Integer.valueOf(i));
				if(c!=null){
					
					c.setIsDelete(0);
					c.setUpdateTime(now);
					
					save(c);
					md.setT(true);
					md.setMessage("产品删除成功！");
					
				} else {
					
					md.setT(false);
					md.setMessage("产品找不到！");
				}
			}
		}
		
		return md;
	}

	@Override
	public String getProductsByCatalogId(int catalogId) {
		
		String result = "";
		
		if(catalogId!=0){
			
			ProductCatalog pc = productCatalogService.findById(catalogId);
			if(pc!=null){
				
				Search search = new Search();
				search.addFilterEqual("isDelete", 1);
				search.addFilterEqual("productCatalog", pc);
				
				List<Product> ps = productDao.search(search);
				result = DataUtil.productToComboboxResult(ps);
			}
		}
		
		return result;
	}

	@Override
	public ProductDto findById(ProductDto pd) {
		
		int id = pd.getId();
		Product p = findById(id);
		ProductDto rpd = DataUtil.convertProductToDto(p);
		return rpd;
	}

	@Override
	public String searchByName(String q) {
		
		Search search = new Search();
		search.addFilterEqual("isDelete", 1);
		search.addFilterLike("name", "%" + q + "%");
		SearchResult<Product> result = searchAndCount(search);
		List<Product> pls = result.getResult();
		if(pls.size()==0){
			
			Product newP = new Product();
			newP.setId(-1);
			newP.setName("查不到产品，请添加(点击)!");
			pls.add(newP);
		}
		String str = DataUtil.productToComboSearchDto(pls);
		
		return str;
	}

	@Override
	public List<Product> findByIds(String[] productIds) {
		
		List<Product> products = new ArrayList<Product>();
		
		if(productIds!=null&&productIds.length!=0){
			
			int len = productIds.length;
			Integer[] obj = new Integer[len];
			
			for(int i=0;i<len;i++){
				
				System.out.println("id:" + productIds[i]);
				obj[i] = Integer.valueOf(productIds[i]);
			}
			
			Product[] ps = productDao.find(obj);
			products.addAll(Arrays.asList(ps));
		}
		
		return products;
	}

	@Override
	public List<Product> findByTask(Task task) {
		
		String hql = "select p from Product as p join p.tasks as t where p.isDelete=1 and t= :tk "; 
		
		return productDao.loadProducts(hql, task);
	}

	@Override
	public List<Product> findByItem(Item i) {
		
		String hql = "select p from Product as p join p.items as i where p.isDelete=1 and i= :tk "; 
		
		return productDao.loadProducts(hql, i);
	}
}
