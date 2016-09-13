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
		
		$('#t_addTask').click(function(){
			
			parent.addPanel('addtask','添加项目','<%=basePath%>win/addtask.do');
		});
		
		
		$('#t_editTask').click(function(){
			
			var row = $('#"taskgrid"').datagrid("getSelected");
			if(row!=null){
				var taskId = row.id;
				parent.addPanel('addtask','添加项目','<%=basePath%>win/modifytask.do?taskId='+taskId);
				
			} else {
				
				$.messager.alert('注意', '请选择行数据!',
				'info');
			}
			
		});
		
		$('#t_removeTask').click(function(){
			
			var row = $('#taskgrid').datagrid("getSelected");
			if(row){
				
				 $.messager.confirm('Confirm','您确定删除:"'+ row.name+'"的信息吗?',function(r){
					 
					    if (r){
					    	
					    	 var rowIndex = $('#taskgrid').datagrid('getRowIndex', row);
							 $('#taskgrid').datagrid('deleteRow',rowIndex);
							 
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
	<div>
		<table id="taskgrid" cellspacing="0" cellpadding="0" 
			class="easyui-datagrid"
			data-options="
						url:'<%=basePath%>task/list.do',
						loadMsg:'数据加载中请稍后……',  
						title:'任务列表',
						rownumbers:true,
						singleSelect:true,
						collapsible:false,
						autoRowHeight:true,
						autoRowWidth:true,
						pagination:true,
						pageNumber:1,
						pageSize:20,
						sortOrder:'desc',
			    		toolbar: '#tb',
			    		onDblClickRow:function(index,row){
						
							var taskId = row.id;
							
							parent.addPanel('taskdetail','项目详情','<%=basePath%>win/taskdetail.do?taskId=' + taskId);
						}
					">
			<thead>
				<tr>
					<th data-options="field:'id',width:30,hidden:true">序号</th>
					<th data-options="field:'code',width:150,align:'center'">项目编码</th>
					<th data-options="field:'taskTypeId',hidden:true"></th>
					<th data-options="field:'taskType',width:80,align:'center'">任务类型</th>
					<th data-options="field:'name',width:280,align:'center'">内容</th>
					<th data-options="field:'customerId',hidden:true"></th>
					<th data-options="field:'customer',width:200,align:'center'">客户</th>
					<th data-options="field:'userId',hidden:true"></th>
					<th data-options="field:'createUser',width:60,align:'center'">创建用户</th>
					<th data-options="field:'createTime',width:100,align:'center'">创建时间</th>
					<th data-options="field:'updateUserId',hidden:true"></th>
					<th data-options="field:'updateUser',width:80,align:'center'">跟进者</th>
					<th data-options="field:'updateTime',width:100,align:'center'">跟进时间</th>
					<th data-options="field:'itemNum',width:50,align:'center'">任务数</th>
					<th data-options="field:'statusStr',width:50,align:'center'">状态</th>
					<th data-options="field:'remark',width:80,align:'center'">备注</th>
				</tr>
			</thead>
		</table>
	
		<div id="tb"
			style="border-bottom: 1px solid #ddd; height: 30px; padding: 3px 10px 0px 5px; background: #fafafa;">
			<div style="float: left;">
				<a id="t_addTask" href="#" class="easyui-linkbutton" plain="true"
					icon="icon-add">新增</a>
			</div>
	
			<div style="float: left;">
				<a id="t_editTask" href="#" class="easyui-linkbutton" plain="true"
					icon="icon-save">编辑</a>
			</div>
	
			<div style="float: left;">
				<a id="t_deleteTask" href="#" class="easyui-linkbutton" plain="true"
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