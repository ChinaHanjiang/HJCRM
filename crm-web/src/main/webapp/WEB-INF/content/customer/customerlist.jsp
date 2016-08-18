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
	href="<%=basePath%>themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>themes/icon.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>demo.css">
<script type="text/javascript" src="<%=basePath%>js/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>js/jquery.easyui.min.js"></script>
<script type="text/javascript">
	
	$(document).ready(function(){
		
		$('#addGrid').click(function(){
			$('#addWin').window('open');
		});
		
		$('#checkContact').click(function(){
			$('#contactWin').window('open');
		});
		
		$('#customergrid').datagrid({
			onDblClickRow:function(index,row){
				
				$('#contactWin').window('open');
			}
		});
		
		var handleTree = function(str){
			str.replace("\"","");
			return str;
		};
		/*--树形结构--*/
		$.ajax({  
	        url : '<%=basePath%>loc/list.do',
			type : 'post',
			data : null,
			dataType : 'json',
			success : function(data) {
				var s = handleTree(data.locTree);
				$('#location').tree({
					data : eval("(" + s + ")"),
					lines : true,
					onClick : function(node) {
						//var _f = node.isF;
						var _selectNodeId = node.id;
						var _locId = node.id;
						var qp = $('#customergrid').datagrid('options').queryParams;
						qp.locId = _locId;
						$("#customergrid").datagrid('reload');
					}
				});
			}
		});
	});
</script>
<title>类型列表</title>

</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'west'" title="类型列表" style="width: 240px">
			<ul id="location"></ul>
		</div>
		<div data-options="region:'center'">
			<table id="customergrid" class="easyui-datagrid" title="客户信息列表"
				data-options="
						url:'<%=basePath%>customer/list.do',
						loadMsg:'数据加载中请稍后……',  
						rownumbers:false,
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
				<!-- sortName:'id', -->

				<thead>
					<tr>
						<th data-options="field:'ck',checkbox:true"></th>
						<th data-options="field:'id',width:50">序号</th>
						<th data-options="field:'code',width:100">客户编码</th>
						<th data-options="field:'name',width:150">名称</th>
						<th data-options="field:'address',width:250">地址</th>
						<th data-options="field:'telephone',width:100">电话</th>
						<th data-options="field:'fax',width:100">传真</th>
						<th data-options="field:'createTime',width:140">创建时间</th>
						<th data-options="field:'updateTime',width:140">修改时间</th>
						<th data-options="field:'remarks',width:150">备注</th>
					</tr>
				</thead>
			</table>
		</div>
		<div id="tb"
			style="border-bottom: 1px solid #ddd; height: 30px; padding: 3px 10px 0px 5px; background: #fafafa;">
			<div style="float: left;">
				<a id="addGrid" href="#" class="easyui-linkbutton" plain="true"
					icon="icon-add">新增</a>
			</div>

			<div style="float: left;">
				<a id="editGrid" href="#" class="easyui-linkbutton" plain="true"
					icon="icon-save"">编辑</a>
			</div>

			<div style="float: left;">
				<a id="removeGrid" href="#" class="easyui-linkbutton" plain="true"
					icon="icon-remove">删除</a>
			</div>
			<div style="float: left;">
				<a id="checkContact" href="#" class="easyui-linkbutton" plain="true"
					icon="icon-remove">查看联系人</a>
			</div>
			<div style="float: left;">
				<a id="export" href="#" class="easyui-linkbutton" plain="true"
					icon="icon-remove">导出excel</a>
			</div>
			<div style="float: right; padding-right: 10px;">
				员工：<input id="search" class="easyui-searchbox"
					data-options="prompt:'请输入查询员工姓名'" style="width: 150px;"></input>
			</div>

		</div>

		<!-- 添加客户窗口 -->
		<div id="addWin" class="easyui-window" title="公司客户编辑窗口"
			data-options="modal:true,closed:true,iconCls:'icon-save',minimizable:false,collapsible:false,maximizable:false"
			style="width: 500px; height: 500px; padding: 10px;">
			<div style="padding: 10px 60px 20px 60px">
				<form id="addWinForm" method="post">
					<table cellpadding="5">

						<tr id="c_code">
							<td>客户编码:</td>
							<td><input id="c_code" class="easyui-textbox"
								style="width: 200px" type="text" name="cardNumber"
								data-options="editable:false"></td>
						</tr>

						<tr id="c_name">
							<td>客户名称:</td>
							<td><input id="cardNumber" class="easyui-textbox"
								style="width: 200px" type="text" name="cardNumber"
								data-options="editable:false"></input></td>
						</tr>
						<tr id="c_phone">
							<td>固定电话:</td>
							<td><input id="totalScore" class="easyui-textbox"
								style="width: 200px" type="text" name="totalScore"
								data-options="editable:false"></input></td>
						</tr>
						<tr id="c_fax">
							<td>传真:</td>
							<td><input id="totalScore" class="easyui-textbox"
								style="width: 200px" type="text" name="totalScore"
								data-options="editable:false"></input></td>
						</tr>
						<tr id="c_address">
							<td>地址:</td>
							<td><input id="totalScore" class="easyui-textbox"
								style="width: 200px" type="text" name="totalScore"
								data-options="editable:false"></input></td>
						</tr>
						<tr>
							<td>项目:</td>
							<td><select id="item" class="easyui-combobox"
								style="width: 200px" name="i">
							</select></td>
						</tr>
						<tr>
							<td>备注:</td>
							<td><textarea id="remark" rows=5 style="width: 200px"
									name="" class="textarea easyui-validatebox"}></textarea></td>
						</tr>

					</table>
				</form>
				<div style="text-align: center; padding: 5px">
					<a id="bsubmit" href="javascript:void(0)" style="width: 80px;"
						class="easyui-linkbutton">提交</a> <a id="bmodify"
						href="javascript:void(0)" style="width: 80px;"
						class="easyui-linkbutton">确认修改</a> <a id="bcancel"
						href="javascript:void(0)" style="width: 80px;"
						class="easyui-linkbutton">取消</a>
				</div>
			</div>
		</div>
		
		<!-- 联系人 -->
		<div id="contactWin" class="easyui-window" title="客户联系人列表"
			data-options="modal:true,closed:true,iconCls:'icon-save',minimizable:false,collapsible:false,maximizable:false"
			style="width: 500px; height: 500px; padding: 10px;">
			
		 </div>
	</div>
</body>
</html>