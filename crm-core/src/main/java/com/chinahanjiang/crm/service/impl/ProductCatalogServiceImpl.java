package com.chinahanjiang.crm.service.impl;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.NonUniqueObjectException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.chinahanjiang.crm.dao.ProductCatalogDao;
import com.chinahanjiang.crm.dto.MessageDto;
import com.chinahanjiang.crm.dto.ProductCatalogDto;
import com.chinahanjiang.crm.dto.SearchResultDto;
import com.chinahanjiang.crm.pojo.ProductCatalog;
import com.chinahanjiang.crm.service.ProductCatalogService;
import com.chinahanjiang.crm.util.DataUtil;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

@Service("productCatalogService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class ProductCatalogServiceImpl implements ProductCatalogService {

	@Resource
	private ProductCatalogDao productCatalogDao;
	
	@Override
	public ProductCatalog findProductCatalogByCode(String pCode) throws NonUniqueObjectException {
		
		Search search = new Search();
		search.addFilterEqual("code", pCode);
		search.addFilterEqual("isDelete", 1);
		ProductCatalog pc = null;
		try{
			
			pc = productCatalogDao.searchUnique(search);
			
		}catch(NonUniqueObjectException e){
			
			throw e;
		}
		
		return pc;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean save(ProductCatalog pc) {
		
		return productCatalogDao.save(pc);
	}

	@Override
	public ProductCatalog findProductCatalogByCode(String pCode,
			ProductCatalog ppPc) {
		
		Search search = new Search();
		search.addFilterEqual("code", pCode);
		search.addFilterEqual("parentCatalog", ppPc);
		search.addFilterEqual("isDelete", 1);
		ProductCatalog pc = null;
		try{
			
			pc = productCatalogDao.searchUnique(search);
			
		}catch(NonUniqueObjectException e){
			
			e.printStackTrace();
		}
		
		return pc;
	}

	@Override
	public String getAllCatalogs() {
		
		ProductCatalog pc = findProductCatalogByCode("HJP");
		String pcTree = DataUtil.productCatalogToJson(pc);
		return pcTree;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MessageDto update(ProductCatalogDto pcd) {
		
		MessageDto md = new MessageDto();
		Timestamp now = new Timestamp(System.currentTimeMillis());
		ProductCatalog productCatalog = null;
		
		ProductCatalog pProductCatalog = findById(pcd.getParentId());
		if(pProductCatalog==null){
			
			md.setMessage("上级产品类型信息不存在！");
			md.setT(false);
			return md;
		} else {
			
			pProductCatalog.setState("closed");
		}
		
		int id = pcd.getId();
		if(id == 0){
			
			productCatalog = new ProductCatalog();
			productCatalog.setCreateTime(now);
			md.setMessage("产品类型信息添加成功！");
			
		} else {
			
			productCatalog = findById(id);
			
			if(productCatalog != null)
				productCatalog.setUpdateTime(now);
			
			md.setMessage("产品类型信息修改成功！");
		}
		
		if(productCatalog != null){
			
			productCatalog.setName(pcd.getName());
			productCatalog.setEname(pcd.getEname());
			productCatalog.setCode(pcd.getCode());
			
			productCatalog.setParentCatalog(pProductCatalog);
			
			save(productCatalog);
			
			md.setIntF(productCatalog.getId());
			md.setT(true);
			
		} else {
			
			md.setT(false);
			md.setMessage("产品类型在数据库不存在");
		}
		
		return md;
	}

	@Override
	public ProductCatalog findById(int parentId) {
		
		return productCatalogDao.find(parentId);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MessageDto delete(ProductCatalogDto pcd) {
		
		MessageDto md = new MessageDto();
		ProductCatalog productCatalog = new ProductCatalog();
		
		String ids = pcd.getIds();
		String[] arrs = ids.split(",");
		if(arrs.length == 0){
			
			md.setMessage("删除产品类型信息不全，删除失败!");
			md.setT(false);
			return md;
			
		}else {
			
			for(String i : arrs){
				
				int id = Integer.valueOf(i);
				productCatalog = findById(id);
				if(productCatalog!=null){
					
					delete(productCatalog);
					
					/*需要看父类别还有没子类别,需不需要把closed去掉*/
				}
			}

			md.setMessage("删除产品类型成功！");
			md.setT(true);
		
			return md;
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(ProductCatalog pc){
		
		Timestamp now = new Timestamp(System.currentTimeMillis());
		
		if(pc!=null){
			
			List<ProductCatalog> childPcs = pc.getChildPcs();
			if(childPcs!=null){
				
				Iterator<ProductCatalog> it = childPcs.iterator();
				while(it.hasNext()){
					
					ProductCatalog cpc = it.next();
					delete(cpc);
				}
			}
			
			pc.setIsDelete(0);
			pc.setUpdateTime(now);
			
			save(pc);
		}
	}
	
	@Override
	public MessageDto check(ProductCatalogDto pcd) {
		
		MessageDto md = new MessageDto();
		String name = pcd.getName();
		String code = pcd.getCode();
		ProductCatalog pProductCatalog = null;
		String message = "";
		
		int parentId = pcd.getParentId();
		if(parentId!=0){
			
			pProductCatalog = findById(parentId);
		}
		
		if(pProductCatalog == null){
			
			md.setT(false);
			md.setMessage("父产品类型不存在！");
			
		} else {
			
			Search search = new Search();
			search.addFilterEqual("parentCatalog", pProductCatalog);
			search.addFilterEqual("isDelete", 1);
			if(name!=null && !name.equals("")){
				
				search.addFilterEqual("name", name);
				message = "产品类型名称数据库中存在，请重新确认！";
			}
				
			if(code!=null && !code.equals("")){
				
				search.addFilterEqual("code", code);
				message = "产品类型编码数据库中存在，请重新确认！";
			}
			
			ProductCatalog productCatalog = null;
			try{
				productCatalog = productCatalogDao.searchUnique(search);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			if(productCatalog==null){
				
				md.setT(true);
				md.setMessage("在数据库中不存在，可以录入！");
				
			} else {
				
				md.setT(false);
				md.setMessage(message);
			}
		}
		
		return md;
	}

	@Override
	public String getCatalogsByParentId(int id) {
		
		ProductCatalog pc;
		if(id != 0){
			
			pc = findById(id);
			
		} else {
			
			pc = findProductCatalogByCode("HJP");
		}
		
		return generateStrForTree(pc);
	}
	
	private String generateStrForTree(ProductCatalog pc){
		
		String str = "";
		Search search = new Search();
		search.addFilterEqual("isDelete", 1);
		search.addFilterEqual("parentCatalog", pc);
		List<ProductCatalog> pcs = productCatalogDao.search(search);
		str = DataUtil.productCatalogToJson(pcs);
		return str;
	}

	@Override
	public String initCatalogTree(ProductCatalogDto pcd) {
		
		ProductCatalog pc = findProductCatalogByCode("HJP");
		return generateStrForTree(pc);
		
	}

	@Override
	public ProductCatalog findProductCatalogByCodeAndName(String pcCode,
			String catalog) {
		ProductCatalog c = null;
		ProductCatalog pc = findProductCatalogByCode(pcCode);
		if(pc!=null){
			
			Search search = new Search();
			search.addFilterEqual("isDelete", 1);
			search.addFilterEqual("parentCatalog", pc);
			search.addFilterEqual("name", catalog);
			
			c = productCatalogDao.searchUnique(search);
		}
		return c;
	}

	@Override
	public SearchResultDto searchAndCount(int id, String order,
			String sort, int page, int row) {
		
		ProductCatalog pc;
		if(id != 0){
			
			pc = findById(id);
			
		} else {
			
			pc = findProductCatalogByCode("HJP");
		
		}
		
		Search search = new Search();
		search.addFilterEqual("isDelete", 1);
		if(pc!=null){
			
			search.addFilterEqual("parentCatalog", pc);
		}
		
		search.setMaxResults(row);
		search.setPage(page - 1 < 0 ? 0 : page - 1);
		SearchResult<ProductCatalog> result = searchAndCount(search);
		List<ProductCatalog> pcs = result.getResult();

		List<ProductCatalogDto> pcds = DataUtil.convertProductCatalogToProductCatalogDto(pcs);
		int records = result.getTotalCount();

		SearchResultDto srd = new SearchResultDto();
		srd.getRows().clear();
		srd.getRows().addAll(pcds);
		srd.setTotal(records);

		return srd;
	}

	private SearchResult<ProductCatalog> searchAndCount(Search search) {
		
		return productCatalogDao.searchAndCount(search);
	}

}
