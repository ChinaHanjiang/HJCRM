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
<style type="text/css">
html  {
	 overflow-x: hidden;
	 
}
</style>
<script type="text/javascript">
	var gindex = new Array();
	var _pm_catalogName = new Array();
	var _pm_productName = new Array();
	var _pm_productCode = new Array();
	var _pm_productId = new Array();
	var _pm_productStandardPrice = new Array();
	var _pm_productDefindPrice = new Array();
</script>
<title>报价列表</title>
</head>
<body>
	<input id="itemId" value="${item.id }" style="display:none;" />
	<div id="quotewindow" class="easyui-panel" data-options="border:false"
		style="padding: 10px;">
		<div class="easyui-panel" data-options="border:true"
			style="width: 720px; padding: 10px 60px 20px 60px">
			<table
				style="width: 650px; margin-right: auto; margin-left: auto; font-family: '宋体'; font-size: 13px;">
				<tr>
					<td style="width: 80px; height: 20px;">项目编号：</td>
					<td style="width: 180px; height: 20px;">${task.code }</td>
					<td style="width: 80px; height: 20px;">客 户：</td>
					<td>${task.customer.name }</td>
				</tr>
				<tr>
					<td style="width: 110px; height: 25px;">内 容：</td>
					<td colspan="3">${task.name }</td>
				</tr>
				<tr>
					<td style="width: 80px; height: 20px;">联系人：</td>
					<td style="width: 180px; height: 20px;">${item.contact.name}</td>
					<td style="width: 80px; height: 20px;">电 话：</td>
					<td>${item.contact.mobilePhone }</td>
				</tr>
			</table>
		</div>

		<s:if test="pqds!=null">
			<%int i=0; %>
			<s:iterator value="pqds" var="p">
				<script type="text/javascript">
					gindex[<%=i %>] = undefined;
					_pm_catalogName[<%=i %>] = undefined;
					_pm_productName[<%=i %>] = undefined;
					_pm_productCode[<%=i %>] = undefined;
					_pm_productStandardPrice[<%=i %>] = undefined;
					_pm_productDefindPrice[<%=i%>] = undefined;
					_pm_productId[<%=i %>] = ${p.product.id};
				</script>
				<div class="easyui-panel" data-options="border:false"
					style="width: 720px; padding-top: 10px;">
					<table id="quotelist_<%=i %>" cellspacing="0" cellpadding="0"
						class="easyui-datagrid"
						data-options="
							url:'<%=basePath%>quotedetail/list.do?productQuoteDetailId= ' + ${p.id }, 
							title:'${p.product.name }的报价单', 
			       			loadMsg:'正在加载数据，请稍后...',
			       			height: 250, 
			       			border: true, 
			       			rownumbers:true,
							singleSelect:true,
							autoRowHeight:true,
							autoRowWidth:true,
			       			collapsible:true,//是否可折叠的 
				   			pageSize:10,
				    		toolbar: '#tb_${p.id }' ,
							onClickCell: onClickCell,
							onEndEdit: onEndEdit
						">
						<thead>
							<tr>
								<th data-options="field:'id',width:30,hidden:true">序号</th>
								<th data-options="field:'productCatalogId',hidden:true"></th>
								<th
									data-options="field:'productCatalog',width:150,
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
													_pm_catalogName[<%=i %>] = node.text;
													
													var row = $('#quotelist_<%=i %>').datagrid('getSelected');
													var index = $('#quotelist_<%=i %>').datagrid('getRowIndex', row);
													$('#quotelist_<%=i %>').datagrid('getRows')[index]['productCatalogId'] = id;
													
													var ed = $('#quotelist_<%=i %>').datagrid('getEditor', {index:index,field:'sproduct'});
													$(ed.target).combobox({
														url:'<%=basePath%>product/find.do?catalogId='+ id
													});
												}
											}
										}
												
									">产品类型</th>
								<th data-options="field:'spid',hidden:true"></th>
								<th
									data-options="field:'sproduct',width:150,
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
													_pm_productName[<%=i %>] = record.text;
													
													var row = $('#quotelist_<%=i %>').datagrid('getSelected');
													var index = $('#quotelist_<%=i %>').datagrid('getRowIndex', row);
													$('#quotelist_<%=i %>').datagrid('getRows')[index]['spid'] = id;
													
													$.ajax({
														type:'POST',
					    		 						url:'<%=basePath%>product/load.do?pd.id=' + id,
					    		 						cache:false,
					    		 						dataType:'json',
					    		 						success:function(data){
					    		 							
					    		 							_pm_productCode[<%=i %>] = data.pd.code;
					    		 							_pm_productStandardPrice[<%=i %>] = data.pd.standardPrice;
					    		 							$('#quotelist_<%=i %>').datagrid('getRows')[index]['code'] = _pm_productCode[<%=i %>];
					    		 							$('#quotelist_<%=i %>').datagrid('getRows')[index]['standardPrice'] = _pm_productStandardPrice[<%=i %>];
					    		 							if(_pm_productDefindPrice[<%=i%>] == undefined){
					    		 								_pm_productDefindPrice[<%=i%>] = data.pd.standardPrice;
					    		 								$('#quotelist_<%=i %>').datagrid('getRows')[index]['definedPrice'] = _pm_productDefindPrice[<%=i%>];
					    		 							}
					    		 						}
													});
												}
											}
										}
									">名称</th>
								<th data-options="field:'code',width:150,align:'center'">产品编码</th>
								<th
									data-options="field:'standardPrice',width:90,align:'center'">标准价</th>
								<th data-options="field:'definedPrice',width:90,align:'center',editor:{type:'textbox',options:{
									onChange:function(newValue,oldValue){
										_pm_productDefindPrice[<%=i%>] = newValue;
									}
								}}">自定义价</th>
								<th data-options="field:'quantity',width:50,align:'center',	editor:{type:'textbox',	options:{required:true}}">数量</th>
							</tr>
						</thead>
					</table>
					<div id="tb_${p.id }"
						style="border-bottom: 1px solid #ddd; height: 30px; padding: 3px 10px 0px 5px; background: #fafafa;" >
						<a href="javascript:void(0)" class="easyui-linkbutton"
							data-options="iconCls:'icon-add',plain:true"
							onclick="append(<%=i %>)">增加</a> <a href="javascript:void(0)"
							class="easyui-linkbutton"
							data-options="iconCls:'icon-remove',plain:true"
							onclick="removeit(<%=i %>)">删除</a> <a href="javascript:void(0)"
							class="easyui-linkbutton"
							data-options="iconCls:'icon-save',plain:true"
							onclick="accept(<%=i %>)">提交</a> <a href="javascript:void(0)"
							class="easyui-linkbutton"
							data-options="iconCls:'icon-save',plain:true"
							onclick="reject(<%=i %>)">撤销</a>
					</div>
				</div>
			<% i++; %>
			</s:iterator>
		</s:if>
	</div>


	<script type="text/javascript">
		
		function endEditing(id){
			
			var editIndex = gindex[id];
			if (editIndex == undefined){return true}
			if ($('#quotelist_'+id).datagrid('validateRow', editIndex)){
				$('#quotelist_'+id).datagrid('endEdit', editIndex);
				gindex[id] = undefined;
				return true;
			} else {
				return false;
			} 
		}
		
		function onClickCell(index, field){
			
			var o = $(this).datagrid('options')
			var gid = o.id;
			var i = gid.split('_');
			var j = i[1];
			var editIndex = gindex[j];
			
			if (editIndex != index){
				if (endEditing(i[1])){
					var row = $('#'+gid).datagrid('selectRow', index);
						row.datagrid('beginEdit', index);
						
					var pcId = $('#'+gid).datagrid("getSelected").productCatalogId;
					var ned = $('#'+gid).datagrid('getEditor', {index:index,field:'sproduct'});
					$(ned.target).combobox({
						url:"<%=basePath%>product/find.do?catalogId="+ pcId
					});		 			
					
					var ed = $('#'+gid).datagrid('getEditor', {index:index,field:field});
					
					if (ed){
						($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
					}
					
					gindex[j] = index;
					
				} else {
					
					setTimeout(function(){
						$('#'+gid).datagrid('selectRow', editIndex);
					},0);
				}
			} 
		}
		
		function onEndEdit(index, row){
			
			var o = $(this).datagrid('options')
			var gid = o.id;
			var i = gid.split('_');
			var j = i[1];
			
			if(_pm_catalogName[j] != undefined){
				
				$('#'+gid).datagrid('getRows')[index]['productCatalog'] = _pm_catalogName[j];
				_pm_catalogName[j] = undefined;
			}
			if(_pm_productName[j] != undefined){
				
				$('#'+gid).datagrid('getRows')[index]['sproduct'] = _pm_productName[j];
				_pm_productName[j] = undefined;
			}
			
			 if(_pm_productStandardPrice[j] != undefined){
				
				$('#'+gid).datagrid('getRows')[index]['definedPrice'] = _pm_productDefindPrice[j];
				_pm_productDefindPrice[j] = undefined;
			} 
		}
		
		function append(id){
			if (endEditing(id)){
				$('#quotelist_'+id).datagrid('appendRow',{});
				gindex[id] = $('#quotelist_'+id).datagrid('getRows').length-1;
				$('#quotelist_'+id).datagrid('selectRow', gindex[id])
						.datagrid('beginEdit', gindex[id]);
			}
		}
		
		function removeit(id){
			
			var editIndex = gindex[id];
			if (editIndex == undefined){return}
			$('#quotelist_'+id).datagrid('cancelEdit', editIndex)
					.datagrid('deleteRow', editIndex);
			gindex[id] = undefined;
		}
		
		function accept(id){
			if (endEditing(id)){
				
				var $pmg = $('#quotelist_'+id);
	            var rows = $pmg.datagrid('getChanges');
				 if (rows.length) {
	                var inserted = $pmg.datagrid('getChanges', "inserted");
	                var deleted = $pmg.datagrid('getChanges', "deleted");
	                var updated = $pmg.datagrid('getChanges', "updated");
	             	var itemId = $('#itemId').val();
	                var effectRow = new Object();
	                effectRow['itemId'] = itemId;
	                effectRow['productId'] = _pm_productId[id];
	                if (inserted.length) {
	                    effectRow["inserted"] = JSON.stringify(inserted);
	                }
	                if (deleted.length) {
	                    effectRow["deleted"] = JSON.stringify(deleted);
	                }
	                if (updated.length) {
	                    effectRow["updated"] = JSON.stringify(updated);
	                }
	                
	                $.post("<%=basePath%>quotedetail/savemix.do", effectRow, function (rsp) {
	                    if (rsp) {
	                    	 $.messager.alert("提示", "修改成功！");
	                        $pmg.datagrid('acceptChanges');
	                    }
	                }, "JSON").error(function () {
	                    $.messager.alert("提示", "提交错误了！");
	                });
	            }
				
			}
		}
		
		function reject(id){
			$('#quotelist_'+id).datagrid('rejectChanges');
			gindex[id] = undefined;
		}
	</script>
</body>

</html>