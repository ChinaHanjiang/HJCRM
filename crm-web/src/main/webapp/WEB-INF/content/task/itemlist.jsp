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
	
</script>
<title>任务列表</title>
</head>
<body>
	<div style="padding-top: 10px;">
		<table id="itemgrid" cellspacing="0" cellpadding="0"
			class="easyui-datagrid"
			data-options="
						url:'<%=basePath%>item/list.do',
						loadMsg:'数据加载中请稍后……',  
						title:'事件列表',
						rownumbers:true,
						singleSelect:true,
						collapsible:false,
						autoRowHeight:true,
						autoRowWidth:true,
						pagination:true,
						pageNumber:1,
						pageSize:20,
						sortOrder:'desc',
						toolbar: '#tb' 
					">
			<thead>
				<tr>
					<th data-options="field:'id',width:30,hidden:true">序号</th>
					<th data-options="field:'code',width:150,align:'center'">编码</th>
					<th data-options="field:'task',width:150,align:'center'">任务编码</th>
					<th data-options="field:'customerId',hidden:true"></th>
					<th data-options="field:'customer',width:200,align:'center'">客户</th>
					<th data-options="field:'name',width:250,align:'center'">内容</th>
					<th data-options="field:'contactId',hidden:true"></th>
					<th data-options="field:'contnact',width:100,align:'center'">联系人</th>
					<th data-options="field:'userId',hidden:true"></th>
					<th data-options="field:'user',width:80,align:'center'">跟进者</th>
					<th data-options="field:'createTime',width:100,align:'center'">创建时间</th>
					<th data-options="field:'updateTime',width:100,align:'center'">修改时间</th>
					<th data-options="field:'statusStr',width:50,align:'center'">状态</th>
					<th data-options="field:'remark',width:60,align:'center'">备注</th>
				</tr>
			</thead>
		</table>
	</div>
	<script type="text/javascript">
	
		function closeDiv(id){
			
			$('#pdiv' + id).remove();
		}
		
		function addCustomer(){
			
			parent.addPanel('customerlist','客户管理','<%=basePath%>win/customerlist.do');
		}
		
		function addProduct(){
			
			parent.addPanel('productsManager','产品管理','<%=basePath%>win/productlist.do');
		}
	</script>
</body>

</html>