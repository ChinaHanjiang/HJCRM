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
	//产品类型
	var _pc_id;
	var _pc_name;
	var _pc_code;
	var _pc_cn_right = false;
	var _pc_cc_right = false;
	var _pc_action_type = 0; /*0-新增，1-修改*/
	
	//产品
	var _p_id;
	var _p_name;
	var _p_code;
	var _p_shortCode;
	var _p_standarPrice;
	var _p_remarks;
	var _p_catalogId;
	var _p_catalog;
	var _p_userId;
	
	//产品组合
	var editIndex = undefined;
	var _pm_catalogName = undefined;
	var _pm_productName = undefined;
	var _pm_productCode = undefined;
	
	$(document).ready(function(){
		 
		/*初始化树形结构  begin*/
		var handleTree = function(str){
			str.replace("\"","");
			return str;
		};
		
		var _treeData;
		
		$.ajax({  
	        url : '<%=basePath%>catalog/list.do',
			type : 'post',
			data : null,
			dataType : 'json',
			success : function(data) {
				var _treeData = eval("(" + handleTree(data.pcTree) + ")");
				$('#productcatalogtree').tree({
					data :  _treeData ,
					lines : true,
					onClick : function(node) {
						var _f = node.isF;
						var _selectNodeId = node.id;
						var _pcId = node.id;
						var qp = $('#productgrid').datagrid('options').queryParams;
						qp.productCatalogId = _pcId;
						$("#productgrid").datagrid('reload');
					},
					onContextMenu: function(e,node){
						e.preventDefault();
						$(this).tree('select',node.target);
						$('#pc_menu').menu('show',{
							left: e.pageX,
							top: e.pageY
						});
					}
				});
			}
		});
	
		/*产品类型 begin*/
		$('#c_bsubmit').click(function(){
				
			var pc = $('#productcatalogtree');
			var node = pc.tree('getSelected');
			var nodeId = node.id;
			var pcName = $('#c_name').val().trim();
			var pcCode = $('#c_code').val().trim();
			
			var l = $('#addCatalogWinForm').form('enableValidation').form('validate');
			if(l){
				
				if(!_pc_cn_right){
					
					$.messager.alert('注意', '产品类型名称与已有存在冲突，请重新确认!',
					'info');
					return;
				}
				
				if(!_pc_cc_right){
					
					$.messager.alert('注意', '产品类型编码与已有存在冲突，请重新确认!',
					'info');
					return;
				}
				
				var str = '';
				str += "pcd.name=" + pcName;
				str += "&pcd.code=" + pcCode;
				str += "&pcd.parentId=" + nodeId;
				
				$.ajax({
					type: "POST",
					url: "<%=basePath%>catalog/add.do?" + str,
					cache: false,
		        	dataType : "json",
		        	success:function(data){
		        		
		        		var _data =  data.md ;
		        		if(_data.t){
		        			
		        			var _id = _data.intF;
		        			$.messager.alert('成功', _data.message,
							'info');
		        			$('#addCatalogWin').window('close');
		        			
		        			var newName = pcName ;
		        			
		        			pc.tree('append', {
								parent: (node?node.target:null),
								data: [{
									id:_id,
									text: newName,
									attributes:{"code":pcCode}
								}]
							});
		        		}else{
		        			$.messager.alert('失败', '添加类型失败!',
							'info');
		        		}
		        	}
				});
			} else {
				
				$.messager.alert('注意', '请完整填写信息!',
				'info');
			}
		});
		
		$('#c_bmodify').click(function(){
			
			var pc = $('#productcatalogtree');
			var node = pc.tree('getSelected');
			var nodeId = node.id;
			var parentNode = pc.tree('getParent',node.target);
			var parentId = parentNode.id;
			var pcName = $('#c_name').textbox('getText').trim();
			var pcCode = $('#c_code').textbox('getText').trim();
			
			var l = $('#addCatalogWinForm').form('enableValidation').form('validate');
			if(l){
				
				var isChanged = false;
				if(pcName != _pc_name){
					
					isChanged = true;
				}
				if(pcCode != _pc_code){
					
					isChanged = true;
				}
				
				if(isChanged){
					
					if(!_pc_cn_right){
						
						$.messager.alert('注意', '产品类型名称与已有存在冲突，请重新确认!',
						'info');
						return;
					}
					
					if(!_pc_cc_right){
						
						$.messager.alert('注意', '产品类型编码与已有存在冲突，请重新确认!',
						'info');
						return;
					}
					
					var str = '';
					str += "pcd.id=" + _pc_id;
					str += "&pcd.name=" + pcName;
					str += "&pcd.code=" + pcCode;
					str += "&pcd.parentId=" + parentId;
					
					$.ajax({
						type: "POST",
						url: "<%=basePath%>catalog/modify.do?" + str,
						cache: false,
			        	dataType : "json",
			        	success:function(data){
			        		
			        		var _data =  data.md ;
			        		if(_data.t){
			        			
			        			$.messager.alert('成功', _data.message,
								'info');
			        			
			        			var newName = pcName ;
			        			
			        			pc.tree('update', {
			        				target: node.target,
			        				text: newName,
			        				attributes:{'code':pcCode}
			        			});
			        			
			        			$('#addCatalogWin').window('close');
			        			
			        		}else{
			        			$.messager.alert('失败', '添加类型失败!',
								'info');
			        		}
			        	}
					});
				} else {
					
					$.messager.alert('注意', '请修改名称或编码内容!',
					'info');
				} 
			} else {
				
				$.messager.alert('注意', '请完整填写信息!',
				'info');
			}
		});
		
		$('#c_bcancel').click(function(){
			
			$('#addCatalogWin').window('close');
		});
		
		$('#addCatalogWin').window({
			onClose:function(){
				
				$('#c_name').textbox('setValue',"");
				$('#c_code').textbox('setValue',"");
				$('#cn_ok').hide();
				$('#cn_wrong').hide();
				$('#cc_ok').hide();
				$('#cc_wrong').hide();
				_pc_cn_right = false;
				_pc_cc_right = false;
			}
		});
		/*name*/
	    $('#c_name').textbox({
	        onChange:function(newValue,oldValue){
	        	
	    		if(newValue==""){
	   				return;
	   			}; 
	   			
	        	//判断名称有没有重复
	        	var pc = $('#productcatalogtree');
				var node = pc.tree('getSelected');
				var nodeId = node.id;
				var parentId = 0;
	        	
				if(_pc_action_type == 0){
					
					parentId = nodeId;
					
				} else {
					
					if(oldValue=="" && newValue!=""){
		        		return;
		        	};
		        	
					var parentNode = pc.tree('getParent',node.target);
					parentId = parentNode.id;
				}
				
				var str = '';
					str += "pcd.name=" + newValue;
					str += "&pcd.parentId=" + parentId;
				
	        	$.ajax({
					type: "POST",
					url: "<%=basePath%>catalog/check.do?" + str,
					cache: false,
		        	dataType : "json",
		        	success:function(data){
		        		
		        		var _data =  data.md ;
		        		if(_data.t){
		        			
		        			_pc_cn_right = true;
		        			$('#cn_wrong').hide();
		        			$('#cn_ok').show();
		        		}else{
		        			
		        			_pc_cn_right = false;
		        			$('#cn_ok').hide();
		        			$('#cn_wrong').show();
		        			$.messager.alert('失败', _data.message,
							'info');
		        		}
		        	}
				});
	        }
	    });
		/*l_code*/
		$('#c_code').textbox({
			onChange:function(newValue,oldValue){
				
				if(newValue==""){
	        		return;
	        	}
	        	
	        	//判断编码有没有重复的
	        	var pc = $('#productcatalogtree');
				var node = pc.tree('getSelected');
				var nodeId = node.id;
				var parentId = 0;
				
				if(_pc_action_type == 0){
					
					parentId = nodeId;
					
				} else {
					
					if(oldValue=="" && newValue!=""){
		        		return;
		        	}
					
					var parentNode = pc.tree('getParent',node.target);
					parentId = parentNode.id;
				}
				
				var str = '';
					str += "pcd.code=" + newValue;
					str += "&pcd.parentId=" + parentId;
	        	
	        	$.ajax({
					type: "POST",
					url: "<%=basePath%>catalog/check.do?" + str,
					cache: false,
		        	dataType : "json",
		        	success:function(data){
		        		
		        		var _data =  data.md ;
		        		if(_data.t){
		        			
		        			_pc_cc_right = true;
		        			$('#cc_wrong').hide();
		        			$('#cc_ok').show();
		        			
		        		}else{
		        			
		        			_pc_cc_right = false;
		        			$('#cc_ok').hide();
		        			$('#cc_wrong').show();
		        			$.messager.alert('失败', _data.message,
							'info');
		        		}
		        	}
				});
			}
		});
		
		$('#p_addGrid').click(function(){
			
			$('#p_bsubmit').show();
			$('#p_bmodify').hide();
			
			
			$('#p_addWin').window('open');
		});
		
		$('#p_editGrid').click(function(){
					
			$('#p_bmodify').show();
			$('#p_bsubmit').hide();
			
			var row = $('#productgrid').datagrid("getSelected");
			if(row!=null){
			
				_p_id = row.id;
				_p_name = row.name==null?"":row.name;
				_p_code = row.code==null?"":row.code;
				_p_shortCode = row.shortCode==null?"":row.shortCode;
				_p_catalogId = row.productCatalogId;
				_p_catalog = row.productCatalog;
				_p_standardPrice = row.standardPrice;
				_p_remarks = row.remarks==null?"":row.remarks;
				
				$('#pu_name').textbox('setText',_p_name);
				$('#pu_code').textbox('setText',_p_code);
				$('#pu_shortCode').textbox('setText',_p_shortCode);
				$('#pu_price').textbox('setText',_p_standardPrice);
				$('#pu_remarks').textbox('setText',_p_remarks);
				$('#pu_catalog').combotree('setValue',_p_catalog);
				
				$('#p_addWin').window('open');
				
			} else {
				
				$.messager.alert('注意', '请选择行数据!',
				'info');
			}
		});
		
		$('#p_removeGrid').click(function(){
			
			 var row = $('#productgrid').datagrid("getSelected");
			 if(row){
				 
				 $.messager.confirm('Confirm','您确定删除:"'+ row.name+'"的信息吗?',function(r){
					 
					    if (r){
					    	
					    	 var rowIndex = $('#productgrid').datagrid('getRowIndex', row);
							 $('#productgrid').datagrid('deleteRow',rowIndex);
							 
							 var p_id = row.id;
							 
							 $.ajax({
									type: "POST",
									url: "<%=basePath%>product/del.do?pd.id=" + p_id,
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
		
		$('#p_addWin').window({
			onClose:function(){
				
				$('#pu_catalog').combotree({
					url:'<%=basePath%>catalog/tree.do'
				});
				
				$('#pu_name').textbox('setText','');
				$('#pu_code').textbox('setText','');
				$('#pu_shortCode').textbox('setText','');
				$('#pu_catalog').combotree('setText','');
				$('#pu_standarprice').textbox('setText','');
				$('#pu_remarks').textbox('setText','');
			}
		});
		
		$('#p_bsubmit').click(function(){
			
			var p_name = $('#pu_name').textbox('getText');
			var cp_name = p_name.replace('#','%23');
			
			var p_code = $('#pu_code').textbox('getText');
			var cp_code = p_code.replace('#','%23');
			
			var p_shortCode = $('#pu_shortCode').textbox('getText');
			var cp_shortCode = p_shortCode.replace('#','%23');
			
			var p_standardPrice = $('#pu_price').textbox('getText');
			var p_remarks = $('#pu_remarks').textbox('getText');
			var node = $('#pu_catalog').combotree('getSelected');
			var p_catalogId = node.id;
			
			var l = $('#p_addWinForm').form('enableValidation').form('validate');
			if(l){
				
				var str = "";
				str += "pd.name=" + cp_name;
				str += "&pd.code=" + cp_code;
				str += "&pd.shortCode=" + cp_shortCode;
				str += "&pd.productCatalogId=" + p_catalogId;
				str += "&pd.standardPrice=" + p_standardPrice;
				str += "&pd.remarks=" + p_remarks;
				
				$.ajax({
					 type:"POST",
		    		 url:"<%=basePath%>product/add.do?" + str,
		    		 cache:false,
		    		 dataType:'json',
		    		 success:function(data){
		    			 
		    			 var _data = data.md;
		    			 if(_data.t){
		    				 
		    				 $.messager.alert('成功', _data.message,
								'info');
		    				 $("#productgrid").datagrid('reload');
		    				 
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
		
		$('#p_bmodify').click(function(){
			
			var p_name = $('#pu_name').textbox('getText');
			var cp_name = p_name.replace('#','%23');
			
			var p_code = $('#pu_code').textbox('getText');
			var cp_code = p_code.replace('#','%23');
			
			var p_shortCode = $('#pu_shortCode').textbox('getText');
			var cp_shortCode = p_shortCode.replace('#','%23');
			
			var p_standardPrice = $('#pu_price').textbox('getText');
			var p_remarks = $('#pu_remarks').textbox('getText');
			var p_catalog = $('#pu_catalog').combotree('getText');
			var p_catalogId;
			if(p_catalog != _p_catalog){
				
				var node = $('#pu_catalog').combotree('getSelected');
				alert(node);
				p_catalogId = node.id;
			} else {
				
				p_catalogId = _p_catalogId;
			}
			
			
			var str = '';
			var isChanged = false;
			
			var l = $('#p_addWinForm').form('enableValidation').form('validate');
			if(l){
				
				str += "pd.id=" + _p_id
				str += "&pd.name=" + cp_name;
				str += "&pd.code=" + cp_code;
				str += "&pd.shortCode=" + cp_shortCode;
				str += "&pd.productCatalogId=" + p_catalogId;
				str += "&pd.standardPrice=" + p_standardPrice;
				str += "&pd.remarks=" + p_remarks;
				
				if(_p_name != p_name){
					
					isChanged = true;
				}
				if(_p_code != p_code){
					
					isChanged = true;
				}
				if(_p_shortCode != p_shortCode){
					
					isChanged = true;
				}
				if(_p_standardPrice != p_standardPrice){
					
					isChanged = true;
				}
				if(_p_remarks != p_remarks){
					
					isChanged = true;
				}
				if(p_catalog != _p_catalog){
					
					isChanged = true;
				}
				
				if(isChanged){
					
					$.ajax({
						 type:"POST",
			    		 url:"<%=basePath%>product/modify.do?" + str,
			    		 cache:false,
			    		 dataType:'json',
			    		 success:function(data){
			    			 
			    			 var _data = data.md;
			    			 if(_data.t){
			    				 
			    				 $.messager.alert('成功', _data.message,
									'info');
			    				 $("#productgrid").datagrid('reload');
			    				 
			    			 } else {
			    				 
			    				 $.messager.alert('失败', _data.message,
									'info');
			    			 }
			    		 }
					});
					
				} else {
					
					$.messager.alert('注意', '产品你信息没有改变！',
					'info');
				}
				
			} else {
				$.messager.alert('注意', '请填完整的表单！',
				'info');
			}
		});
		
		$('#p_bcancel').click(function(){
			
			$('#p_addWin').window('close');
		});
		
		$('#productMixWin').window({
			onClose:function(){
				editIndex = undefined;
				_pm_catalogName = undefined;
				_pm_productName = undefined;
				_pm_productCode = undefined;
				_p_id = 0;
			}
		});
		
	});
</script>
<title>类型列表</title>

</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'west'" title="产品类型列表" style="width: 250px">
			<ul id="productcatalogtree"></ul>
		</div>
		<div id="pc_menu" class="easyui-menu" style="width: 120px;">
			<div onclick="pc_append()" data-options="iconCls:'icon-add'">添加</div>
			<div onclick="pc_modify()" data-options="iconCls:'icon-edit'">修改</div>
			<div onclick="pc_removeit()" data-options="iconCls:'icon-remove'">删除</div>
		</div>
		<div data-options="region:'center'">
			<table id="productgrid" class="easyui-datagrid" title="产品信息列表"
				data-options="
						url:'<%=basePath%>product/list.do',
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
						toolbar: '#p_tb' ,
						onDblClickRow:function(index,row){
						
							_p_id = row.id;
							var _productName = row.name;
							$('#productMixWin').window({
								title:_productName + ' -- 产品列表清单'
							});
							$('#productMixWin').window('open');
							$('#productMixGrid').datagrid('options').url = '<%=basePath%>pcf/list.do?pcfd.fpid = ' + _p_id;
							$('#productMixGrid').datagrid('reload');
						}
						">
				<thead>
					<tr>
						<th data-options="field:'ck',checkbox:true"></th>
						<th data-options="field:'id',hidden:true">序号</th>
						<th data-options="field:'shortCode',hidden:true">短编码</th>
						<th data-options="field:'code',width:200">产品编码</th>
						<th data-options="field:'name',width:200">名称</th>
						<th data-options="field:'productCatalogId',hidden:true"></th>
						<th data-options="field:'productCatalog',width:180">产品类型</th>
						<th data-options="field:'standardPrice',width:80">标准价</th>
						<th data-options="field:'mixNum',width:60">组成数</th>
						<th data-options="field:'telephone',width:120">创建者</th>
						<th data-options="field:'createTime',hidden:true,width:140">创建时间</th>
						<th data-options="field:'updateTime',hidden:true,width:140">修改时间</th>
						<th data-options="field:'remarks',width:120">备注</th>
					</tr>
				</thead>
			</table>
		</div>
		<div id="p_tb"
			style="border-bottom: 1px solid #ddd; height: 30px; padding: 3px 10px 0px 5px; background: #fafafa;">
			<div style="float: left;">
				<a id="p_addGrid" href="#" class="easyui-linkbutton" plain="true"
					icon="icon-add">新增</a>
			</div>

			<div style="float: left;">
				<a id="p_editGrid" href="#" class="easyui-linkbutton" plain="true"
					icon="icon-save"">编辑</a>
			</div>

			<div style="float: left;">
				<a id="p_removeGrid" href="#" class="easyui-linkbutton" plain="true"
					icon="icon-remove">删除</a>
			</div>
			
		</div>

		<!-- 添加产品窗口 -->
		<div id="p_addWin" class="easyui-window" title="产品编辑窗口"
			data-options="modal:true,closed:true,iconCls:'icon-save',minimizable:false,collapsible:false,maximizable:false"
			style="width: 450px; height: 500px; padding: 10px;">
			<div style="padding: 10px 60px 20px 60px">
				<form id="p_addWinForm" method="post">
					<table cellpadding="5">
						<tr id="p_name">
							<td>产品名称:</td>
							<td><input id="pu_name" class="easyui-textbox"
								style="width: 200px" type="text" name="f_p_name"
								data-options="editable:true,required:true"></input></td>
						</tr>
						<tr id="p_catalog">
							<td>产品类型:</td>
							<td>
								<input id="pu_catalog" class="easyui-combotree" name="f_p_catalog"
								data-options="url:'<%=basePath%>catalog/tree.do',method:'get'" style="width:100%">
							</td>
						</tr>
						<tr id="p_shotcode">
							<td>短编码:</td>
							<td><input id="pu_shortCode" class="easyui-textbox"
								style="width: 200px" type="text" name="f_p_shortCode"
								data-options="editable:true"></input></td>
						</tr>
						<tr id="p_code">
							<td>产品编码:</td>
							<td><input id="pu_code" class="easyui-textbox"
								style="width: 200px" type="text" name="f_p_code"
								data-options="editable:true"></input></td>
						</tr>
						<tr id="p_price">
							<td>标准价</td>
							<td><input id="pu_price" class="easyui-textbox"
								style="width: 200px" type="text" name="f_p_price"
								data-options="editable:true"></input></td>
						</tr>
						<tr id="c_remarks">
							<td>备注:</td>
							<td>
							<input id="pu_remarks" class="easyui-textbox"
								style="width: 200px; height:60px;" type="text" name="f_p_remarks"
								data-options="editable:true,multiline:true"></input>
						</tr>

					</table>
				</form>
				<div style="text-align: center; padding: 5px">
					<a id="p_bsubmit" href="javascript:void(0)" style="width: 80px;"
						class="easyui-linkbutton">提交</a> <a id="p_bmodify"
						href="javascript:void(0)" style="width: 80px;"
						class="easyui-linkbutton">确认修改</a> <a id="p_bcancel"
						href="javascript:void(0)" style="width: 80px;"
						class="easyui-linkbutton">取消</a>
				</div>
			</div>
		</div>

		<!-- 产品组合 -->
		<div id="productMixWin" class="easyui-window" title="产品组合列表"
			data-options="modal:true,closed:true,iconCls:'icon-save',minimizable:false,collapsible:false,maximizable:false"
			style="width: 620px; height: 300px; padding: 10px;">

			<div data-options="region:'center'">
				<table id="productMixGrid" class="easyui-datagrid"
					data-options="
						loadMsg:'数据加载中请稍后……',  
						rownumbers:true,
						singleSelect:true,
						autoRowHeight:true,
						autoRowWidth:true,
						sortOrder:'desc',
						toolbar: '#pm_tb' ,
						onClickCell: onClickCell,
						onEndEdit: onEndEdit
						">
					<thead>
						<tr>
							<th data-options="field:'id',hidden:true"></th>
							<th data-options="field:'productCatalogId',hidden:true"></th>
							<th data-options="field:'productCatalog',width:250,
							editor:{
								type:'combotree',
								options:{
									valueField:'id',
									textField:'text',
									method:'get',
									url:'<%=basePath%>catalog/tree.do',
									required:true,
									onClick:function(node){
										
										var id = node.id;
										_pm_catalogName = node.text;
										
										var row = $('#productMixGrid').datagrid('getSelected');
										var index = $('#productMixGrid').datagrid('getRowIndex', row);
										$('#productMixGrid').datagrid('getRows')[index]['productCatalogId'] = id;
										
										var ed = $('#productMixGrid').datagrid('getEditor', {index:index,field:'sproduct'});
										$(ed.target).combobox({
											url:'<%=basePath%>product/find.do?catalogId='+ id
										});
									}
								}
							}
									
						">产品类型</th>
							<th data-options="field:'spid',hidden:true"></th>
							<th data-options="field:'sproduct',width:150,
								formatter:function(value,row){
									return row.sproduct;
								},
								editor:{
									type:'combobox',
									options:{
										valueField:'id',
										textField:'text',
										method:'get',
										required:true,
										onSelect:function(record){
										
											var id = record.id;
											_pm_productName = record.text;
											
											var row = $('#productMixGrid').datagrid('getSelected');
											var index = $('#productMixGrid').datagrid('getRowIndex', row);
											$('#productMixGrid').datagrid('getRows')[index]['spid'] = id;
											
											$.ajax({
												type:'POST',
			    		 						url:'<%=basePath%>product/load.do?pd.id=' + id,
			    		 						cache:false,
			    		 						dataType:'json',
			    		 						success:function(data){
			    		 							
			    		 							_pm_productCode = data.pd.code;
			    		 							$('#productMixGrid').datagrid('getRows')[index]['code'] = _pm_productCode;
			    		 						}
											});
										}
									}
								}
							">名称</th>
							<th data-options="field:'code',width:120">产品编码</th>
							<th data-options="field:'quantity',width:40,
							editor:{
								type:'textbox',
								option:{
									required:true
								}
							}">数量</th>
						</tr>
					</thead>
				</table>
			</div>
			<div id="pm_tb"
				style="border-bottom: 1px solid #ddd; height: 30px; padding: 3px 10px 0px 5px; background: #fafafa;">
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()">增加</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit()">删除</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="accept()">提交</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="reject()">撤销</a>
			</div>
		</div>
		
		<!-- 添加产品类型窗口 -->
		<div id="addCatalogWin" class="easyui-window" title="添加产品类型"
			data-options="modal:true,closed:true,iconCls:'icon-save',minimizable:false,collapsible:false,maximizable:false"
			style="width: 480px; height: 160px; padding: 10px;">
			<div align="center">
			
				<form id="addCatalogWinForm" method="post">
					<table cellpadding="5">
						<tr>
							<td>名称:</td>
							<td><input id="c_name" class="easyui-textbox"
								style="width: 200px" type="text" name="cname"
								data-options="required:true"></input>
								<img id="cn_ok" style="display:none;" src="<%=basePath%>icon/ok.png" />
								<img id="cn_wrong" style="display:none;" src="<%=basePath%>icon/wrong.png" />
								</td>
						</tr>
						<tr>
							<td>编码:</td>
							<td><input id="c_code" class="easyui-textbox"
								style="width: 200px" type="text" name="ccode"
								data-options="required:true"></input>
								<img id="cc_ok" style="display:none;" src="<%=basePath%>icon/ok.png" />
								<img id="cc_wrong" style="display:none;" src="<%=basePath%>icon/wrong.png" />
								</td>
						</tr>
					</table>
				</form>
				<div style="text-align: center; padding: 5px">
					<a id="c_bsubmit" href="javascript:void(0)" style="width: 80px;"
						class="easyui-linkbutton">提交</a> 
					<a id="c_bmodify" href="javascript:void(0)" style="width: 80px;"
						class="easyui-linkbutton">修改</a> 
					<a id="c_bcancel"	href="javascript:void(0)" style="width: 80px;"
						class="easyui-linkbutton">取消</a>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		
		var pc_append = function(){
			
			_pc_action_type = 0;
			
			var pc = $('#productcatalogtree');
			var node = pc.tree('getSelected');
				
			$('#c_bsubmit').show();
			$('#c_bmodify').hide();
			$('#addCatalogWin').window('open');
				
		};
		
		var pc_modify = function(){
			
			_pc_action_type = 1;
			
			var pc = $('#productcatalogtree');
			var node = pc.tree('getSelected');
			var nodeId = node.id;
			var nodeName = node.text;
			var attributes = node.attributes;
			
			var pcName = nodeName;
			var pcCode = attributes.code;
			
			_pc_id = nodeId;
			_pc_name = pcName;
			_pc_code = pcCode;
			
			$('#c_name').textbox('setValue',pcName);
			$('#c_code').textbox('setValue',pcCode);
			
			$('#c_bsubmit').hide();
			$('#c_bmodify').show();
			$('#addCatalogWin').window('open');
		};
		
		var pc_removeit= function(){
			
			var pc = $('#productcatalogtree');
			var node = pc.tree('getSelected');
			var id = node.id;
			if(!pc.tree('isLeaf',node.target)){//判断是否是叶子节点
				$.messager.confirm('注意','是否删除根节点?',function(r){
				    if (r){
				    	$.ajax({
							type: "POST",
							url: "<%=basePath%>catalog/del.do?pcd.id=" + id,
							cache: false,
				        	dataType : "json",
				        	success:function(data){
				        		
				        		$.messager.alert('成功', '产品类型删除成功!',
								'info');
				        	}
						});
				    	
				    	pc.tree('remove',node.target);
				    }
				});
			} else {
				$.messager.confirm('注意','是否删除叶子节点?',function(r){
				    if (r){
				    	
				    	$.ajax({
							type: "POST",
							url: "<%=basePath%>catalog/del.do?pcd.id=" + id,
							cache: false,
				        	dataType : "json",
				        	success:function(data){
				        		
				        		$.messager.alert('成功', '产品类型删除成功!',
								'info');
				        	}
						});
				    	
				    	pc.tree('remove',node.target);
				    }
				});
			}
		};
		
		/*产品组合*/
		
		function endEditing(){
			if (editIndex == undefined){return true}
			if ($('#productMixGrid').datagrid('validateRow', editIndex)){
				$('#productMixGrid').datagrid('endEdit', editIndex);
				editIndex = undefined;
				return true;
			} else {
				return false;
			}
		}
		
		function onClickCell(index, field){
			
			if (editIndex != index){
				if (endEditing()){
					var row = $('#productMixGrid').datagrid('selectRow', index);
						row.datagrid('beginEdit', index);
						
					var pcId = $("#productMixGrid").datagrid("getSelected").productCatalogId;
					var ned = $('#productMixGrid').datagrid('getEditor', {index:index,field:'sproduct'});
					$(ned.target).combobox({
						url:"<%=basePath%>product/find.do?catalogId="+ pcId
					});		 			
					
					var ed = $('#productMixGrid').datagrid('getEditor', {index:index,field:field});
					
					if (ed){
						($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
					}
					editIndex = index;
				} else {
					
					setTimeout(function(){
						$('#productMixGrid').datagrid('selectRow', editIndex);
					},0);
				}
			}
		}
		
		function onEndEdit(index, row){
			
			if(_pm_catalogName != undefined){
				
				$("#productMixGrid").datagrid('getRows')[index]['productCatalog'] = _pm_catalogName;
				_pm_catalogName = undefined;
			}
			if(_pm_productName != undefined){
				
				$("#productMixGrid").datagrid('getRows')[index]['sproduct'] = _pm_productName;
				_pm_productName = undefined;
			}
		}
		
		function append(){
			if (endEditing()){
				$('#productMixGrid').datagrid('appendRow',{});
				editIndex = $('#productMixGrid').datagrid('getRows').length-1;
				$('#productMixGrid').datagrid('selectRow', editIndex)
						.datagrid('beginEdit', editIndex);
			}
		}
		
		function removeit(){
			
			if (editIndex == undefined){return}
			$('#productMixGrid').datagrid('cancelEdit', editIndex)
					.datagrid('deleteRow', editIndex);
			editIndex = undefined;
		}
		
		function accept(){
			if (endEditing()){
				
				var $pmg = $('#productMixGrid');
				var $pg = $('#productGrid');
                var rows = $pmg.datagrid('getChanges');
				 if (rows.length) {
                    var inserted = $pmg.datagrid('getChanges', "inserted");
                    var deleted = $pmg.datagrid('getChanges', "deleted");
                    var updated = $pmg.datagrid('getChanges', "updated");
                    var effectRow = new Object();
                    effectRow['productId'] = _p_id;
                    
                    if (inserted.length) {
                        effectRow["inserted"] = JSON.stringify(inserted);
                    }
                    if (deleted.length) {
                        effectRow["deleted"] = JSON.stringify(deleted);
                    }
                    if (updated.length) {
                        effectRow["updated"] = JSON.stringify(updated);
                    }
                    
                    $.post("<%=basePath%>pcf/savemix.do", effectRow, function (rsp) {
                        if (rsp) {
                        	
                            $pmg.datagrid('acceptChanges');
                            $("#productgrid").datagrid('reload');
                        }
                    }, "JSON").error(function () {
                        $.messager.alert("提示", "提交错误了！");
                    });
                }
				
			}
		}
		
		function reject(){
			$('#productMixGrid').datagrid('rejectChanges');
			editIndex = undefined;
		}
	</script>
</body>
</html>