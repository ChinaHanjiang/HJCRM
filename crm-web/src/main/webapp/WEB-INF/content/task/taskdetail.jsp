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
		
		$('#i_addTask').click(function(){
			
			var id = $('#taskId').val();
			
			 var newObj = {
					 
					 id:'additem',
					 title:'添加任务',
					 href:'<%=basePath%>win/additem.do?taskId='+id
			 };
			 
			 parent.openPanel(newObj);
		});
		
		
		$('#i_editTask').click(function(){
			
			var row = $('#"itemgrid"').datagrid("getSelected");
			if(row!=null){
				
				
			} else {
				
				$.messager.alert('注意', '请选择行数据!',
				'info');
			}
			
		});
		
		$('#i_removeTask').click(function(){
			
			var row = $('#itemgrid').datagrid("getSelected");
			if(row){
				
				 $.messager.confirm('Confirm','您确定删除:"'+ row.name+'"的信息吗?',function(r){
					 
					    if (r){
					    	
					    	 var rowIndex = $('#taskgrid').datagrid('getRowIndex', row);
							 $('#itemgrid').datagrid('deleteRow',rowIndex);
							 
							 var id = row.id;
							 
							 $.ajax({
								 
								type: "POST",
								url: "<%=basePath%>task/del.do?td.id=" + id,
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
	});
	</script>
<title>任务列表</title>
</head>
<body>
<!-- 事件列表 -->
<div class="easyui-panel" data-options="border:true" title="项目详情"
		style="width: 720px; padding: 10px 60px 20px 60px">
		<input style="display:none;" type="text" name="taskId" id="taskId" value="${task.id }" />
		<table
			style="width: 650px; margin-right: auto; margin-left: auto; font-family: '宋体'; font-size: 13px;">
			<tr>
				<td style="width: 80px; height: 20px;">项目编号：</td>
				<td style="width: 180px; height: 20px;">${task.code }</td>
				<td style="width: 80px; height: 20px;">项目类型：</td>
				<td>${task.taskType.name }</td>
			</tr>
			<tr>
				<td style="width: 80px; height: 20px;">客户名称：</td>
				<td style="width: 180px; height: 20px;">${task.customer.name }</td>
				<td style="width: 80px; height: 20px;">创建时间：</td>
				<td>${task.createTime }</td>
			</tr>
			<tr>
				<td style="width: 110px; height: 25px;">内  容：</td>
				<td colspan="3">${task.name }</td>
			</tr>
			<s:if test="products!=null">
				<%int i=0; %>
				<s:iterator value="products" var="p">
			<tr>
				<%if(i==0){ %>
					<td style="width: 110px; height: 25px;">产  品：</td>
				<%} else { %>
					<td style="width: 110px; height: 25px;"></td>
				<%} %>
					<td colspan="3">${p.name }</td>
			</tr>
				<% i++; %>
				</s:iterator>
			</s:if>
			<tr>
				<td style="width: 80px; height: 20px;">创建者：</td>
				<td style="width: 180px; height: 20px;">${task.createUser.name }</td>
				<td style="width: 80px; height: 20px;">跟进者：</td>
				<td>${task.updateUser.name }</td>
			</tr>
			<tr>
				<td style="width: 110px; height: 25px;">备  注：</td>
				<td colspan="3">${task.remarks }</td>
			</tr>
		</table>
	</div>
	<div style="padding-top: 10px;">
		
		<table id="contactgrid" class="easyui-datagrid"
			data-options="
				url:'<%=basePath%>contact/list.do?customerId = <s:if test="task.customer==null">0</s:if><s:else>${task.customer.id }</s:else>',
				loadMsg:'数据加载中请稍后……',  
				title:'相关联系人',
				width:720,
				rownumbers:true,
				collapsible:false,
				autoRowHeight:true,
				autoRowWidth:true
				">
			<thead>
				<tr>
					<th data-options="field:'id',hidden:true">序号</th>
					<th data-options="field:'customerId',hidden:true">公司ID</th>
					<th data-options="field:'name',width:150">姓名</th>
					<th data-options="field:'sexId',hidden:true">性别</th>
					<th data-options="field:'sex',width:50">性别</th>
					<th data-options="field:'phone',width:120">手机</th>
					<th data-options="field:'email',width:150">邮箱</th>
					<th data-options="field:'duty',width:80">职务</th>
					<th data-options="field:'remarks',width:120">备注</th>
				</tr>
			</thead>
		</table>
	</div>
	<div style="padding-top: 10px;">
		<table id="itemgrid" cellspacing="0" cellpadding="0"
			class="easyui-datagrid"
			data-options="
						url:'<%=basePath%>item/list.do?td.id=${task.id }',
						loadMsg:'数据加载中请稍后……',  
						title:'事件列表',
						rownumbers:true,
						singleSelect:true,
						collapsible:false,
						autoRowHeight:true,
						autoRowWidth:true,
						pagination:true,
						pageNumber:1,
						pageSize:10,
						sortOrder:'desc',
						toolbar: '#tb' 
					">
			<thead>
				<tr>
					<th data-options="field:'id',width:30,hidden:true">序号</th>
					<th data-options="field:'code',width:150,align:'center'">编码</th>
					<th data-options="field:'itemType',width:80,align:'center'">任务类型</th>
					<th data-options="field:'customerId',hidden:true"></th>
					<th data-options="field:'customer',width:200,align:'center'">客户</th>
					<th data-options="field:'name',width:250,align:'center'">内容</th>
					<th data-options="field:'contactId',hidden:true"></th>
					<th data-options="field:'contact',width:100,align:'center'">联系人</th>
					<th data-options="field:'userId',hidden:true"></th>
					<th data-options="field:'user',width:80,align:'center'">跟进者</th>
					<th data-options="field:'createTime',width:100,align:'center'">创建时间</th>
					<th data-options="field:'updateTime',width:100,align:'center'">修改时间</th>
					<th data-options="field:'statusStr',width:50,align:'center'">状态</th>
					<th data-options="field:'remark',width:60,align:'center'">备注</th>
				</tr>
			</thead>
		</table>
		<div id="tb"
			style="border-bottom: 1px solid #ddd; height: 30px; padding: 3px 10px 0px 5px; background: #fafafa;">
			<div style="float: left;">
				<a id="i_addTask" href="#" class="easyui-linkbutton" plain="true"
					icon="icon-add">新增</a>
			</div>
	
			<div style="float: left;">
				<a id="i_editTask" href="#" class="easyui-linkbutton" plain="true"
					icon="icon-save">编辑</a>
			</div>
	
			<div style="float: left;">
				<a id="i_deleteTask" href="#" class="easyui-linkbutton" plain="true"
					icon="icon-remove">删除</a>
			</div>
		</div>
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