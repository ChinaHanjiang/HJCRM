<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%@ page import="com.chinahanjiang.crm.pojo.Product" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>themes/material/easyui.css">
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>themes/icon.css">
<script type="text/javascript" src="<%=basePath%>js/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>js/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	/*定义全局变量*/
	
	//产品
	var _p_id;
	var _p_name;
	var _p_ename;
	var _p_code;
	var _p_shortCode;
	var _p_standardPrice;
	var _p_remarks;
	var _p_catalogId;
	var _p_catalog;
	var _p_catalog;
	var _p_userId;
	var _p_unitId;
	
	var _beforeload_type = 0;
	
	$(document).ready(function(){
		
		<%
		Integer type = (Integer)request.getAttribute("type");
		
		if(type==2){
			
			Product product = (Product)request.getAttribute("product");
		%>
			
			$('#p_bmodify').show();
			$('#p_bsubmit').hide();
			$('#pu_name').textbox('setText','<%=product.getName()%>');
			$('#pu_code').textbox('setText','<%=product.getCode()%>');
			$('#pu_shortCode').textbox('setText','<%=product.getShortCode()%>');
			$('#pu_ename').textbox('setText','<%=product.getEname()%>');
			$('#pu_unit').combobox('setValue',<%=product.getUnit().getId()%>);
			$('#pu_price').textbox('setText','<%=product.getStandardPrice()%>');
			$('#pu_remarks').textbox('setText','<%=product.getRemarks()%>');
			
			_p_id = <%=product.getId()%>;
			_p_name = '<%=product.getName()%>';
			_p_ename = '<%=product.getEname()%>';
			_p_code = '<%=product.getCode()%>';
			_p_shortCode = '<%=product.getShortCode()%>';
			_p_standardPrice = <%=product.getStandardPrice()%>;
			_p_remarks ='<%=product.getRemarks()%>';
			_p_catalogId = <%=product.getProductCatalog().getId()%>;
			_p_catalog = '<%=product.getProductCatalog().getName()%>';
			_p_unitId = <%=product.getUnit().getId()%>;
			
			_beforeload_type = 1;
		<%
			} else {
				
		%>
		
			$('#p_bmodify').hide();
			$('#p_bsubmit').show();
		
		<%
			}
		%>
		
		$('#p_bsubmit').click(function(){
			
			var p_name = $('#pu_name').textbox('getText');
			var cp_name = p_name.replace('#','%23');
			
			var p_ename = $('#pu_ename').textbox('getText');
			var cp_ename = p_ename.replace('#','%23');
			
			var p_code = $('#pu_code').textbox('getText');
			var cp_code = p_code.replace('#','%23');
			
			var p_shortCode = $('#pu_shortCode').textbox('getText');
			var cp_shortCode = p_shortCode.replace('#','%23');
			
			var p_standardPrice = $('#pu_price').textbox('getText');
			var p_remarks = $('#pu_remarks').textbox('getText');
			var node = $('#pu_catalog').combotree('getSelected');
			var p_catalogId = node.id;
			
			var p_unitId = $('#pu_unit').combobox('getValue');
			
			var l = $('#p_addWinForm').form('enableValidation').form('validate');
			if(l){
				
				var str = "";
				str += "pd.name=" + cp_name;
				str += "&pd.code=" + cp_code;
				str += "&pd.shortCode=" + cp_shortCode;
				str += "&pd.productCatalogId=" + p_catalogId;
				str += "&pd.standardPrice=" + p_standardPrice;
				str += "&pd.remarks=" + p_remarks;
				str += "&pd.ename=" + cp_ename;
				str += "&pd.unitId=" + cp_unitId;
				
				$.ajax({
					 type:"POST",
		    		 url:"<%=basePath%>product/add.do?" + str,
		    		 cache:false,
		    		 dataType:'json',
		    		 success:function(data){
		    			 
		    			 var _data = data.md;
		    			 
		    			 if(_data.t){
		    				 
		    				 var id = _data.intF;
		    				 
		    				 /* $.messager.alert('成功', _data.message,
								'info'); */
							/*成功，跳转到"产品详情"页面*/
							
		    				 var newObj = {
		    						 id:'productdetail',
		    						 title:'产品详情',
		    						 href:'<%=basePath%>win/productdetail.do?productId='+id
		    					 };
		    						 
		    					parent.openAndClose(newObj,'产品编辑');
							
		    				 
		    			 } else {
		    				 
		    				 $.messager.alert('失败', _data.message,
								'info');
		    			 }
		    		 }
				});
			} else {
				$.messager.alert('注意', '请填完整的表单！',
				'info');
			}
			
		});
		
		$('#p_bmodify').click(function(){
			
			var p_name = $('#pu_name').textbox('getText');
			var cp_name = p_name.replace('#','%23');
			
			var p_ename = $('#pu_ename').textbox('getText');
			var cp_ename = p_ename.replace('#','%23');
			
			var p_code = $('#pu_code').textbox('getText');
			var cp_code = p_code.replace('#','%23');
			
			var p_shortCode = $('#pu_shortCode').textbox('getText');
			var cp_shortCode = p_shortCode.replace('#','%23');
			
			var p_standardPrice = $('#pu_price').textbox('getText');
			var p_remarks = $('#pu_remarks').textbox('getText');
			var p_catalog = $('#pu_catalog').combotree('getText');
			var p_catalogId;
			
			var p_unitId = $('#pu_unit').combobox('getValue');
			
			if(p_catalog != _p_catalog){
				
				var node = $('#pu_catalog').combotree('getSelected');
				alert(node);
				p_catalogId = node.id;
				
			} else {
				
				p_catalogId = _p_catalogId;
			}
			
			
			var str = '';
			var isChanged = false;
			
			var l = $('#p_addWinForm').form('enableValidation').form('validate');
			if(l){
				
				str += "pd.id=" + _p_id
				str += "&pd.name=" + cp_name;
				str += "&pd.ename=" + cp_ename;
				str += "&pd.code=" + cp_code;
				str += "&pd.shortCode=" + cp_shortCode;
				str += "&pd.productCatalogId=" + p_catalogId;
				str += "&pd.standardPrice=" + p_standardPrice;
				str += "&pd.unitId=" + p_unitId;
				str += "&pd.remarks=" + p_remarks;
				
				if(_p_name != p_name){
					
					isChanged = true;
				}
				if(_p_ename != p_ename){
					
					isChanged = true;
				}
				if(_p_code != p_code){
					
					isChanged = true;
				}
				if(_p_shortCode != p_shortCode){
					
					isChanged = true;
				}
				if(_p_standardPrice != p_standardPrice){
					
					isChanged = true;
				}
				if(_p_remarks != p_remarks){
					
					isChanged = true;
				}
				if(p_unitId != _p_unitId){
					
					isChanged = true;
				}
				if(p_catalog != _p_catalog){
					
					isChanged = true;
				}
				
				if(isChanged){
					
					$.ajax({
						 type:"POST",
			    		 url:"<%=basePath%>product/modify.do?" + str,
			    		 cache:false,
			    		 dataType:'json',
			    		 success:function(data){
			    			 
			    			 var _data = data.md;
			    			 if(_data.t){
			    				 
			    				 var id = _data.intF;
			    				 
			    				 /* $.messager.alert('成功', _data.message,
									'info'); */
								/*成功，跳转到"产品详情"页面*/
								
			    				 var newObj = {
			    						 id:'productdetail',
			    						 title:'产品详情',
			    						 href:'<%=basePath%>win/productdetail.do?productId='+id
			    					 };
			    						 
			    					parent.openAndClose(newObj,'产品编辑');
			    				 
			    			 } else {
			    				 
			    				 $.messager.alert('失败', _data.message,
									'info');
			    			 }
			    		 }
					});
					
				} else {
					
					$.messager.alert('注意', '产品你信息没有改变！',
					'info');
				}
				
			} else {
				$.messager.alert('注意', '请填完整的表单！',
				'info');
			}
		});
		
		$('#p_bcancel').click(function(){
			
			parent.closePanel('产品编辑');
		});
		
		$('#pu_catalog').combotree({
			url:'<%=basePath%>catalog/tree.do',
			method:'get',
			editable:true,
			onLoadSuccess:function(){
				if(_beforeload_type == 1){
					
					$('#pu_catalog').combotree('setText',_p_catalog);
					_beforeload_type = 0;
				}
			}
		});
		
	});
</script>
<title>类型列表</title>

</head>
<body>
	<!-- 添加产品窗口 -->
	<div id="p_addWin" class="easyui-panel" title="产品编辑窗口"
		data-options=""	style="width: 450px; height: 500px; padding: 10px;">
		<div style="padding: 10px 60px 20px 60px">
			<form id="p_addWinForm" method="post">
				<table cellpadding="5">
					<tr id="p_name">
						<td>产品名称:</td>
						<td><input id="pu_name" class="easyui-textbox"
							style="width: 200px" type="text" name="f_p_name"
							data-options="editable:true,required:true"></input></td>
					</tr>
					<tr id="p_ename">
						<td>产品名称:</td>
						<td><input id="pu_ename" class="easyui-textbox"
							style="width: 200px" type="text" name="f_p_ename"
							data-options="editable:true,required:true"></input></td>
					</tr>
					<tr id="p_catalog">
						<td>产品类型:</td>
						<td>
							<input id="pu_catalog" class="easyui-combotree" name="f_p_catalog" 
							data-options="" style="width:100%">
						</td>
					</tr>
					<tr id="p_shotcode">
						<td>短编码:</td>
						<td><input id="pu_shortCode" class="easyui-textbox"
							style="width: 200px" type="text" name="f_p_shortCode"
							data-options="editable:true"></input></td>
					</tr>
					<tr id="p_code">
						<td>产品编码:</td>
						<td><input id="pu_code" class="easyui-textbox"
							style="width: 200px" type="text" name="f_p_code"
							data-options="editable:false"></input></td>
					</tr>
					<tr>
						<td>单位:</td>
						<td><select id="pu_unit" class="easyui-combobox" data-options="panelHeight:'auto'"
							style="width: 200px" name="f_pu_unit">
								<option value=0 selected="selected">请选择</option>
								<s:if test="units!=null">
									<s:iterator value="units" var="u">
										<option value=${u.id }>${u.name }</option>
									</s:iterator>
								</s:if>
						</select></td>
					</tr>
					<tr id="p_price">
						<td>标准价</td>
						<td><input id="pu_price" class="easyui-textbox"
							style="width: 200px" type="text" name="f_p_price"
							data-options="editable:true"></input></td>
					</tr>
					<tr id="c_remarks">
						<td>备注:</td>
						<td>
						<input id="pu_remarks" class="easyui-textbox"
							style="width: 200px; height:60px;" type="text" name="f_p_remarks"
							data-options="editable:true,multiline:true"></input>
					</tr>

				</table>
			</form>
			<div style="text-align: center; padding: 5px">
				<a id="p_bsubmit" href="javascript:void(0)" style="width: 80px;"
					class="easyui-linkbutton">提交</a> <a id="p_bmodify"
					href="javascript:void(0)" style="width: 80px;"
					class="easyui-linkbutton">确认修改</a> <a id="p_bcancel"
					href="javascript:void(0)" style="width: 80px;"
					class="easyui-linkbutton">取消</a>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		
	</script>
</body>
</html>