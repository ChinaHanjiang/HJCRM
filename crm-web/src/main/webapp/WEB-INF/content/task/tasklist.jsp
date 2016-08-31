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
		
		/*定义全局变量*/
		var _t_id;
		var _t_name;
		var _t_code;
		var _t_customer;
		var _t_customerId;
		var _t_tasktype;
		var _t_tasktypeId;
		var _t_user;
		var _t_userId;
		var _t_updateuser;
		var _t_updateuserId;
		var _t_itemNum;
		var _t_remarks;
		var _t_status;
		var _i_id;
		var _i_name;
		var _i_code;
		var _i_contact;
		var _i_contactId;
		var _i_remarks;
		
		$('#t_addTask').click(function(){
			
			$('#t_bsubmit').show();
			$('#t_bmodify').hide();
			
			$('#t_tasktype').combobox('setValue',0);
			
			$('#addTaskWin').window('open');
		});
		
		
		$('#t_editTask').click(function(){
			
			$('#t_bsubmit').show();
			$('#t_bmodify').hide();
			
			var row = $('#"taskgrid"').datagrid("getSelected");
			if(row!=null){
				
				_t_id = row.id;
				_t_name = row.name;
				_t_code = row.code;
				_t_customer = row.customer;
				_t_customerId = row.customerId;
				_t_tasktype = row.tasktype;
				_t_tasktypeId = row.tasktypeId;
				_t_user = row.user;
				_t_userId = row.userId;
				_t_updateuser = row.updateuser;
				_t_updateuserId = row.updateuserId;
				_t_itemNum = row.itemNum;
				_t_remarks = row.remarks;
				_t_status = row.status;
				
				
				$('#t_name').textbox('setValue',_t_name);
				$('#t_code').textbox('setValue',_t_code);
				$('#t_customerId').val(_t_customerId);
				$('#t_search').textbox('setValue',_t_customer);
				$('#t_remarks').val(_t_remarks);
				$('#t_tasktype').combobox('setValue',_t_tasktypeId);
				
				$('#addTaskWin').window('open');;
				
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
		
		$('#t_bsubmit').click(function(){
			
			var t_name;
			var t_code;
			var t_customerId;
			var t_tasktypeId;
			var t_remarks;
			var i_name;
			var i_code;
			var i_contactId;
			var i_remarks;
			
			t_name = $('#t_name').textbox('getValue');
			t_code = $('#t_code').textbox('getText');
			t_customerId = $('#t_customerId').val();
			t_reamrks = $('#t_remarks').val();
			t_tasktypeId = $('#t_tasktype').combobox('getValue');
			
			i_name = $('#i_name').textbox('getValue');
			i_code = $('#i_code').textbox('getText');
			i_remarks = $('#i_remarks').val('');
			i_contactId = $('#i_contact').combogrid('getValue');
			
			alert(i_contactId);
			
			var tl = $('#addTaskForm').form('enableValidation').form('validate');
			var il = $('#addItemForm').form('enableValidation').form('validate');
			if(tl){
				
				var str = '';
				str += "td.name=" + t_name;
				str += "&td.code=" + t_code;
				str += "&td.customerId=" + t_customerId;
				str += "&td.taskTypeId=" + t_tasktypeId;
				str += "&td.remarks=" + t_remarks; 
				
				if(i_name!=""){
					
					if(il){
						
						str += "&id.name=" + i_name;
						str += "&id.code=" + i_code;
						str += "&id.remarks=" + i_remarks;
						str += "&id.contactId=" + i_contactId;
					} else {
						
						$.messager.alert('注意', '事件的表单请填写完整！',
						'info');
					}
				}
				
				$.ajax({
					 type:"POST",
		    		 url:"<%=basePath%>task/add.do?" + str,
		    		 cache:false,
		    		 dataType:'json',
		    		 success:function(data){
		    			 
		    			 var _data = data.md;
		    			 if(_data.t){
		    				 
		    				 $.messager.alert('成功', _data.message,
								'info');
		    				 $("#taskgrid").datagrid('reload');
		    				 
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
		
		$('#t_bmodify').click(function(){
			
			var t_name;
			var t_code;
			var t_customerId;
			var t_tasktypeId;
			var t_remarks;
			
			t_name = $('#t_name').textbox('getValue');
			t_code = $('#t_code').textbox('getText');
			t_customerId = $('#t_customerId').val();
			t_reamrks = $('#t_remarks').val();
			t_tasktypeId = $('#t_tasktype').combobox('getValue');
			
			var str = '';
			var isChanged = false;
			
			var tl = $('#addTaskForm').form('enableValidation').form('validate');
			if(tl){
				
				str += "td.name=" + t_name;
				str += "&td.code=" + t_code;
				str += "&td.customerId=" + t_remarks;
				str += "&td.tasktypeId=" + t_tasktypeId;
				str += "&td.remarks=" + t_remarks; 
				
				if(_t_name != t_name){
					
					isChanged = true;
				}
				if(_t_code != t_code){
					
					isChanged = true;
				}
				if(_t_customerId != t_customerId){
					
					isChanged = true;
				}
				if(_t_remarks != t_remarks){
					
					isChanged = true;
				}
				if(_t_tasktype != t_tasktype){
					
					isChanged = true;
				}
				
				if(isChange){
					
					$.ajax({
						 type:"POST",
			    		 url:"<%=basePath%>task/modify.do?" + str,
			    		 cache:false,
			    		 dataType:'json',
			    		 success:function(data){
			    			 
			    			 var _data = data.md;
			    			 if(_data.t){
			    				 
			    				 $.messager.alert('成功', _data.message,
									'info');
			    				 $("#taskgrid").datagrid('reload');
			    				 
			    			 } else {
			    				 
			    				 $.messager.alert('失败', _data.message,
									'info');
			    			 }
			    		 }
					});
					
				} else {
					
					$.messager.alert('注意', '任务信息没有改变！',
					'info');
				}
				
			} else {
				
				$.messager.alert('注意', '请填完整的表单！',
				'info');
			}
			
		});
		
		$('#t_bcancel').click(function(){
			
			$('#addTaskWin').window('close');
			
		});
		
		$('#addTaskWin').window({
			
			onClose:function(){
				
				$('#t_name').textbox('setValue','');
				$('#t_code').textbox('setValue','');
				$('#t_customerId').val('');
				$('#t_search').searchbox('setValue','');
				$('#t_remarks').val('');
				$('#t_tasktype').combobox('setValue',0);
				
				$('#i_name').textbox('setValue','');
				$('#i_code').textbox('setValue','');
				$('#i_remarks').val('');
				$('#i_contact').combogrid('setValue','');
			}
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
		
		$('#t_tasktype').combobox({ 
		      editable:false, //不可编辑状态
		      required:true, 
		      cache: false,
		      panelHeight: 'auto',//自动高度适合
		      onChange:function(){
		    	  
		    	  var _tasktypeId = $(this).combobox('getValue');
		    	  var str = "ttd.id=" + _tasktypeId;
		    	  $.ajax({
		    		  type:"POST",
			    		 url:"<%=basePath%>task/generatecode.do?" + str,
			    		 cache:false,
			    		 dataType:'json',
			    		 success:function(data){
			    			 
			    			 var _result = data.code;
			    			 var _icode = _result + "." + "1";
			    			 $('#t_code').textbox('setText',_result);
			    			 $('#i_code').textbox('setText',_icode);
			    		 }
		    	  });
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
						height: 300, 
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
					<th data-options="field:'itemNum',width:50,align:'center'">事件数</th>
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
	<!-- 事件列表 -->
	
	<div style="padding-top: 10px;">
		<table id="itemgrid" cellspacing="0" cellpadding="0"
			class="easyui-datagrid"
			data-options="
						url:'<%=basePath%>item/list.do',
						loadMsg:'数据加载中请稍后……',  
						title:'事件列表',
						height: 300, 
						rownumbers:true,
						singleSelect:true,
						collapsible:false,
						autoRowHeight:true,
						autoRowWidth:true,
						pagination:true,
						pageNumber:1,
						pageSize:10,
						sortOrder:'desc'
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

	<!-- 添加任务窗口 -->
	<div id="addTaskWin" class="easyui-window" title="任务编辑窗口"
		data-options="modal:true,closed:true,iconCls:'icon-save',minimizable:false,collapsible:false,maximizable:false"
		style="width: 480px; height: 600px; padding: 10px;">
		<div class="easyui-panel" data-options="border:false" style="width:450px;height:550px;">
	        <div class="easyui-layout" data-options="fit:true">
	            <div id="f_task" data-options="region:'north',collapsible:false,border:false" title="任务" style="width:450px;height:270px;padding:10px">
	                <div style="padding: 0px 20px 0px 50px">
						<form id="addTaskForm" method="post">
							<table cellpadding="5">
								<tr>
									<td>内容:</td>
									<td><input id="t_name" class="easyui-textbox"
										style="width: 200px; height:40px;" type="text" name="f_t_name" required
										data-options="multiline:true"></input></td>
								</tr>
								<tr>
									<td>任务类别:</td>
									<td>
										<select id="t_tasktype" class="easyui-combobox"  required
											data-options="panelHeight: 'auto',editable:false"
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
										style="width: 200px; height:20px;" type="text" name="f_t_code" required
										data-options="editable:false"></input></td>
								</tr>
								<tr>
									<td>客户:</td>
									<td>
										<input id="t_customerId" style="width: 200px; display:none;"
										 type="text" name="f_t_customerId"></input>
										<input id="t_search" class="easyui-searchbox" required
											data-options="prompt:'请输入查询客户名称'" style="width: 200px;"></input>
									</td>
								</tr>
								<tr>
									<td>备注:</td>
									<td><textarea id="t_remarks" rows=5 style="width: 200px;height:40px;"
											name="f_t_remarks" class="textarea easyui-validatebox"}></textarea></td>
								</tr>
							</table>
						</form>
					</div>
	            </div>
	            <div id="f_item" data-options="region:'center',border:false" title="事件" style="width:450px; height:250px; padding:10px;">
		            <div style="padding: 0px 20px 0px 50px">
		            	<form id="addItemForm" method="post">
		            		<table cellpadding="5">
								<tr>
									<td>内容:</td>
									<td><input id="i_name" class="easyui-textbox"
										style="width: 200px; height:40px;" type="text" name="f_i_name" required
										data-options="multiline:true"></input></td>
								</tr>
								<tr>
									<td>编码:</td>
									<td><input id="i_code" class="easyui-textbox"
										style="width: 200px; height:20px;" type="text" name="f_i_code" required
										data-options="editable:false"></input></td>
								</tr>
								<tr>
									<td>联系人:</td>
									<td>
							            <select id="i_contact" class="easyui-combogrid" style="width:200px;" required 
							            data-options=" 
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
									<td><textarea id="i_remarks" rows=5 style="width: 200px;height:40px;"
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