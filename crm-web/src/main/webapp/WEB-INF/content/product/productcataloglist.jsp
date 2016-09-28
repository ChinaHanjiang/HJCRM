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
	var _pc_ename;
	var _pc_code;
	var _pc_parentId = 1;
	var _pc_cn_right = false;
	var _pc_cc_right = false;
	var _pc_action_type = 0; /*0-新增，1-修改*/
	
	$(document).ready(function(){
		
		$('#productcatalogtree').tree({
			url:'<%=basePath%>catalog/tree.do',
			method:'get',
			editable:true,
			lines : true,
			onClick : function(node) {
				var _f = node.isF;
				var _selectNodeId = node.id;
				var _pcId = node.id;
				_pc_parentId = _pcId;
				$("#productcatalogtree").tree('expand',node.target);
				var qp = $('#productCatalogGrid').datagrid('options').queryParams;
				qp.parentCatalogId = _pcId;
				$("#productCatalogGrid").datagrid('reload');
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
		$('#c_ccode').textbox({
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
		
		$('#addCatalogWin').window({
			
			onClose:function(){
				
				$('#c_cname').textbox('setText','');
				$('#c_ename').textbox('setText','');
				$('#c_code').textbox('setText','');
			}
		});
		
		$('#pc_addGrid').click(function(){
			
			$('#pc_bsubmit').show();
			$('#pc_bmodify').hide();
			$('#addCatalogWin').window('open');
		});
		
		$('#pc_editGrid').click(function(){
			
			$('#pc_bmodify').show();
			$('#pc_bsubmit').hide();
			
			var row = $('#productCatalogGrid').datagrid("getSelections");
			if(row!=null && row.length==1){
			
				_pc_id = row[0].id;
				_pc_name = row[0].name;
				_pc_ename = row[0].ename;
				_pc_code = row[0].code;
				
				$('#c_cname').textbox('setText',_pc_name);
				$('#c_ename').textbox('setText',_pc_ename);
				$('#c_code').textbox('setText',_pc_code);
				
				$('#addCatalogWin').window('open');
				
			} else {
				
				$.messager.alert('注意', '请选择行数据!',
				'info');
			}
			
		});
		
		$('#pc_removeGrid').click(function(){
			
			var row = $('#productCatalogGrid').datagrid("getSelections");
			if(row!=null){
				
				var id = new Array();
		    	
		    	$.each(row,function(i, o){
					 
					 id[i] = o.id;
				});
		    	
		    	var pc = $('#productcatalogtree');
		    	
		    	$.messager.confirm('注意','是否删除节点及子节点?',function(r){
				    if (r){
				    	
				    	$.ajax({
							type: "POST",
							url: "<%=basePath%>catalog/del.do?pcd.ids=" + id,
							cache: false,
				        	dataType : "json",
				        	success:function(data){
				        		
				        		$.messager.alert('成功', '产品类型删除成功!',
								'info');
				        		
								$.each(row,function(i, o){
									
									var n = pc.tree('find',o.id);
									if(n!=null){
										
										pc.tree('remove',n.target);
									}
									
									 var rowIndex = $('#productCatalogGrid').datagrid('getRowIndex', o);
									 $('#productCatalogGrid').datagrid('deleteRow',rowIndex);
								});
				        	}
						});
				    }
				});
			}
		});
		
		$('#pc_bsubmit').click(function(){
			
			var pc = $('#productcatalogtree');
			var node = pc.tree('getSelected');
			if(node!=null)
				_pc_parentId = node.id;
			var pc_name = $('#c_cname').textbox('getText')
			var pc_ename = $('#c_ename').textbox('getText');
			var pc_code = $('#c_code').textbox('getText');
			
			var l = $('#addCatalogWinForm').form('enableValidation').form('validate');
			if(l){
				
				var str = '';
				str += "pcd.name=" + pc_name;
				str += "&pcd.ename=" + pc_ename;
				str += "&pcd.code=" + pc_code;
				str += "&pcd.parentId=" + _pc_parentId;
				
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
		        			$('#productCatalogGrid').datagrid('reload');
		        			
		        			var newName = pc_name ;
		        			
		        			pc.tree('append', {
								parent: (node?node.target:null),
								data: [{
									id:_id,
									text: newName
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
		
		$('#pc_bmodify').click(function(){
			
			var pc = $('#productcatalogtree');
			
			var pc_name = $('#c_cname').textbox('getText')
			var pc_ename = $('#c_ename').textbox('getText');
			var pc_code = $('#c_code').textbox('getText');
			
			var l = $('#addCatalogWinForm').form('enableValidation').form('validate');
			if(l){
				
				var isChanged = false;
				if(pc_name != _pc_name){
					
					isChanged = true;
				}
				if(pc_name != _pc_name){
					
					isChanged = true;
				}
				if(pc_name != _pc_name){
					
					isChanged = true;
				}
				
				if(isChanged){
					
					var str = '';
					str += "pcd.id=" + _pc_id;
					str += "&pcd.name=" + pc_name;
					str += "&pcd.ename=" + pc_ename;
					str += "&pcd.code=" + pc_code;
					str += "&pcd.parentId=" + _pc_parentId;
					
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
			        			
			        			var newName = pc_name ;
			        			
			        			var node = pc.tree('find',_pc_id);
			        			
			        			pc.tree('update', {
			        				target: node.target,
			        				text: newName
			        			});
			        			
			        			$('#addCatalogWin').window('close');
			        			$('#productCatalogGrid').datagrid('reload');
			        			
			        		}else{
			        			$.messager.alert('失败', '添加类型失败!',
								'info');
			        		}
			        	}
					});
				} else {
					
					$.messager.alert('注意', '信息没有修改，请确认!',
					'info');
				} 
			} else {
				
				$.messager.alert('注意', '请完整填写信息!',
				'info');
			}
		});
		
		$('#pc_bcancel').click(function(){
			
			$('#addCatalogWin').window('close');
		});
		
		$('#pc_backGrid').click(function(){
			
			_pc_parentId = 1;
			
			var qp = $('#productCatalogGrid').datagrid('options').queryParams;
			qp.parentCatalogId = _pc_parentId;
			
			$("#productCatalogGrid").datagrid('reload');
			
			$('#productcatalogtree').tree('collapseAll',null);
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
		<div data-options="region:'center'">
			<table id="productCatalogGrid" class="easyui-datagrid" title="产品类别列表"
				data-options="
						url:'<%=basePath%>catalog/gridlist.do',
						loadMsg:'数据加载中请稍后……',  
						rownumbers:true,
						singleSelect:false,
						collapsible:false,
						autoRowHeight:true,
						autoRowWidth:true,
						pagination:true,
						pageNumber:1,
						pageSize:20,
						toolbar: '#pc_tb'
						">
				<thead>
					<tr>
						<th data-options="field:'ck',checkbox:true"></th>
						<th data-options="field:'id',hidden:true">序号</th>
						<th data-options="field:'code',width:160">产品编码</th>
						<th data-options="field:'name',width:160">中文名称</th>
						<th data-options="field:'ename',width:160">英文名称</th>
						<th data-options="field:'telephone',width:80">创建者</th>
						<th data-options="field:'createTime',width:140">创建时间</th>
						<th data-options="field:'updateTime',width:140">修改时间</th>
						<th data-options="field:'remarks',width:120">备注</th>
					</tr>
				</thead>
			</table>
		</div>
		<div id="pc_tb"
			style="border-bottom: 1px solid #ddd; height: 30px; padding: 3px 10px 0px 5px; background: #fafafa;">
			<div style="float: left;">
				<a id="pc_backGrid" href="#" class="easyui-linkbutton" plain="true"
					icon="icon-add">刷新</a>
			</div>
			
			<div style="float: left;">
				<a id="pc_addGrid" href="#" class="easyui-linkbutton" plain="true"
					icon="icon-add">新增</a>
			</div>

			<div style="float: left;">
				<a id="pc_editGrid" href="#" class="easyui-linkbutton" plain="true"
					icon="icon-save"">编辑</a>
			</div>

			<div style="float: left;">
				<a id="pc_removeGrid" href="#" class="easyui-linkbutton" plain="true"
					icon="icon-remove">删除</a>
			</div>
			
		</div>
		
		<!-- 添加产品类型窗口 -->
		<div id="addCatalogWin" class="easyui-window" title="添加产品类型"
			data-options="modal:true,closed:true,iconCls:'icon-save',minimizable:false,collapsible:false,maximizable:false"
			style="width: 480px; height: 220px; padding: 10px;">
			<div align="center">
			
				<form id="addCatalogWinForm" method="post">
					<table cellpadding="5">
						<tr>
							<td>中文名称:</td>
							<td><input id="c_cname" class="easyui-textbox"
								style="width: 200px" type="text" name="ccname"
								data-options="required:true"></input>
								<img id="ccn_ok" style="display:none;" src="<%=basePath%>icon/ok.png" />
								<img id="ccn_wrong" style="display:none;" src="<%=basePath%>icon/wrong.png" />
								</td>
						</tr>
						<tr>
							<td>英文名称:</td>
							<td><input id="c_ename" class="easyui-textbox"
								style="width: 200px" type="text" name="cename"
								data-options="required:true"></input>
								<img id="cen_ok" style="display:none;" src="<%=basePath%>icon/ok.png" />
								<img id="cen_wrong" style="display:none;" src="<%=basePath%>icon/wrong.png" />
								</td>
						</tr>
						<tr>
							<td>编&nbsp;&nbsp;&nbsp;&nbsp;码:</td>
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
					<a id="pc_bsubmit" href="javascript:void(0)" style="width: 80px;"
						class="easyui-linkbutton">提交</a> 
					<a id="pc_bmodify" href="javascript:void(0)" style="width: 80px;"
						class="easyui-linkbutton">修改</a> 
					<a id="pc_bcancel"	href="javascript:void(0)" style="width: 80px;"
						class="easyui-linkbutton">取消</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>