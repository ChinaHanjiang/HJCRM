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
	src="<%=basePath%>js/jquery.iframeWin.js"></script>
<script type="text/javascript"
	src="<%=basePath%>locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">

	$(document).ready(function(){
		
		$('#q_addQuote').click(function(){
			
			$.iframe.win({
				title:"打开网页",
				url:'<%=basePath%>win/quotewindow.do',
				width: 800,
				height: 600,
				modal: true,
				onClosed:function(){
					
					$('#quotegrid').datagrid('reload');
				}
			});
		});
	});
	
</script>
<title>报价列表</title>
</head>
<body>
	<table id="quotegrid" cellspacing="0" cellpadding="0"
		class="easyui-datagrid"
		data-options="
					url:'<%=basePath%>item/list.do', 
					title:'报价列表', 
	       			loadMsg:'正在加载数据，请稍后...',
	      			width: 1250, 
	       			height: 'auto', 
	       			nowrap: false, 
	       			striped: false, 
	       			border: true, 
	       			rownumbers:true,
	       			collapsible:false,//是否可折叠的 
		   			singleSelect:true,//是否单选 
		   			pagination:true,//分页控件 
		   			pageSize:20,
		   			frozenColumns:[[ 
		        		{field:'ck',checkbox:true} 
		    		]], 
		    		toolbar: '#tb' 
				">
		<thead>
			<tr>
				<th data-options="field:'id',width:30,hidden:true">序号</th>
				<th data-options="field:'code',width:120,align:'center'">任务编码</th>
				<th data-options="field:'taskId',width:30,hidden:true"></th>
				<th data-options="field:'task',width:120,align:'center'">项目编码</th>
				<th data-options="field:'name',width:120,align:'center'">任务名称</th>
				<th data-options="field:'customerId',width:30,hidden:true"></th>
				<th data-options="field:'customer',width:120,align:'center'">客户</th>
				<th data-options="field:'contactId',width:30,hidden:true"></th>
				<th data-options="field:'contact',width:120,align:'center'">联系人</th>
				<th data-options="field:'status',width:120,align:'center'">是否报价</th>
				<th data-options="field:'userId',width:30,hidden:true"></th>
				<th data-options="field:'user',width:120,align:'center'">执行者</th>
				<th data-options="field:'createTime',width:180,align:'center'">创建时间</th>
				<th data-options="field:'updateTime',width:180,align:'center'">更新时间</th>
				<th data-options="field:'remarks',width:130,align:'center'">备注</th>
			</tr>
		</thead>
	</table>

	<div id="tb"
		style="border-bottom: 1px solid #ddd; height: 30px; padding: 3px 10px 0px 5px; background: #fafafa;">
		<div style="float: left;">
			<a id="q_addQuote" href="#" class="easyui-linkbutton" plain="true"
				icon="icon-add">报价</a>
		</div>

		<div style="float: left;">
			<a id="q_editQuote" href="#" class="easyui-linkbutton" plain="true"
				icon="icon-save">修改</a>
		</div>

		<div style="float: left;">
			<a id="q_deleteQuote" href="#" class="easyui-linkbutton" plain="true"
				icon="icon-remove">删除</a>
		</div>

		<div style="float: right; padding-left: 5px;">
			<input id="g_search" class="easyui-searchbox"
				data-options="prompt:'请输入查询员工姓名'" style="width: 150px;"></input>
		</div>
		
	</div>

	<!-- 添加集团信息窗口 -->
	<!-- 	
	<div id="openQuoteWin" class="easyui-window" title="报价编辑窗口"
		data-options="modal:true,closed:true,iconCls:'icon-save',minimizable:false,collapsible:false,maximizable:false"
		style="width: 450px; height: 260px; padding: 10px;">
		<div style="padding: 10px 60px 20px 60px">
			
			
		</div>
	</div>
	 -->
</body>

</html>