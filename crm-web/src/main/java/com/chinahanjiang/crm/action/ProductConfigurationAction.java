package com.chinahanjiang.crm.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.stereotype.Controller;

import com.chinahanjiang.crm.dto.MessageDto;
import com.chinahanjiang.crm.dto.ProductConfigurationDto;
import com.chinahanjiang.crm.service.ProductConfigurationService;

@Controller
@ParentPackage("ajaxdefault")
@Namespace("/pcf")
@Results({ @Result(name = "error", location = "/error.jsp"),
		@Result(name = "list", type = "json"),
		@Result(name = "savemix", type = "json") })
@ExceptionMappings({ @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class ProductConfigurationAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	@Resource
	private ProductConfigurationService productConfigurationService;

	private List<Object> rows;

	private int total;

	private int page;

	private String sort;

	private String order;

	private String inserted;

	private String updated;

	private String deleted;

	private int productId;

	private MessageDto md;

	public MessageDto getMd() {
		return md;
	}

	public void setMd(MessageDto md) {
		this.md = md;
	}

	public String getInserted() {
		return inserted;
	}

	public void setInserted(String inserted) {
		this.inserted = inserted;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	private ProductConfigurationDto pcfd;

	public ProductConfigurationDto getPcfd() {
		return pcfd;
	}

	public void setPcfd(ProductConfigurationDto pcfd) {
		this.pcfd = pcfd;
	}

	public List<Object> getRows() {
		return rows;
	}

	public void setRows(List<Object> rows) {
		this.rows = rows;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	@Action("list")
	public String list() {

		List<ProductConfigurationDto> cds = productConfigurationService
				.loadProductMixs(pcfd);

		this.rows = new ArrayList<Object>();

		this.rows.clear();
		this.rows.addAll(cds);
		this.total = cds.size();

		return "list";
	}

	@Action("savemix")
	public String savemix() {
		
		md = productConfigurationService.saveProductMix(this.productId,
				this.inserted, this.updated, this.deleted);

		return "savemix";
	}
}
