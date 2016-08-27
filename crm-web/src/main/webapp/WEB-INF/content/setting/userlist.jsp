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

	/*定义全局变量*/
	
	var _u_name;
	var _u_cardName;
	var _u_phone;
	var _u_sex;
	var _u_duty;
	var _u_email;
	var _u_remarks;
	
	$(document).ready(function(){
		
		$('#addUserWin').window({
			
			onClose:function(){
				
				$('#u_name').textbox('setValue','');
				$('#u_cardName').textbox('setValue','');
				$('#u_phone').textbox('setValue','');
				$('#u_email').textbox('setValue','');
				$('#u_duty').textbox('setValue','');
				$('#u_sex').combobox('setValue',1);
				$('#u_remarks').val();
			}
		});
		
		$('#u_addUser').click(function(){
			
			$('#u_bsubmit').show();
			$('#u_bmodify').hide();
			
			$('#addUserWin').window('open');
			
		});
		
		$('#u_editUser').click(function(){
			
			$('#u_bsubmit').hide();
			$('#u_bmodify').show();
			
			
			var row = $('#usergrid').datagrid("getSelected");
			if(row!=null){
				
				_u_id = row.id;
				_u_name = row.name;
				_u_cardNumber = row.carNumber;
				_u_phone = row.phone;
				_u_email = row.email;
				_u_sex = row.sexId;
				_u_remarks = row.remarks;
				
				$('#u_name').textbox('setValue',_u_name);
				$('#u_cardName').textbox('setValue',_u_cardName);
				$('#u_remarks').val(_u_remarks);
				$('#u_phone').textbox('setValue',_u_phone);
				$('#u_email').textbox('setValue',_u_email);
				$('#u_duty').textbox('setValue',_u_duty);
				$('#u_sex').combobox('setValue',_u_sex);
				
				$('#addUserWin').window('open');
				
			} else {
				
				$.messager.alert('注意', '请选择行数据!',
				'info');
			}
			
		});
		
		$('#u_deleteUser').click(function(){
			
			var row = $('#usergrid').datagrid("getSelected");
			if(row){
				
				 $.messager.confirm('Confirm','您确定删除:"'+ row.name+'"的信息吗?',function(r){
					 
					    if (r){
					    	
					    	 var rowIndex = $('#usergrid').datagrid('getRowIndex', row);
							 $('#usergrid').datagrid('deleteRow',rowIndex);
							 
							 var id = row.id;
							 
							 $.ajax({
								 
								type: "POST",
								url: "<%=basePath%>user/del.do?ud.id=" + id,
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
		
		$('#u_bsubmit').click(function(){
			
			var u_name;
			var u_cardName;
			var u_phone;
			var u_email;
			var u_sex;
			var u_duty;
			var u_remarks;
			
			u_name = $('#u_name').textbox('getValue');
			u_cardName = $('#u_cardName').textbox('getValue',_u_cardName);
			u_remarks = $('#u_remarks').val(_u_remarks);
			u_phone = $('#u_phone').textbox('getValue');
			u_email = $('#u_email').textbox('getValue');
			u_duty = $('#u_duty').textbox('getValue');
			u_sex = $('#u_sex').combobox('getValue');
			
			var l = $('#addTaskTypeForm').form('enableValidation').form('validate');
			if(l){
				
				var str = '';
				str += "ud.name=" + u_name;
				str += "&ud.cardName=" + u_cardName;
				str += "&ud.remarks=" + u_remarks;
				str += "&ud.phone=" + u_phone;
				str += "&ud.email=" + u_email;
				str += "&ud.duty=" + u_duty;
				str += "&ud.sex=" + u_sex;
				
				$.ajax({
					 type:"POST",
		    		 url:"<%=basePath%>user/add.do?" + str,
		    		 cache:false,
		    		 dataType:'json',
		    		 success:function(data){
		    			 
		    			 var _data = data.md;
		    			 if(_data.t){
		    				 
		    				 $.messager.alert('成功', _data.message,
								'info');
		    				 $("#usergrid").datagrid('reload');
		    				 
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
		
		$('#u_bmodify').click(function(){
			
			var u_name;
			var u_cardName;
			var u_phone;
			var u_email;
			var u_sex;
			var u_duty;
			var u_remarks;
			
			u_name = $('#u_name').textbox('getValue');
			u_cardName = $('#u_cardName').textbox('getValue');
			u_remarks = $('#u_remarks').val(_u_remarks);
			u_phone = $('#u_phone').textbox('getValue');
			u_email = $('#u_email').textbox('getValue');
			u_duty = $('#u_duty').textbox('getValue');
			u_sex = $('#u_sex').combobox('getValue');
			
			var str = '';
			var isChanged = false;
			
			var l = $('#addTaskTypeForm').form('enableValidation').form('validate');
			if(l){
				
				str += "ud.id=" + _u_id;
				str += "&ud.name=" + u_name;
				str += "&ud.cardName=" + u_cardName;
				str += "&ud.remarks=" + u_remarks;
				str += "&ud.phone=" + u_phone;
				str += "&ud.email=" + u_email;
				str += "&ud.duty=" + u_duty;
				str += "&ud.sex=" + u_sex;
				
				if(u_name != _u_name){
					
					isChanged = true;
				} 
				if(u_cardName != _u_cardName){
					
					isChanged = true;
				} 
				if(u_phone != _u_phone){
									
					isChanged = true;
				} 
				if(u_email != _u_email){
					
					isChanged = true;
				} 
				if(u_sex != _u_sex){
					
					isChanged = true;
				} 
				if(u_duty != _u_duty){
					
					isChanged = true;
				}
				if(u_remarks != _u_remarks){
					
					isChanged = true;
				}
				
				if(isChanged){
					
					$.ajax({
						 type:"POST",
			    		 url:"<%=basePath%>user/modify.do?" + str,
			    		 cache:false,
			    		 dataType:'json',
			    		 success:function(data){
			    			 
			    			 var _data = data.md;
			    			 if(_data.t){
			    				 
			    				 $.messager.alert('成功', _data.message,
									'info');
			    				 $("#usergrid").datagrid('reload');
			    				 
			    			 } else {
			    				 
			    				 $.messager.alert('失败', _data.message,
									'info');
			    			 }
			    		 }
					});
					
				} else {
					
					$.messager.alert('注意', '用户信息没有改变！',
					'info');
				}
			} else {
				
				$.messager.alert('注意', '请填完整的表单！',
				'info');
			}
		});
		
		$('#u_bcancel').click(function(){
			
			$('#addUserWin').window('close');
		});
		
	});
	</script>
<title>员工列表</title>
</head>
<body>
	<table id="usergrid" cellspacing="0" cellpadding="0"
		class="easyui-datagrid"
		data-options="
					url:'<%=basePath%>user/list.do',
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
		    		toolbar: '#tb' 
				">
		<thead>
			<tr>
				<th data-options="field:'id',hidden:true">序号</th>
				<th data-options="field:'cardName',width:120,align:'center'">工号</th>
				<th data-options="field:'name',width:120,align:'center'">名字</th>
				<th data-options="field:'sexId',hidden:true"></th>
				<th data-options="field:'sex',width:30,align:'center'">性别</th>
				<th data-options="field:'duty',width:130,align:'center'">职务</th>
				<th data-options="field:'mobilephone',width:130,align:'center'">电话</th>
				<th data-options="field:'email',width:130,align:'center'">邮箱</th>
				<th data-options="field:'userId',hidden:true"></th>
				<th data-options="field:'user',width:130,align:'center'">职务</th>
				<th data-options="field:'createTime',width:140">创建时间</th>
				<th data-options="field:'updateTime',width:140">修改时间</th>
				<th data-options="field:'remarks',width:130,align:'center'">备注</th>
			</tr>
		</thead>
	</table>

	<div id="tb"
		style="border-bottom: 1px solid #ddd; height: 30px; padding: 3px 10px 0px 5px; background: #fafafa;">
		<div style="float: left;">
			<a id="u_addUser" href="#" class="easyui-linkbutton" plain="true"
				icon="icon-add">新增</a>
		</div>

		<div style="float: left;">
			<a id="u_editUser" href="#" class="easyui-linkbutton" plain="true"
				icon="icon-save">编辑</a>
		</div>

		<div style="float: left;">
			<a id="u_deleteUser" href="#" class="easyui-linkbutton" plain="true"
				icon="icon-remove">删除</a>
		</div>

		<div style="float: right; padding-left: 5px;">
			<input id="u_search" class="easyui-searchbox"
				data-options="prompt:'请输入查询员工姓名'" style="width: 150px;"></input>
		</div>
	</div>

	<!-- 添加员工窗口 -->
	<div id="addUserWin" class="easyui-window" title="员工编辑窗口"
		data-options="modal:true,closed:true,iconCls:'icon-save',minimizable:false,collapsible:false,maximizable:false"
		style="width: 420px; height: 440px; padding: 10px;">
		<div style="padding: 10px 60px 20px 60px">
			<form id="addUserForm" method="post">
				<table cellpadding="5">
					<tr>
						<td>工号:</td>
						<td><input id="u_cardNumber" class="easyui-textbox"
							style="width: 200px" type="text" name="f_u_cardNumber" required
							data-options=""></input></td>
					</tr>
					<tr>
						<td>姓名:</td>
						<td><input id="u_name" class="easyui-textbox"
							style="width: 200px" type="text" name="f_u_name" required
							data-options=""></input></td>
					</tr>
					<tr>
							<td>性别:</td>
							<td>
							<select id="u_sex" class="easyui-combobox"
								style="width: 200px" name="f_u_sex">
								<option value=1 selected="selected">男</option>
								<option value=0>女</option>
							</select>
							</td>
						</tr>
					<tr>
						<td>职称:</td>
						<td><input id="u_duty" class="easyui-textbox"
							style="width: 200px" type="text" name="f_u_duty" data-options=""></input></td>
					</tr>
					<tr>
						<td>手机号:</td>
						<td><input id="u_phone" class="easyui-textbox"
							style="width: 200px" type="text" name="f_u_phone" data-options=""></input></td>
					</tr>
					<tr>
						<td>邮箱:</td>
						<td><input id="u_phone" class="easyui-textbox"
							style="width: 200px" type="text" name="f_u_phone" data-options=""></input></td>
					</tr>
					<tr>
						<td>备注:</td>
						<td><textarea id="u_remark" rows=5 style="width: 200px;height:60px;"
								name="f_u_remark" class="textarea easyui-validatebox"}></textarea></td>
					</tr>
				</table>
			</form>
			<div style="text-align: center; padding: 5px">
				<a id="u_bsubmit" href="javascript:void(0)" style="width: 80px;"
					class="easyui-linkbutton">提交</a> <a id="u_bmodify"
					href="javascript:void(0)" style="width: 80px;"
					class="easyui-linkbutton">确认修改</a> <a id="u_bcancel"
					href="javascript:void(0)" style="width: 80px;"
					class="easyui-linkbutton">取消</a>
			</div>
		</div>
	</div>
</body>

</html>