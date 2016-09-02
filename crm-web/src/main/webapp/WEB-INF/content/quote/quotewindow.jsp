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
	<style type="text/css">
		html { overflow-x:hidden; }
	</style>
	<script type="text/javascript">
		
	</script>
	<title>报价列表</title>
</head>
<body >
	<div id="quotewindow" class="easyui-panel" data-options="border:false"
		style="padding: 10px;">
		<div class="easyui-panel" data-options="border:true"
			 style="width: 720px;  padding: 10px 60px 20px 60px">
			<form id="quotewindowform" method="post">
				<table cellpadding="5">
					<tr>
						<td>项目编码: GMSCX-20160830-002.1</td>
						<td>客户：奥瑞金包装股份有限公司昆明分公司</td>
					</tr>
					<tr>
						<td>任务编码:GMSCX-20160830-002</td>
						<td>联系人：吕总</td>
					</tr>
					
				</table>
			</form>
		</div>
		<div class="easyui-panel" data-options="border:false" 
			style="width:720px; padding-top: 10px;">
			<table id="quotelist" cellspacing="0" cellpadding="0"
				class="easyui-datagrid"
				data-options="
							url:'<%=basePath%>groups/list.do', 
							title:'产品:AAAAAA报价表', 
			       			loadMsg:'正在加载数据，请稍后...',
			       			height: 250, 
			       			nowrap: false, 
			       			striped: false, 
			       			border: true, 
			       			rownumbers:true,
			       			collapsible:true,//是否可折叠的 
				   			pageSize:10,
				    		toolbar: '#tb' 
						">
				<thead>
					<tr>
						<th data-options="field:'id',width:30,hidden:true">序号</th>
						<th data-options="field:'code',width:180,align:'center'">产品编码</th>
						<th data-options="field:'name',width:180,align:'center'">产品名称</th>
						<th data-options="field:'createTime',width:120,align:'center'">标准价</th>
						<th data-options="field:'updateTime',width:120,align:'center'">自定义价</th>
					</tr>
				</thead>
			</table>
			<div id="tb"
				style="border-bottom: 1px solid #ddd; height: 30px; padding: 3px 10px 0px 5px; background: #fafafa;">
				<div style="float: left;">
					<a id="pq_add" href="#" class="easyui-linkbutton" plain="true"
						icon="icon-add">新增</a>
				</div>
				<div style="float: left;">
					<a id="qp_remove" href="#" class="easyui-linkbutton" plain="true"
						icon="icon-remove">删除</a>
				</div>
				<div style="float: left;">
					<a id="qp_save" href="#" class="easyui-linkbutton" plain="true"
						icon="icon-remove">保存</a>
				</div>
				<div style="float: left;">
					<a id="pq_cancel" href="#" class="easyui-linkbutton" plain="true"
						icon="icon-save">重置</a>
				</div>
				<div style="float: left;">
					<a id="qp_defalut" href="#" class="easyui-linkbutton" plain="true"
						icon="icon-remove">设置默认</a>
				</div>
			</div>
		</div>
		<div style="text-align: center; padding: 5px">
				<a id="g_bsubmit" href="javascript:void(0)" style="width: 80px;"
					class="easyui-linkbutton">提交</a> <a id="g_bmodify"
					href="javascript:void(0)" style="width: 80px;"
					class="easyui-linkbutton">确认修改</a> <a id="g_bcancel"
					href="javascript:void(0)" style="width: 80px;"
					class="easyui-linkbutton">取消</a>
			</div>
	</div>
</body>

</html>