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
	var _g_id;
	var _g_name;
	var _g_code;
	var _g_remarks;
	var _g_gn_right = true;
	var _g_gc_right = true;
	var _g_action_type = 0; /*0-新增，1-修改*/
	
	$(document).ready(function(){
		
		$('#addGroupsWin').window({
			
			onClose:function(){
				
				_g_action_type = 0;
				_g_gn_right = true;
				_g_gc_right = true;
				
				$('#g_name').textbox('setValue','');
				$('#g_code').textbox('setValue','');
				$('#g_remarks').val('');
				$('#gn_wrong').hide();
    			$('#gn_ok').hide();
    			$('#gc_wrong').hide();
    			$('#gc_ok').hide();
				
			}
		});
		
		$('#g_addEmp').click(function(){
			
			_g_action_type = 0;
			
			$('#g_bsubmit').show();
			$('#g_bmodify').hide();
			
			$('#addGroupsWin').window('open');
		});
		
		$('#g_editEmp').click(function(){
			
			$('#g_bsubmit').hide();
			$('#g_bmodify').show();
			
			_g_action_type = 1;
			
			var row = $('#groupsgrid').datagrid("getSelected");
			if(row!=null){
				
				_g_id = row.id;
				_g_name = row.name;
				_g_code = row.code;
				_g_remarks = row.remarks;
				
				$('#g_name').textbox('setValue',_g_name);
				$('#g_code').textbox('setValue',_g_code);
				$('#g_remarks').val(_g_remarks);
				
				$('#addGroupsWin').window('open');
				
			} else {
				
				$.messager.alert('注意', '请选择行数据!',
				'info');
			}
		});
		
		$('#g_deleteEmp').click(function(){
			
			var row = $('#groupsgrid').datagrid("getSelected");
			if(row){
				
				 $.messager.confirm('Confirm','您确定删除:"'+ row.name+'"的信息吗?',function(r){
					 
					    if (r){
					    	
					    	 var rowIndex = $('#groupsgrid').datagrid('getRowIndex', row);
							 $('#groupsgrid').datagrid('deleteRow',rowIndex);
							 
							 var id = row.id;
							 
							 $.ajax({
								 
								 type: "POST",
									url: "<%=basePath%>groups/del.do?gd.id=" + id,
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
		
		$('#g_bsubmit').click(function(){
			
			var g_name;
			var g_code;
			var g_remarks;
			
			g_name = $('#g_name').textbox('getText');
			g_code = $('#g_code').textbox('getText');
			g_remarks = $('#g_remarks').val();
			
			var l = $('#addGroupsForm').form('enableValidation').form('validate');
			if(l){
				
				if(!_g_gn_right){
					
					$.messager.alert('注意', '集团名称与已有存在冲突，请重新确认!',
					'info');
					return;
				}
				
				if(!_g_gc_right){
					
					$.messager.alert('注意', '集团编码与已有存在冲突，请重新确认!',
					'info');
					return;
				}
				
				var str = '';
				str += "gd.name=" + g_name;
				str += "&gd.code=" + g_code;
				str += "&gd.remarks=" + g_remarks;
				
				$.ajax({
					 type:"POST",
		    		 url:"<%=basePath%>groups/add.do?" + str,
		    		 cache:false,
		    		 dataType:'json',
		    		 success:function(data){
		    			 
		    			 var _data = data.md;
		    			 if(_data.t){
		    				 
		    				 $.messager.alert('成功', _data.message,
								'info');
		    				 $("#groupsgrid").datagrid('reload');
		    				 
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
		
		$('#g_bmodify').click(function(){
			
			var g_name;
			var g_code;
			var g_remarks;
			
			g_name = $('#g_name').textbox('getText');
			g_code = $('#g_code').textbox('getText');
			g_remarks = $('#g_remarks').val();
			
			var str = '';
			var isChanged = false;
			
			var l = $('#addGroupsForm').form('enableValidation').form('validate');
			if(l){
				
				if(!_g_gn_right){
					
					$.messager.alert('注意', '集团名称与已有存在冲突，请重新确认!',
					'info');
					return;
				}
				
				if(!_g_gc_right){
					
					$.messager.alert('注意', '集团编码与已有存在冲突，请重新确认!',
					'info');
					return;
				}
				
				str = '';
				str += "gd.id=" + _g_id;
				str += "&gd.name=" + g_name;
				str += "&gd.code=" + g_code;
				str += "&gd.remarks=" + g_remarks;
				
				if(g_name != _g_name){
					
					isChanged = true;
				} 
				if(g_code != _g_code){
					
					isChanged = true;
				}
				if(g_remarks != _g_remarks){
					
					isChanged = true;
				}
				
				if(isChanged){
					
					$.ajax({
						 type:"POST",
			    		 url:"<%=basePath%>groups/modify.do?" + str,
			    		 cache:false,
			    		 dataType:'json',
			    		 success:function(data){
			    			 
			    			 var _data = data.md;
			    			 if(_data.t){
			    				 
			    				 $.messager.alert('成功', _data.message,
									'info');
			    				 $("#groupsgrid").datagrid('reload');
			    				 
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
		
		$('#g_bcancel').click(function(){
			
			$('#addGroupsWin').window('close');
		});
		
		$('#g_name').textbox({
			onChange:function(newValue,oldValue){
				
				if(newValue==""){
       				return;
       			}
				
	        	//判断名称有没有重复
				if(_g_action_type==1){
					if(oldValue=="" && newValue!=""){
		        		return;
		        	}
				}
		        	
				var str = '';
					str += "gd.name=" + newValue;
					str += "&gd.code=";
				
	        	$.ajax({
					type: "POST",
					url: "<%=basePath%>groups/check.do?" + str,
					cache: false,
		        	dataType : "json",
		        	success:function(data){
		        		
		        		var _data =  data.md ;
		        		if(_data.t){
		        			
		        			_g_gn_right = true;
		        			$('#gn_wrong').hide();
		        			$('#gn_ok').show();
		        		}else{
		        			
		        			_g_gn_right = false;
		        			$('#gn_ok').hide();
		        			$('#gn_wrong').show();
		        			$.messager.alert('失败', _data.message,
							'info');
		        		}
		        	}
				});
	       	 }
		});
		
		$('#g_code').textbox({
			onChange:function(newValue,oldValue){
				
				if(newValue==""){
       				return;
       			}
       			
	        	//判断名称有没有重复
				if(_g_action_type==1){
				
					if(oldValue=="" && newValue!=""){
		        		return;
					}
		        }
		        	
				var str = '';
					str += "gd.code=" + newValue;
					str += "&gd.name="
				
	        	$.ajax({
					type: "POST",
					url: "<%=basePath%>groups/check.do?" + str,
					cache: false,
		        	dataType : "json",
		        	success:function(data){
		        		
		        		var _data =  data.md ;
		        		if(_data.t){
		        			
		        			_g_gc_right = true;
		        			$('#gc_wrong').hide();
		        			$('#gc_ok').show();
		        		}else{
		        			
		        			_g_gc_right = false;
		        			$('#gc_ok').hide();
		        			$('#gc_wrong').show();
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
	<table id="groupsgrid" cellspacing="0" cellpadding="0"
		class="easyui-datagrid"
		data-options="
					url:'<%=basePath%>groups/list.do', 
					title:'集团信息列表', 
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
			<a id="g_addEmp" href="#" class="easyui-linkbutton" plain="true"
				icon="icon-add">新增</a>
		</div>

		<div style="float: left;">
			<a id="g_editEmp" href="#" class="easyui-linkbutton" plain="true"
				icon="icon-save">编辑</a>
		</div>

		<div style="float: left;">
			<a id="g_deleteEmp" href="#" class="easyui-linkbutton" plain="true"
				icon="icon-remove">删除</a>
		</div>

		<div style="float: right; padding-left: 5px;">
			<input id="g_search" class="easyui-searchbox"
				data-options="prompt:'请输入查询员工姓名'" style="width: 150px;"></input>
		</div>
		
	</div>

	<!-- 添加集团信息窗口 -->
	<div id="addGroupsWin" class="easyui-window" title="员工编辑窗口"
		data-options="modal:true,closed:true,iconCls:'icon-save',minimizable:false,collapsible:false,maximizable:false"
		style="width: 450px; height: 260px; padding: 10px;">
		<div style="padding: 10px 60px 20px 60px">
			<form id="addGroupsForm" method="post">
				<table cellpadding="5">
					<tr>
						<td>名称:</td>
						<td><input id="g_name" class="easyui-textbox"
							style="width: 200px" type="text" name="f_g_name" required
							data-options=""></input>
							<img id="gn_ok" style="display:none;" src="<%=basePath%>icon/ok.png" />
							<img id="gn_wrong" style="display:none;" src="<%=basePath%>icon/wrong.png" /></td>
					</tr>
					<tr>
						<td>编码:</td>
						<td><input id="g_code" class="easyui-textbox"
							style="width: 200px" type="text" name="f_g_code" required
							data-options=""></input>
							<img id="gc_ok" style="display:none;" src="<%=basePath%>icon/ok.png" />
							<img id="gc_wrong" style="display:none;" src="<%=basePath%>icon/wrong.png" /></td>
					</tr>
					<tr>
						<td>备注:</td>
						<td><textarea id="g_remarks" rows=5 style="width:200px; height:50px;"
								name="f_g_remarks" class="textarea easyui-validatebox"}></textarea></td>
					</tr>
				</table>
			</form>
			<div style="text-align: center; padding: 5px">
				<a id="g_bsubmit" href="javascript:void(0)" style="width: 80px;"
					class="easyui-linkbutton">提交</a> <a id="g_bmodify"
					href="javascript:void(0)" style="width: 80px;"
					class="easyui-linkbutton">确认修改</a> <a id="g_bcancel"
					href="javascript:void(0)" style="width: 80px;"
					class="easyui-linkbutton">取消</a>
			</div>
		</div>
	</div>
</body>

</html>