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

	/*定义全局变量*/
	var _tt_id;
	var _tt_name;
	var _tt_remarks;
	var _tt_tn_right = true;
	var _tt_tc_right = true;
	var _tt_action_type = 0; /*0-新增，1-修改*/
	
	$(document).ready(function(){
		
		$('#addTaskTypeWin').window({
			
			onClose:function(){
				
				_tt_action_type = 0;
				_tt_tn_right = true;
				_tt_tc_right = true;
				
				$('#tt_name').textbox('setValue','');
				$('#tt_code').textbox('setValue','');
				$('#tt_remarks').val('');
				$('#ttn_wrong').hide();
    			$('#ttn_ok').hide();
    			$('#ttc_wrong').hide();
    			$('#ttc_ok').hide();
				
			}
		});
		
		$('#tt_addTaskType').click(function(){
			
			_tt_action_type = 0;
			
			$('#tt_bsubmit').show();
			$('#tt_bmodify').hide();
			
			$('#addTaskTypeWin').window('open');
		});
		
		$('#tt_editTaskType').click(function(){
			
			$('#tt_bsubmit').hide();
			$('#tt_bmodify').show();
			
			_tt_action_type = 1;
			
			var row = $('#tasktypegrid').datagrid("getSelected");
			if(row!=null){
				
				_tt_id = row.id;
				_tt_name = row.name;
				_tt_code = row.code;
				_tt_remarks = row.remarks;
				
				$('#tt_name').textbox('setValue',_tt_name);
				$('#tt_code').textbox('setValue',_tt_code);
				$('#tt_remarks').val(_tt_remarks);
				
				$('#addTaskTypeWin').window('open');
				
			} else {
				
				$.messager.alert('注意', '请选择行数据!',
				'info');
			}
		});
		
		$('#tt_deleteTaskType').click(function(){
			
			var row = $('#tasktypegrid').datagrid("getSelected");
			if(row){
				
				 $.messager.confirm('Confirm','您确定删除:"'+ row.name+'"的信息吗?',function(r){
					 
					    if (r){
					    	
					    	 var rowIndex = $('#tasktypegrid').datagrid('getRowIndex', row);
							 $('#tasktypegrid').datagrid('deleteRow',rowIndex);
							 
							 var id = row.id;
							 
							 $.ajax({
								 
								type: "POST",
								url: "<%=basePath%>tasktype/del.do?ttd.id=" + id,
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
		
		$('#tt_bsubmit').click(function(){
			
			var tt_name;
			var tt_code;
			var tt_remarks;
			
			tt_name = $('#tt_name').textbox('getText');
			tt_code = $('#tt_code').textbox('getText');
			tt_remarks = $('#tt_remarks').val();
			
			var l = $('#addTaskTypeForm').form('enableValidation').form('validate');
			if(l){
				
				if(!_tt_tn_right){
					
					$.messager.alert('注意', '集团名称与已有存在冲突，请重新确认!',
					'info');
					return;
				}
				
				if(!_tt_tc_right){
					
					$.messager.alert('注意', '集团编码与已有存在冲突，请重新确认!',
					'info');
					return;
				}
				
				var str = '';
				str += "ttd.name=" + tt_name;
				str += "&ttd.code=" + tt_code;
				str += "&ttd.remarks=" + tt_remarks;
				
				$.ajax({
					 type:"POST",
		    		 url:"<%=basePath%>tasktype/add.do?" + str,
		    		 cache:false,
		    		 dataType:'json',
		    		 success:function(data){
		    			 
		    			 var _data = data.md;
		    			 if(_data.t){
		    				 
		    				 $.messager.alert('成功', _data.message,
								'info');
		    				 $("#tasktypegrid").datagrid('reload');
		    				 
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
		
		$('#tt_bmodify').click(function(){
			
			var tt_name;
			var tt_code;
			var tt_remarks;
			
			tt_name = $('#tt_name').textbox('getText');
			tt_code = $('#tt_code').textbox('getText');
			tt_remarks = $('#tt_remarks').val();
			
			var str = '';
			var isChanged = false;
			
			var l = $('#addTaskTypeForm').form('enableValidation').form('validate');
			if(l){
				
				if(!_tt_tn_right){
					
					$.messager.alert('注意', '集团名称与已有存在冲突，请重新确认!',
					'info');
					return;
				}
				
				if(!_tt_tc_right){
					
					$.messager.alert('注意', '集团编码与已有存在冲突，请重新确认!',
					'info');
					return;
				}
				
				str = '';
				str += "ttd.id=" + _tt_id;
				str += "&ttd.name=" + tt_name;
				str += "&ttd.code=" + tt_code;
				str += "&ttd.remarks=" + tt_remarks;
				
				if(tt_name != _tt_name){
					
					isChanged = true;
				} 
				if(tt_code != _tt_code){
					
					isChanged = true;
				}
				if(tt_remarks != _tt_remarks){
					
					isChanged = true;
				}
				
				if(isChanged){
					
					$.ajax({
						 type:"POST",
			    		 url:"<%=basePath%>tasktype/modify.do?" + str,
			    		 cache:false,
			    		 dataType:'json',
			    		 success:function(data){
			    			 
			    			 var _data = data.md;
			    			 if(_data.t){
			    				 
			    				 $.messager.alert('成功', _data.message,
									'info');
			    				 $("#tasktypegrid").datagrid('reload');
			    				 
			    			 } else {
			    				 
			    				 $.messager.alert('失败', _data.message,
									'info');
			    			 }
			    		 }
					});
					
				} else {
					
					$.messager.alert('注意', '集团信息没有改变！',
					'info');
				}
			} else {
				
				$.messager.alert('注意', '请填完整的表单！',
				'info');
			}
		});
		
		$('#tt_bcancel').click(function(){
			
			$('#addTaskTypeWin').window('close');
		});
		
		$('#tt_name').textbox({
			onChange:function(newValue,oldValue){
				
				if(newValue==""){
       				return;
       			}
				
	        	//判断名称有没有重复
				if(_tt_action_type==1){
					if(oldValue=="" && newValue!=""){
		        		return;
		        	}
				}
		        	
				var str = '';
					str += "ttd.name=" + newValue;
					str += "&ttd.code=";
				
	        	$.ajax({
					type: "POST",
					url: "<%=basePath%>tasktype/check.do?" + str,
					cache: false,
		        	dataType : "json",
		        	success:function(data){
		        		
		        		var _data =  data.md ;
		        		if(_data.t){
		        			
		        			_tt_tn_right = true;
		        			$('#ttn_wrong').hide();
		        			$('#ttn_ok').show();
		        		}else{
		        			
		        			_tt_tn_right = false;
		        			$('#ttn_ok').hide();
		        			$('#ttn_wrong').show();
		        			$.messager.alert('失败', _data.message,
							'info');
		        		}
		        	}
				});
	       	 }
		});
		
		$('#tt_code').textbox({
			onChange:function(newValue,oldValue){
				
				if(newValue==""){
       				return;
       			}
       			
	        	//判断名称有没有重复
				if(_tt_action_type==1){
				
					if(oldValue=="" && newValue!=""){
		        		return;
					}
		        }
		        	
				var str = '';
					str += "ttd.code=" + newValue;
					str += "&ttd.name="
				
	        	$.ajax({
					type: "POST",
					url: "<%=basePath%>tasktype/check.do?" + str,
					cache: false,
		        	dataType : "json",
		        	success:function(data){
		        		
		        		var _data =  data.md ;
		        		if(_data.t){
		        			
		        			_tt_tc_right = true;
		        			$('#ttc_wrong').hide();
		        			$('#ttc_ok').show();
		        		}else{
		        			
		        			_tt_tc_right = false;
		        			$('#ttc_ok').hide();
		        			$('#ttc_wrong').show();
		        			$.messager.alert('失败', _data.message,
							'info');
		        		}
		        	}
				});
			}
		});
	});
	</script>
<title>集团列表</title>
</head>
<body>
	<table id="tasktypegrid" cellspacing="0" cellpadding="0"
		class="easyui-datagrid"
		data-options="
					url:'<%=basePath%>tasktype/list.do', 
					title:'任务类型列表', 
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
				<th data-options="field:'name',width:120,align:'center'">名称</th>
				<th data-options="field:'code',width:120,align:'center'">编码</th>
				<th data-options="field:'createTime',width:180,align:'center'">创建时间</th>
				<th data-options="field:'updateTime',width:180,align:'center'">更新时间</th>
				<th data-options="field:'user',width:120,align:'center'">创建者</th>
				<th data-options="field:'remarks',width:130,align:'center'">备注</th>
			</tr>
		</thead>
	</table>

	<div id="tb"
		style="border-bottom: 1px solid #ddd; height: 30px; padding: 3px 10px 0px 5px; background: #fafafa;">
		<div style="float: left;">
			<a id="tt_addTaskType" href="#" class="easyui-linkbutton" plain="true"
				icon="icon-add">新增</a>
		</div>

		<div style="float: left;">
			<a id="tt_editTaskType" href="#" class="easyui-linkbutton" plain="true"
				icon="icon-save">编辑</a>
		</div>

		<div style="float: left;">
			<a id="tt_deleteTaskType" href="#" class="easyui-linkbutton" plain="true"
				icon="icon-remove">删除</a>
		</div>
		
	</div>

	<!-- 添加任务类型窗口 -->
	<div id="addTaskTypeWin" class="easyui-window" title="员工编辑窗口"
		data-options="modal:true,closed:true,iconCls:'icon-save',minimizable:false,collapsible:false,maximizable:false"
		style="width: 450px; height: 260px; padding: 10px;">
		<div style="padding: 10px 60px 20px 60px">
			<form id="addTaskTypeForm" method="post">
				<table cellpadding="5">
					<tr>
						<td>名称:</td>
						<td><input id="tt_name" class="easyui-textbox"
							style="width: 200px" type="text" name="f_tt_name" required
							data-options=""></input>
							<img id="ttn_ok" style="display:none;" src="<%=basePath%>icon/ok.png" />
							<img id="ttn_wrong" style="display:none;" src="<%=basePath%>icon/wrong.png" /></td>
					</tr>
					<tr>
						<td>编码:</td>
						<td><input id="tt_code" class="easyui-textbox"
							style="width: 200px" type="text" name="f_tt_code" required
							data-options=""></input>
							<img id="ttc_ok" style="display:none;" src="<%=basePath%>icon/ok.png" />
							<img id="ttc_wrong" style="display:none;" src="<%=basePath%>icon/wrong.png" /></td>
					</tr>
					<tr>
						<td>备注:</td>
						<td><textarea id="tt_remarks" rows=5 style="width:200px; height:50px;"
								name="f_tt_remarks" class="textarea easyui-validatebox"}></textarea></td>
					</tr>
				</table>
			</form>
			<div style="text-align: center; padding: 5px">
				<a id="tt_bsubmit" href="javascript:void(0)" style="width: 80px;"
					class="easyui-linkbutton">提交</a> <a id="tt_bmodify"
					href="javascript:void(0)" style="width: 80px;"
					class="easyui-linkbutton">确认修改</a> <a id="tt_bcancel"
					href="javascript:void(0)" style="width: 80px;"
					class="easyui-linkbutton">取消</a>
			</div>
		</div>
	</div>
</body>

</html>