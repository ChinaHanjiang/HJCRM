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

var totalPrice = 0.0;

$(document).ready(function(){
	
	$('#id_back').click(function(){
		
		var id = $('#taskId').val();
		
		 var newObj = {
			 id:'taskdetail',
			 title:'项目详情',
			 href:'<%=basePath%>win/taskdetail.do?taskId=' + id
		 };
		 
		 parent.closePanel('任务详情');
	});
});
</script>
<title></title>
</head>
<body>
	<div class="easyui-panel" data-options="border:false" style="width:820px;;height:600px;">
		<div class="easyui-panel" data-options="border:true" title="任务详情"
			style="" align="center">
			<input style="display: none;" type="text" name="taskId" id="taskId"
				value="${item.task.id }" />
			<table
				style="width: 600px; margin-right: auto; margin-left: auto; font-family: '宋体'; font-size: 13px;
				padding-top:10px;">
				<tr>
					<td style="width: 80px; height: 20px;">任务编号：</td>
					<td style="width: 180px; height: 20px;">${item.code }</td>
					<td style="width: 80px; height: 20px;">任务类型：</td>
					<td>${item.itemType.name }</td>
				</tr>
				<tr>
					<td style="width: 80px; height: 30px;">项目内容：</td>
					<td colspan="3">${item.task.name }</td>
				</tr>
				<tr>
					<td style="width: 80px; height: 20px;">客户：</td>
					<s:if test="item.customer == null">
						<td style="width: 180px; height: 20px;">${item.task.customer.name }</td>
					</s:if>
					<s:else>
						<td style="width: 180px; height: 20px;">${item.customer.name }</td>
					</s:else>
					<td style="width: 80px; height: 20px;">联系人：</td>
					<td>${item.contact.name }</td>
				</tr>
				<tr>
					<td style="width: 110px; height: 30px;">内 容：</td>
					<td colspan="3">${item.name }</td>
				</tr>
				<tr>
					<td style="width: 80px; height: 20px;">跟进者：</td>
					<td>${item.user.name }</td>
					<td style="width: 80px; height: 20px;">创建时间：</td>
					<td>${item.createTime }</td>
				</tr>
				<tr>
					<td style="width: 110px; height: 25px;">备注：</td>
					<td colspan="3">${item.remarks }</td>
				</tr>
				<s:if test="item.itemAttachments!=null">
					<%
						int i = 0;
					%>
					<s:iterator value="item.itemAttachments" var="ia">
							<tr>
								<%
									if (i == 0) {
								%>
								<td style="width: 110px; height: 25px;">附件：</td>
								<%
									} else {
								%>
								<td style="width: 110px; height: 25px;"></td>
								<%
									}
								%>
								<td>${ia.name }</td>
								<td>下载</td>
							</tr>
							<%
								i++;
							%>
						</s:iterator>
				</s:if>
			</table>
			<s:if test="pqds!=null">
				<%int i=0;%>
				<s:iterator value="pqds" var="p">
					<script type="text/javascript">
						totalPrice += ${p.price}
					</script>
					<div class="easyui-panel" data-options="border:false"
						style="width: 720px; padding-top: 10px;">
						<table id="quotelist_<%=i %>" cellspacing="0" cellpadding="0"
							class="easyui-datagrid"
							data-options="
								url:'<%=basePath%>quotedetail/list.do?productQuoteDetailId= ' + ${p.id }, 
								title:'${p.product.name }的报价单', 
				       			loadMsg:'正在加载数据，请稍后...',
				       			border: true, 
				       			rownumbers:true,
								singleSelect:true,
								autoRowHeight:true,
								autoRowWidth:true,
					   			pageSize:10
							">
							<thead>
								<tr>
									<th data-options="field:'id',width:30,hidden:true">序号</th>
									<th data-options="field:'productCatalogId',hidden:true"></th>
									<th	data-options="field:'productCatalog',width:150">产品类型</th>
									<th data-options="field:'spid',hidden:true"></th>
									<th	data-options="field:'sproduct',width:150">名称</th>
									<th data-options="field:'code',width:150,align:'center'">产品编码</th>
									<th data-options="field:'standardPrice',width:90,align:'center'">标准价</th>
									<th data-options="field:'definedPrice',width:90,align:'center'">自定义价</th>
									<th data-options="field:'quantity',width:50,align:'center'">数量</th>
								</tr>
							</thead>
						</table>
						<div style="padding-top:3px;"><span style="float:right; font-weight:bold; font-size:14; width:100px;">合计:&nbsp;&nbsp;<span id="pq_price_<%=i %>">${p.price }</span></span></div>
					</div>
				<% i++; %>
				</s:iterator>
				<div style="padding-top:3px;height:25px;"><span style="padding-right:60px;float:right; font-weight:bold; font-size:14; width:100px;">总计:&nbsp;&nbsp;<span id="pq_totalPrice"></span></span></div>
				
				<script type="text/javascript">
					$('#pq_totalPrice').html(totalPrice);
				</script>
			</s:if>
		</div>
		<div style="padding-top:20px; width:800px; padding-bottom: 10px;" align="center">
				<a id="id_back" href="javascript:void(0)" style="width: 120px;"
								class="easyui-linkbutton">关闭</a>
		</div>
	</div>
</body>
</html>