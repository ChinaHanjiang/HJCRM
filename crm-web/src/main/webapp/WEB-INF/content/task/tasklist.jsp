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
		
		/*定义全局变量*/
		var _c_id;
		var _c_name;
		
		$('#t_addTask').click(function(){
			
			$('#t_bsubmit').show();
			$('#t_bmodify').hide();
			
			$('#addEmpWin').window('open');;
		});
		
		
		$('#t_editTask').click(function(){
			
			$('#t_bsubmit').show();
			$('#t_bmodify').hide();
			
		});
		
		$('#t_removeTask').click(function(){
			
			
		});
		
		$('#t_bsubmit').click(function(){
			
			
		});
		
		$('#t_bmodify').click(function(){
			
			
		});
		
		$('#t_bcancle').click(function(){
			
			
		});
		
		$('#t_search').searchbox({
			
			searcher:function(value,name){
				
				if(value==""){
					
					$.messager.alert('注意','请输入需要检索的名字!',
					'info');
					return;
				}
				
				var str = '';
				str +="cd.name=" + value;
				
			    $('#searchlist').datalist({
			        url: '<%=basePath%>customer/search.do?' + str.trim()
			    });
			    
			    $('#searchCustomerWin').window('open');
			}
		});
		
		$('#searchlist').datalist({
			
			onDblClickRow:function(index,row){
				
				var id = row.id;
				var text = row.text;
				
				$('#t_search').searchbox('setValue',text);
				$('#t_customerId').val(id);
				
				var str = '';
				str += "cd.id=" + id;
				
				$('#i_contact').combogrid({
					url:'<%=basePath%>contact/find.do?' + str.trim()
				});
				
				 $('#searchCustomerWin').window('close');
				
			}
		});
		
		$('#searchCustomerWin').window({
			
			onClose:function(){
				
				
			}
		});
	});
	</script>
<title>任务列表</title>
</head>
<body>
	<table id="employeeinfo" cellspacing="0" cellpadding="0"
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
		    		toolbar: '#tb' 
				">
		<thead>
			<tr>
				<th data-options="field:'id',width:30,hidden:true">序号</th>
				<th data-options="field:'code',width:120,align:'center'">编码</th>
				<th data-options="field:'taskTypeId',hidden:true"></th>
				<th data-options="field:'taskType',width:120,align:'center'">任务类型</th>
				<th data-options="field:'name',width:120,align:'center'">内容</th>
				<th data-options="field:'customerId',hidden:true"></th>
				<th data-options="field:'customer',width:60,align:'center'">客户</th>
				<th data-options="field:'userId',hidden:true"></th>
				<th data-options="field:'createUser',width:130,align:'center'">创建用户</th>
				<th data-options="field:'createTime',width:120,align:'center'">创建时间</th>
				<th data-options="field:'updateUserId',hidden:true"></th>
				<th data-options="field:'updateUser',width:130,align:'center'">跟进者</th>
				<th data-options="field:'updateTime',width:60,align:'center'">跟进时间</th>
				<th data-options="field:'itemNum',width:60,align:'center'">子任务数</th>
				<th data-options="field:'status',width:60,align:'center'">状态</th>
				<th data-options="field:'remark',width:130,align:'center'">备注</th>
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

	<!-- 添加任务窗口 -->
	<div id="addTaskWin" class="easyui-window" title="任务编辑窗口"
		data-options="modal:true,closed:true,iconCls:'icon-save',minimizable:false,collapsible:false,maximizable:false"
		style="width: 480px; height: 600px; padding: 10px;">
		<div class="easyui-panel" data-options="border:false" style="width:450px;height:550px;">
	        <div class="easyui-layout" data-options="fit:true">
	            <div data-options="region:'north',collapsible:false" title="任务" style="width:450px;height:280px;padding:10px">
	                <div style="padding: 0px 20px 20px 50px">
						<form id="addTaskForm" method="post">
							<table cellpadding="5">
								<tr>
									<td>内容:</td>
									<td><input id="t_name" class="easyui-textbox"
										style="width: 200px" type="text" name="f_t_name" required
										data-options=""></input></td>
								</tr>
								<tr>
									<td>任务类别:</td>
									<td>
										<select id="t_tasktype" class="easyui-combobox" 
											data-options="panelHeight: 'auto',editable:true"
											style="width: 200px" name="f_t_tasktype">
											<option value=0 selected="selected">请选择</option>
												<s:if test="taskTypes!=null">
													<s:iterator value="taskTypes" var="tt">
														<option value=${tt.id }>${tt.name }</option>
													</s:iterator>
												</s:if>
										</select>
									</td>
								</tr>
								<tr>
									<td>编码:</td>
									<td><input id="t_code" class="easyui-textbox"
										style="width: 200px" type="text" name="f_t_name" required
										data-options=""></input></td>
								</tr>
								<tr>
									<td>客户:</td>
									<td>
										<input id="t_customerId" style="width: 200px;display:none;"
										 type="text" name="f_t_customerId"></input>
										<input id="t_search" class="easyui-searchbox"
											data-options="prompt:'请输入查询客户名称'" style="width: 200px;"></input>
									</td>
								</tr>
								<tr>
									<td>备注:</td>
									<td><textarea id="t_remarks" rows=5 style="width: 200px;height:60px;"
											name="f_t_remarks" class="textarea easyui-validatebox"}></textarea></td>
								</tr>
							</table>
						</form>
					</div>
	            </div>
	            <div data-options="region:'center'"  title="事件" style="width:450px;height:250px;padding:10px;">
		            <div style="padding: 0px 20px 20px 50px">
		            	<form id="addItemForm" method="post">
		            		<table cellpadding="5">
								<tr>
									<td>内容:</td>
									<td><input id="i_name" class="easyui-textbox"
										style="width: 200px" type="text" name="f_i_name" required
										data-options=""></input></td>
								</tr>
								<tr>
									<td>联系人:</td>
									<td>
							            <select id="i_contact" class="easyui-combogrid" style="width:200px;" data-options="
							                    panelWidth: 500,
							                    idField: 'id',
							                    textField: 'name',
							                    columns: [[
							                        {field:'id',title:'序号',width:80,align:'center'},
							                        {field:'name',title:'名称',width:120,align:'center'},
							                        {field:'duty',title:'职务',width:80,align:'center'}
							                    ]],
							                    fitColumns: true
							                ">
							            </select>
									</td>
								</tr>
								<tr>
									<td>备注:</td>
									<td><textarea id="i_remarks" rows=5 style="width: 200px;height:60px;"
											name="f_i_remarks" class="textarea easyui-validatebox"}></textarea></td>
								</tr>
							</table>
		            	
		            	</form>
		            </div>
	            </div>
	            <div data-options="region:'south',border:false"  style="padding:10px">
	               <div style="text-align: center; padding: 5px">
						<a id="t_bsubmit" href="javascript:void(0)" style="width: 80px;"
							class="easyui-linkbutton">提交</a> <a id="t_bmodify"
							href="javascript:void(0)" style="width: 80px;"
							class="easyui-linkbutton">确认修改</a> <a id="t_bcancel"
							href="javascript:void(0)" style="width: 80px;"
							class="easyui-linkbutton">取消</a>
					</div>
	            </div>
	        </div>
	    </div>
	</div>
	
	<!-- 弹出客户查询窗口 -->
	<div id="searchCustomerWin" class="easyui-window"  title="客户查询结果"
		data-options="modal:true,closed:true,border:false,
		minimizable:false,collapsible:false,maximizable:false"
		style="width: 350px; height: 300px; padding-left:15px;padding-top:10px;">
		 <ul id="searchlist" class="easyui-datalist" lines="true"
		 style="width:300px;height:250px">
		 	
		 </ul>
	</div>
</body>

</html>