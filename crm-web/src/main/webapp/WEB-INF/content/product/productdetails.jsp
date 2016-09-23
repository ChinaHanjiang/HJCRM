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
	
	//产品组合
	var _p_id = ${product.id};
	var editIndex = undefined;
	var _pm_catalogName = undefined;
	var _pm_productName = undefined;
	var _pm_productCode = undefined;
	
	//产品属性
	var _pp_id;
	var _pp_name;
	var _pp_value;
	var _pp_ename;
	var _pp_evalue;
	var _pp_remarks;
	
	$(document).ready(function(){
		
		$('#addPropertyWin').window({
			
			onClose:function(){
				
				$('#pp_name').textbox('setText',"");
				$('#pp_value').textbox('setText',"");
				$('#pp_ename').textbox('setText',"");
				$('#pp_evalue').textbox('setText',"");
				$('#pp_remarks').textbox('setText',"");
			}
		});
		
		$('#pp_addGrid').click(function(){
			
			$('#pp_bsubmit').show();
			$('#pp_bmodify').hide();
			
			
			$('#addPropertyWin').window('open');
		});
		
		$('#pp_editGrid').click(function(){
			
			$('#pp_bmodify').show();
			$('#pp_bsubmit').hide();
			
			var row = $('#productPropertyGrid').datagrid("getSelections");
			if(row!=null && row.length==1){
			
				_pp_id = row[0].id;
				_pp_name = row[0].name==null?"":row[0].name;
				_pp_value = row[0].value==null?"":row[0].value;
				_pp_ename = row[0].ename==null?"":row[0].ename;
				_pp_evalue = row[0].evalue==null?"":row[0].evalue;
				_pp_remarks = row[0].remarks==null?"":row[0].remarks;
				
				$('#pp_name').textbox('setText',_pp_name);
				$('#pp_value').textbox('setText',_pp_value);
				$('#pp_ename').textbox('setText',_pp_ename);
				$('#pp_evalue').textbox('setText',_pp_evalue);
				$('#pp_remarks').textbox('setText',_pp_remarks);
				
				$('#addPropertyWin').window('open');
				
			} else {
				
				$.messager.alert('注意', '请选择行数据!',
				'info');
			}
		});
		
		$('#pp_removeGrid').click(function(){
			
			 var row = $('#productPropertyGrid').datagrid("getSelections");
			 if(row!=null){
				 
				 $.messager.confirm('Confirm','您确定删除:"'+ row.length+'"行信息吗?',function(r){
					 
					    if (r){
					    	
							var id = new Array();
					    	
					    	$.each(row,function(i, o){
							   
					    		 var rowIndex = $('#productPropertyGrid').datagrid('getRowIndex', o);
								 $('#productPropertyGrid').datagrid('deleteRow',rowIndex);
								 
								 id[i] = o.id;
							});
							 
							$.ajax({
								type: "POST",
								url: "<%=basePath%>productproperty/del.do?ppd.id=" + p_id,
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
		
		$('#pp_bsubmit').click(function(){
			
			var pp_name = $('#pp_name').textbox('getText');
			var pp_value = $('#pp_value').textbox('getText');
			var pp_ename = $('#pp_ename').textbox('getText');
			var pp_value = $('#pp_evalue').textbox('getText');
			var pp_remarks = $('#pp_remarks').textbox('getText');
			
			var l = $('#addPropertyWinForm').form('enableValidation').form('validate');
			if(l){
				
				var str = "";
				str += "ppd.name=" + pp_name;
				str += "&ppd.value=" + pp_value;
				str += "&ppd.ename=" + pp_ename;
				str += "&ppd.evalue=" + pp_evalue;
				str += "&ppd.remarks=" + pp_remarks;
				
				$.ajax({
					 type:"POST",
		    		 url:"<%=basePath%>productproperty/add.do?" + str,
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
		
		$('#pp_bmodify').click(function(){
			
			var pp_name = $('#pp_name').textbox('getText');
			var pp_value = $('#pp_value').textbox('getText');
			var pp_ename = $('#pp_ename').textbox('getText');
			var pp_value = $('#pp_evalue').textbox('getText');
			var pp_remarks = $('#pp_remarks').textbox('getText');
			
			var str = '';
			var isChanged = false;
			
			var l = $('#addPropertyWinForm').form('enableValidation').form('validate');
			if(l){
				
				str += "ppd.id=" + _p_id;
				str += "&ppd.name=" + pp_name;
				str += "&ppd.value=" + pp_value;
				str += "&ppd.ename=" + pp_ename;
				str += "&ppd.evalue=" + pp_evalue;
				str += "&ppd.remarks=" + pp_remarks;
				
				if(_pp_name != pp_name){
					
					isChanged = true;
				}
				if(_pp_value != pp_value){
					
					isChanged = true;
				}
				if(_pp_ename != pp_ename){
									
					isChanged = true;
				}
				if(_pp_evalue != pp_evalue){
					
					isChanged = true;
				}
				if(_pp_remarks != pp_remarks){
					
					isChanged = true;
				}
				
				if(isChanged){
					
					$.ajax({
						 type:"POST",
			    		 url:"<%=basePath%>productproperty/modify.do?" + str,
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
		
		$('#pp_bcancel').click(function(){
			
			$('#addPropertyWin').window('close');
		});
		
	});
</script>
<title>类型列表</title>

</head>
<body>
	<div align="left">
		<div class="easyui-panel" title="产品详情" style="width: 620px; padding-top: 10px;">
			
			<table
					style="width: 600px; margin-right: auto; margin-left: auto; font-family: '宋体'; font-size: 13px;">
					<tr>
						<td style="width: 80px; height: 20px;">产品编号:</td>
						<td colspan="3">${product.code }</td>
						
					</tr>
					<tr>
						<td style="width: 80px; height: 20px;">中文名:</td>
						<td style="width: 180px; height: 20px;">${product.name }</td>
						<td style="width: 80px; height: 20px;">英文名：</td>
						<td>${product.ename }</td>
					</tr>
					<tr>
						<td style="width: 80px; height: 20px;">产品类型:</td>
						<td colspan="3">${product.productCatalog.name }</td>
					</tr>
					<tr>
						<td style="width: 80px; height: 20px;">标准价:</td>
						<td style="width: 180px; height: 20px;">${product.standardPrice }</td>
						<td style="width: 80px; height: 20px;">单位：</td>
						<td>${product.unit.name }</td>
					</tr>
					<tr>
						<td style="width: 80px; height: 20px;">创建者:</td>
						<td style="width: 180px; height: 20px;">${product.user.name }</td>
						<td style="width: 80px; height: 20px;">创建时间：</td>
						<td>${product.createTime }</td>
					</tr>
					<tr>
						<td style="width: 80px; height: 20px;">备注:</td>
						<td colspan="3">${product.remarks }</td>
					</tr>
			</table>
		</div>
	
		<!-- 产品属性 -->
		<div id="productProperty" style="width: 620px; padding-top: 10px;">
	
			<table id="productPropertyGrid" class="easyui-datagrid"
				data-options="
					url:'<%=basePath%>productproperty/list.do?productId = ${product.id }',
					title:'产品属性列表',
					loadMsg:'数据加载中请稍后……',  
					rownumbers:true,
					singleSelect:false,
					autoRowHeight:true,
					autoRowWidth:true,
					sortOrder:'desc',
					toolbar: '#pp_tb' ,
					onClickCell: onClickCell,
					onEndEdit: onEndEdit
					">
				<thead>
					<tr>
						<th data-options="field:'ck',checkbox:true"></th>
						<th data-options="field:'id',hidden:true"></th>
						<th data-options="field:'name',width:160">中文名</th>
						<th data-options="field:'value',width:80">中文值</th>
						<th data-options="field:'ename',width:160">英文名</th>
						<th data-options="field:'evalue',width:80">英文值</th>
						<th data-options="field:'remarks',width:40">备注</th>
					</tr>
				</thead>
			</table>
		
			<div id="pp_tb"
				style="border-bottom: 1px solid #ddd; height: 30px; padding: 3px 10px 0px 5px; background: #fafafa;">
				<div style="float: left;">
					<a id="pp_addGrid" href="#" class="easyui-linkbutton" plain="true"
						icon="icon-add">新增</a>
				</div>
		
				<div style="float: left;">
					<a id="pp_editGrid" href="#" class="easyui-linkbutton" plain="true"
						icon="icon-save">编辑</a>
				</div>
		
				<div style="float: left;">
					<a id="pp_removeGrid" href="#" class="easyui-linkbutton" plain="true"
						icon="icon-remove">删除</a>
				</div>
			</div>
		</div>
	
		<!-- 产品组合 -->
		<div id="productMix" style="width: 620px; padding-top: 10px;">
	
			<table id="productMixGrid" class="easyui-datagrid"
				data-options="
					url:'<%=basePath%>pcf/list.do?pcfd.fpid = ${product.id }',
					title:'产品组合列表',
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
						<th data-options="field:'productCatalog',width:200,
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
						<th data-options="field:'unit',width:40">单位</th>
						<th data-options="field:'quantity',width:40,
						editor:{
							type:'textbox',
							options:{
								required:true
							}
						}">数量</th>
					</tr>
				</thead>
			</table>
		
			<div id="pm_tb"
				style="border-bottom: 1px solid #ddd; height: 30px; padding: 3px 10px 0px 5px; background: #fafafa;">
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()">增加</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit()">删除</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="accept()">提交</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="reject()">撤销</a>
			</div>
		</div>
	</div>
	
	<!-- 添加产品属性窗口 -->
	<div id="addPropertyWin" class="easyui-window" title="添加产品属性"
		data-options="modal:true,closed:true,iconCls:'icon-save',minimizable:false,collapsible:false,maximizable:false"
		style="width: 480px; height: 160px; padding: 10px;">
		<div align="center">
		
			<form id="addPropertyWinForm" method="post">
				<table cellpadding="5">
					<tr>
						<td>中文名称:</td>
						<td><input id="pp_cname" class="easyui-textbox"
							style="width: 200px" type="text" name="cname"
							data-options="required:true"></input>
							</td>
					</tr>
					<tr>
						<td>中文数值:</td>
						<td><input id="pp_cvalue" class="easyui-textbox"
							style="width: 200px" type="text" name="cname"
							data-options="required:true"></input>
							</td>
					</tr>
					<tr>
						<td>英文名称:</td>
						<td><input id="pp_ename" class="easyui-textbox"
							style="width: 200px" type="text" name="cname"
							data-options="required:true"></input>
							</td>
					</tr>
					<tr>
						<td>英文数值:</td>
						<td><input id="pp_evalue" class="easyui-textbox"
							style="width: 200px" type="text" name="cname"
							data-options="required:true"></input>
							</td>
					</tr>
					<tr>
						<td>备注:</td>
						<td><input id="pp_remarks" class="easyui-textbox"
							style="width: 200px" type="text" name="cname"
							data-options="required:true"></input>
							</td>
					</tr>
				</table>
			</form>
			<div style="text-align: center; padding: 5px">
				<a id="pp_bsubmit" href="javascript:void(0)" style="width: 80px;"
					class="easyui-linkbutton">提交</a> 
				<a id="pp_bmodify" href="javascript:void(0)" style="width: 80px;"
					class="easyui-linkbutton">修改</a> 
				<a id="pp_bcancel"	href="javascript:void(0)" style="width: 80px;"
					class="easyui-linkbutton">取消</a>
			</div>
		</div>
	</div>
		
	<script type="text/javascript">
	
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