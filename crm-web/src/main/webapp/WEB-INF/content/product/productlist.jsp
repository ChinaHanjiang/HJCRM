<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
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
	
	$(document).ready(function(){
		
		$('#p_addGrid').click(function(){
			
			 var newObj = {
				 id:'producteidt',
				 title:'产品编辑',
				 href:'<%=basePath%>win/addproduct.do'
			 };
			 
			 parent.openAndClose(newObj,'产品列表');
		});
		
		$('#p_editGrid').click(function(){
			
			var row = $('#productgrid').datagrid("getSelections");
			if(row!=null && row.length==1){
				
				var id = row[0].id;
				
				var newObj = {
					 id:'producteidt',
					 title:'产品编辑',
					 href:'<%=basePath%>win/modifyproduct.do?productId='+id
				 };
					 
				parent.openAndClose(newObj,'产品列表');
			} else {
				
				$.messager.alert('注意', '请选择1行数据!',
				'info');
			}
			
		});
		
		$('#p_removeGrid').click(function(){
			
			 var row = $('#productgrid').datagrid("getSelections");
			 if(row!=null){
				 
				 $.messager.confirm('Confirm','您确定删除"'+ row.length+'"行信息吗?',function(r){
					 
					    if (r){
					    	
							var id = new Array();
					    	
					    	$.each(row,function(i, o){
							   
					    		 var rowIndex = $('#productgrid').datagrid('getRowIndex', o);
								 $('#productgrid').datagrid('deleteRow',rowIndex);
								 
								 id[i] = o.id;
							});
							 
							 $.ajax({
									type: "POST",
									url: "<%=basePath%>product/del.do?pd.ids=" + id,
									cache: false,
						        	dataType : "json",
						        	success:function(data){
						        		
						        		var _data =  data.md ;
						        		if(_data.t){
						        			$.messager.alert('成功',_data.message,
											'info');
						        		}else{
						        			$.messager.alert('失败', _data.message,
											'info');
						        		}
						        	}
								});
					    }
				 });
				 
			 } else {
					
				$.messager.alert('注意', '请选择行数据!',
				'info');
			}
		});
		
		$('#p_catalog').combotree({
			url:'<%=basePath%>catalog/tree.do',
			method:'get',
			onSelect:function(record){
				
				var catalogId = record.id;
				var qp = $('#productgrid').datagrid('options').queryParams;
				qp.productCatalogId = catalogId;
				$("#productgrid").datagrid('reload');
			}
		});
		
		$('#search').searchbox({
			
			searcher:function(value,name){
				
				$('#productgrid').datagrid({
					queryParams:{
						name:value
					}
				});
			}
		});
	});
</script>
<title>产品列表</title>

</head>
<body>
	<div class="easyui-panel" data-options="border:false">
		<table id="productgrid" class="easyui-datagrid" title="产品信息列表"
			data-options="
					url:'<%=basePath%>product/list.do',
					loadMsg:'数据加载中请稍后……',  
					rownumbers:true,
					singleSelect:true,
					collapsible:false,
					autoRowHeight:true,
					autoRowWidth:true,
					pagination:true,
					pageNumber:1,
					pageSize:20,
					sortOrder:'desc',
					toolbar: '#p_tb' ,
					onDblClickRow:function(index,row){
					
						_p_id = row.id;
						
						var newObj = {
							 id:'productdetail',
							 title:'产品详情',
							 href:'<%=basePath%>win/productdetail.do?productId='+_p_id
						 };
						 
						 parent.openAndClose(newObj,'产品列表');
						
					}
					">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th data-options="field:'id',hidden:true">序号</th>
					<th data-options="field:'shortCode',hidden:true">短编码</th>
					<th data-options="field:'code',width:180">产品编码</th>
					<th data-options="field:'name',width:180">名称</th>
					<th data-options="field:'ename',width:180">英文名称</th>
					<th data-options="field:'unit',width:40">单位</th>
					<th data-options="field:'productCatalogId',hidden:true"></th>
					<th data-options="field:'productCatalog',width:120">产品类型</th>
					<th data-options="field:'standardPrice',width:80">标准价</th>
					<th data-options="field:'mixNum',width:40">组成</th>
					<th data-options="field:'telephone',width:60">创建者</th>
					<th data-options="field:'createTime',width:140">创建时间</th>
					<th data-options="field:'updateTime',width:140">修改时间</th>
					<th data-options="field:'remarks',width:40">备注</th>
				</tr>
			</thead>
		</table>
	</div>
	<div id="p_tb"
		style="border-bottom: 1px solid #ddd; height: 30px; padding: 3px 10px 0px 5px; background: #fafafa;">
		<div style="float: left;">
			<a id="p_addGrid" href="#" class="easyui-linkbutton" plain="true"
				icon="icon-add">新增</a>
		</div>

		<div style="float: left;">
			<a id="p_editGrid" href="#" class="easyui-linkbutton" plain="true"
				icon="icon-save">编辑</a>
		</div>

		<div style="float: left;">
			<a id="p_removeGrid" href="#" class="easyui-linkbutton" plain="true"
				icon="icon-remove">删除</a>
		</div>
		
		<div style="float: right; padding-right: 10px;">
				<input id="search" class="easyui-searchbox"
					data-options="prompt:'请输入查询名称'" style="width: 180px;"></input>
		</div>
			
		<div style="float: right; padding-right:10px;">
		产品类型：<input id="p_catalog" class="easyui-combotree" name="f_p_catalog"
						data-options="" style="width:180px;">
		</div>
	</div>
	
</body>
</html>