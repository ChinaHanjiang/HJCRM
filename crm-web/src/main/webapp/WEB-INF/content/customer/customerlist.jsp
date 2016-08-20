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
	var _c_id;
	var _c_name;
	var _c_code;
	var _c_groupsId;
	var _c_groups;
	var _c_phone;
	var _c_fax;
	var _c_address;
	var _c_location;
	var _c_remarks;
	
	var setFareaId = function(locId){
		$('#cu_farea').combobox('setValue',locId);
	};
	
	var setSareaId = function(locId){
		$('#cu_sarea').combobox('setValue',locId);
	};
	
	var setTareaId = function(locId){
		$('#cu_tarea').combobox('setValue',locId);
	};
	
	$(document).ready(function(){
		 
		/*初始化树形结构  begin*/
		var handleTree = function(str){
			str.replace("\"","");
			return str;
		};
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
		/*初始化树形结构  end*/
		
		/*区域类型 begin*/
		/*大区域*/
		 $('#cu_farea').combobox({ 
	        editable:false, //不可编辑状态
	        required:true, 
	        cache: false,
	        panelHeight: 'auto',//自动高度适合
	  		onChange:function(){
	      		var _farea = $('#cu_farea').combobox('getValue');	
	      		if(_farea!=0){
	      			$.ajax({
			       		type: "POST",
			        	url: "<%=basePath%>loc/subloc.do?pid="+_farea,
			        	cache: false,
			        	dataType : "json",
			        	success: function(data){
		        			var _data = eval(data.subLocs);
		        			$("#cu_sarea").combobox("loadData",_data);
		                 }
			         });
	      		}else{
	      			
	      			$('#cu_sarea').combobox('clear');
	      		}
	          }
	      });

		/*二级区域*/
		$('#cu_sarea').combobox({ 
	      editable:false, //不可编辑状态
	      required:true, 
	      cache: false,
	      panelHeight: 'auto',//自动高度适合
	      valueField:'id',  
	      textField:'text',
	      onChange:function(){
	    	  var _sarea = $('#cu_sarea').combobox('getValue');
	    	  if(_sarea!=0){
		    	  $.ajax({
		    		  type:"POST",
		    		  url:"<%=basePath%>loc/subloc.do?pid=" + _sarea,
		    		  cache:false,
		    		  dataType:'json',
		    		  success:function(data){
		    			  	var _data = eval(data.subLocs);
		        			$("#cu_tarea").combobox("loadData",_data);
		    		  }
		    	  });
	    	  } else {
	    		  
	    		  $('#cu_tarea').combobox('clear');
	    	  }
		    }
		 });
		
		  /*三级区域*/
	    $('#cu_tarea').combobox({ 
	      editable:false, //不可编辑状态
	      required:true, 
	      cache: false,
	      panelHeight: 'auto',//自动高度适合
	      valueField:'id',   
	      textField:'text'
	    });
		/*区域类型 end*/
		
		/*集团信息 begin*/
		 $('#cu_groups').combobox({ 
	      editable:false, //不可编辑状态
	      cache: false,
	      panelHeight: 'auto'//自动高度适合
	    });
		/*集团信息 end*/
		
		$('#c_addWin').window({
			onClose:function(){
				
				$('#cu_code').textbox('setValue',"");
				$('#cu_name').textbox('setValue',"");
				$('#cu_group').textbox('setValue',"");
				$('#cu_phone').textbox('setValue',"");
				$('#cu_fax').textbox('setValue',"");
				$('#cu_address').textbox('setValue',"");
				$('#cu_remarks').val('');
				$('#cu_farea').combobox('setValue',0);
				$('#cu_sarea').combobox('clear');
				$('#cu_tarea').combobox('clear');
			}
		});
		
		/* 客户信息操作 begin*/
		
		$('#c_addGrid').click(function(){
			
			$('#c_bmodify').hide();
			$('#c_bsubmit').show();
			
			$('#cu_farea').combobox('setValue',0);
			$('#cu_groups').combobox('setValue',0);
			$('#cu_sarea').combobox('select',0);
			$('#cu_tarea').combobox('select',0);
			
			$('#c_addWin').window('open');
			
		});
		
		$('#c_editGrid').click(function(){
			
			$('#c_bsubmit').hide();
			$('#c_bmodify').show();
			
			var row = $('#customergrid').datagrid("getSelected");
			if(row!=null){
				
				_c_id = row.id;
				_c_name = row.name;
				_c_code = row.code;
				_c_groupsId = row.groupsId;
				_c_groups = row.groups;
				_c_phone = row.telephone;
				_c_fax = row.fax;
				_c_address = row.address;
				_c_location = row.locId;
				_c_remarks = row.remarks;
				
				$('#cu_name').textbox('setValue',_c_name);
				$('#cu_code').textbox('setValue',_c_code);
				$('#cu_phone').textbox('setValue',_c_phone);
				$('#cu_fax').textbox('setValue',_c_fax);
				$('#cu_address').textbox('setValue',_c_address);
				$('#cu_remarks').val(_c_remarks);
				
				$.ajax({
		    		  type:"POST",
		    		  url:"<%=basePath%>loc/parloc.do?locId=" + _c_location,
		    		  cache:false,
		    		  dataType:'json',
		    		  success:function(data){
		    			  
		    			 var str = data.parentLocs;
		    			 var s = str.split('-');
		    			 
		    			 setTimeout("setFareaId(" + s[0] + ")",500);
		    			 setTimeout("setSareaId(" + s[1] + ")",700);
		    		  }
		    	  });
				
				setTimeout("setTareaId(" + _c_location + ")",900);
				
				$('#cu_groups').combobox('setValue',_c_groupsId);
				
				$('#c_addWin').window('open');
				
			} else {
				
				$.messager.alert('注意', '请选择行数据!',
				'info');
			}
		});
		
		/*删除客户*/
		$('#c_removeGrid').click(function(){
			
			 var row = $('#customergrid').datagrid("getSelected");
			 if(row){
				 
				 var rowIndex = $('#customergrid').datagrid('getRowIndex', row);
				 $('#customergrid').datagrid('deleteRow',rowIndex);
				 
				 var c_id = row.id;
				 
				 $.ajax({
						type: "POST",
						url: "<%=basePath%>customer/del.do?cd.id=" + c_id,
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
		
		//客户表单提交
		$('#c_bsubmit').click(function(){
			
			var c_name;
			var c_code;
			var c_phone;
			var c_fax;
			var c_address;
			var c_remarks;
			var c_groupsId;
			var c_locationId;
			
			c_name = $('#cu_name').textbox('getText');
			c_code = $('#cu_code').textbox('getText');
			c_phone = $('#cu_phone').textbox('getText');
			c_fax = $('#cu_fax').textbox('getText');
			c_address = $('#cu_address').textbox('getText');
			c_remarks = $('#cu_remarks').val();
			c_groupsId = $('#cu_groups').combobox('getValue');
			c_locationId = $('#cu_tarea').combobox('getValue');
			
			var l = $('#c_addWinForm').form('enableValidation').form('validate');
			if(l){
				
				var str = "cd.name=" + c_name + "&cd.code=" + c_code + "&cd.telephone="
							+ c_phone + "&cd.fax=" + c_fax + "&cd.address=" + c_address
							+ "&cd.remarks=" + c_remarks + "&cd.locId=" + c_locationId
							+ "&cd.groupsId=" + c_groupsId;
				
				$.ajax({
					 type:"POST",
		    		 url:"<%=basePath%>customer/add.do?" + str,
		    		 cache:false,
		    		 dataType:'json',
		    		 success:function(data){
		    			 
		    			 var _data = data.md;
		    			 if(_data.t){
		    				 
		    				 $.messager.alert('成功', _data.message,
								'info');
		    				 $("#customergrid").datagrid('reload');
		    				 
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
		
		//客户表单修改
		$('#c_bmodify').click(function(){
			
			var c_id;
			var c_name;
			var c_code;
			var c_phone;
			var c_fax;
			var c_address;
			var c_remarks;
			var c_groupsId;
			var c_locationId;
			
			
			c_name = $('#cu_name').textbox('getText');
			c_code = $('#cu_code').textbox('getText');
			c_phone = $('#cu_phone').textbox('getText');
			c_fax = $('#cu_fax').textbox('getText');
			c_address = $('#cu_address').textbox('getText');
			c_remarks = $('#cu_remarks').val();
			c_groupsId = $('#cu_groups').combobox('getValue');
			c_locationId = $('#cu_tarea').combobox('getValue');
			
			var str = '';
			var isChanged = false;
			
			var l = $('#c_addWinForm').form('enableValidation').form('validate');
			if(l){
				
				str += "cd.id=" + _c_id
				str += "&cd.name=" + c_name;
				str += "&cd.code=" + c_code;
				str += "&cd.telephone="	+ c_phone;
				str += "&cd.fax=" + c_fax;
				str += "&cd.address=" + c_address;
				str += "&cd.remarks=" + c_remarks;
				str += "&cd.locId=" + c_locationId;
				str += "&cd.groupsId=" + c_groupsId;
				
				if(_c_name != c_name){
					
					isChanged = true;
				}
				if(_c_code != c_code){
					
					isChanged = true;
				}
				if(_c_phone != c_phone){
					
					isChanged = true;
				}
				if(_c_fax != c_fax){
					
					isChanged = true;
				}
				if(_c_address != c_address){
					
					isChanged = true;
				}
				if(_c_remarks != c_remarks){
					
					isChanged = true;
				}
				if(_c_location != c_locationId){
					
					isChanged = true;
				}
				if(_c_groupsId != c_groupsId){
					
					isChanged = true;
				}
				
				if(isChanged){
					
					$.ajax({
						 type:"POST",
			    		 url:"<%=basePath%>customer/modify.do?" + str,
			    		 cache:false,
			    		 dataType:'json',
			    		 success:function(data){
			    			 
			    			 var _data = data.md;
			    			 if(_data.t){
			    				 
			    				 $.messager.alert('成功', _data.message,
									'info');
			    				 $("#customergrid").datagrid('reload');
			    				 
			    			 } else {
			    				 
			    				 $.messager.alert('失败', _data.message,
									'info');
			    			 }
			    		 }
					});
					
				} else {
					
					$.messager.alert('注意', '客户信息没有改变！',
					'info');
				}
				
			} else {
				$.messager.alert('注意', '请填完整的表单！',
				'info');
			}
		});
		
		$('#c_bcancel').click(function(){
			
			$('#c_addWin').window('close');
		});
		
		
		/* 客户信息操作 begin*/
		
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
						rownumbers:true,
						singleSelect:true,
						collapsible:false,
						autoRowHeight:true,
						autoRowWidth:true,
						pagination:true,
						pageNumber:1,
						pageSize:20,
						sortOrder:'desc',
						toolbar: '#c_tb' ,
						onDblClickRow:function(index,row){
						
							$('#contactWin').window('open');
							var c_id = row.id;
							$('#contactgrid').datagrid('options').url = '<%=basePath%>contact/list.do?customerId = ' + c_id;
							$('#contactgrid').datagrid('reload');
						}
						">
				<thead>
					<tr>
						<th data-options="field:'ck',checkbox:true"></th>
						<th data-options="field:'id',hidden:true">序号</th>
						<th data-options="field:'code',width:70">客户编码</th>
						<th data-options="field:'name',width:150">名称</th>
						<th data-options="field:'groupsId',hidden:true"></th>
						<th data-options="field:'groups',width:80">隶属集团</th>
						<th data-options="field:'locId',hidden:true"></th>
						<th data-options="field:'location',width:80">区域</th>
						<th data-options="field:'address',width:250">地址</th>
						<th data-options="field:'telephone',width:80">电话</th>
						<th data-options="field:'fax',width:80">传真</th>
						<th data-options="field:'createTime',width:140">创建时间</th>
						<th data-options="field:'updateTime',width:140">修改时间</th>
						<th data-options="field:'remarks',width:150">备注</th>
					</tr>
				</thead>
			</table>
		</div>
		<div id="c_tb"
			style="border-bottom: 1px solid #ddd; height: 30px; padding: 3px 10px 0px 5px; background: #fafafa;">
			<div style="float: left;">
				<a id="c_addGrid" href="#" class="easyui-linkbutton" plain="true"
					icon="icon-add">新增</a>
			</div>

			<div style="float: left;">
				<a id="c_editGrid" href="#" class="easyui-linkbutton" plain="true"
					icon="icon-save"">编辑</a>
			</div>

			<div style="float: left;">
				<a id="c_removeGrid" href="#" class="easyui-linkbutton" plain="true"
					icon="icon-remove">删除</a>
			</div>
			<!-- 
			<div style="float: left;">
				<a id="c_checkContact" href="#" class="easyui-linkbutton" plain="true"
					icon="icon-remove">查看联系人</a>
			</div>
			 -->
			<div style="float: left;">
				<a id="c_export" href="#" class="easyui-linkbutton" plain="true"
					icon="icon-remove">导出excel</a>
			</div>
			<div style="float: right; padding-right: 10px;">
				客户：<input id="c_search" class="easyui-searchbox"
					data-options="prompt:'请输入查询客户名称'" style="width: 150px;"></input>
			</div>

		</div>

		<!-- 添加客户窗口 -->
		<div id="c_addWin" class="easyui-window" title="公司客户编辑窗口"
			data-options="modal:true,closed:true,iconCls:'icon-save',minimizable:false,collapsible:false,maximizable:false"
			style="width: 450px; height: 500px; padding: 10px;">
			<div style="padding: 10px 60px 20px 60px">
				<form id="c_addWinForm" method="post">
					<table cellpadding="5">
						<tr id="c_name">
							<td>客户名称:</td>
							<td><input id="cu_name" class="easyui-textbox"
								style="width: 200px" type="text" name="f_c_name"
								data-options="editable:true,required:true"></input></td>
						</tr>
						<tr id="c_location">
							<td>区域:</td>
							<td><select id="cu_farea" class="easyui-combobox"
								style="width: 70px" name="f_c_farea">
									<option value=0 selected="selected">请选择</option>
									<s:if test="locations!=null">
										<s:iterator value="locations" var="d">
											<option value=${d.id }>${d.name }</option>
										</s:iterator>
									</s:if>
							</select> <select id="cu_sarea" class="easyui-combobox"
								style="width: 70px" name="f_c_sarea">
								<option value=0 selected="selected">请选择</option>
							</select> <select id="cu_tarea" class="easyui-combobox"
								style="width: 70px" name="f_c_tarea">
								<option value=0 selected="selected">请选择</option>
							</select></td>
						</tr>
						<tr id="c_phone">
							<td>固定电话:</td>
							<td><input id="cu_phone" class="easyui-textbox"
								style="width: 200px" type="text" name="f_c_phone"
								data-options="editable:true"></input></td>
						</tr>
						<tr id="c_fax">
							<td>传真电话:</td>
							<td><input id="cu_fax" class="easyui-textbox"
								style="width: 200px" type="text" name="f_c_fax"
								data-options="editable:true"></input></td>
						</tr>
						<tr id="c_address">
							<td>邮寄地址:</td>
							<td><input id="cu_address" class="easyui-textbox"
								style="width: 200px" type="text" name="f_c_address"
								data-options="editable:true"></input></td>
						</tr>
						<tr id="c_group">
							<td>隶属集团:</td>
							<td><select id="cu_groups" class="easyui-combobox"
								style="width: 200px" name="f_c_groups">
								<option value=0 selected="selected">请选择</option>
									<s:if test="groups!=null">
										<s:iterator value="groups" var="g">
											<option value=${g.id }>${g.name }</option>
										</s:iterator>
									</s:if>
							</select></td>
						</tr>
						<tr id="c_code">
							<td>客户编码:</td>
							<td><input id="cu_code" class="easyui-textbox"
								style="width: 200px" type="text" name="f_c_code"
								data-options="editable:true,required:true"></td>
						</tr>
						<tr id="c_remarks">
							<td>备注:</td>
							<td><textarea id="cu_remarks" rows=5 style="width: 200px"
									name="f_c_remarks" class="textarea easyui-validatebox"}></textarea></td>
						</tr>

					</table>
				</form>
				<div style="text-align: center; padding: 5px">
					<a id="c_bsubmit" href="javascript:void(0)" style="width: 80px;"
						class="easyui-linkbutton">提交</a> <a id="c_bmodify"
						href="javascript:void(0)" style="width: 80px;"
						class="easyui-linkbutton">确认修改</a> <a id="c_bcancel"
						href="javascript:void(0)" style="width: 80px;"
						class="easyui-linkbutton">取消</a>
				</div>
			</div>
		</div>

		<!-- 联系人 -->
		<div id="contactWin" class="easyui-window" title="客户联系人列表"
			data-options="modal:true,closed:true,iconCls:'icon-save',minimizable:false,collapsible:false,maximizable:false"
			style="width: 900px; height: 300px; padding: 10px;">

			<div data-options="region:'center'">
				<table id="contactgrid" class="easyui-datagrid"
					data-options="
						loadMsg:'数据加载中请稍后……',  
						rownumbers:true,
						singleSelect:true,
						collapsible:false,
						autoRowHeight:true,
						autoRowWidth:true,
						pagination:true,
						pageNumber:1,
						pageSize:10,
						sortOrder:'desc',
						toolbar: '#ct_tb' 
						">
					<thead>
						<tr>
							<th data-options="field:'ck',checkbox:true"></th>
							<th data-options="field:'id',hidden:true">序号</th>
							<th data-options="field:'name',width:80">姓名</th>
							<th data-options="field:'sex',width:30">性别</th>
							<th data-options="field:'phone',width:120">手机</th>
							<th data-options="field:'email',width:120">邮箱</th>
							<th data-options="field:'duty',width:80">职务</th>
							<th data-options="field:'createTime',width:120">创建时间</th>
							<th data-options="field:'updateTime',width:120">修改时间</th>
							<th data-options="field:'remarks',width:50">备注</th>
						</tr>
					</thead>
				</table>
			</div>
			<div id="ct_tb"
				style="border-bottom: 1px solid #ddd; height: 30px; padding: 3px 10px 0px 5px; background: #fafafa;">
				<div style="float: left;">
					<a id="ct_addGrid" href="#" class="easyui-linkbutton" plain="true"
						icon="icon-add">新增</a>
				</div>

				<div style="float: left;">
					<a id="ct_editGrid" href="#" class="easyui-linkbutton" plain="true"
						icon="icon-save"">编辑</a>
				</div>

				<div style="float: left;">
					<a id="ct_removeGrid" href="#" class="easyui-linkbutton"
						plain="true" icon="icon-remove">删除</a>
				</div>
				<div style="float: left;">
					<a id="ct_export" href="#" class="easyui-linkbutton" plain="true"
						icon="icon-remove">导出excel</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>