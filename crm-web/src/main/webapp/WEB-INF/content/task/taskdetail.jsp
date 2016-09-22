<%@page import="java.util.Iterator"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="java.util.*,com.chinahanjiang.crm.pojo.*" %>
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
		
		<%
			Task task = (Task)request.getAttribute("task");
			int status = task.getStatus();
			if(status==0){
				
		%>
			$('#itemgrid').datagrid({toolbar: '#tb'});
		<%
			}
		%>
		
		$('#i_addItem').click(function(){
			
			var id = $('#taskId').val();
			
			 var newObj = {
					 
					 id:'itemedit',
					 title:'任务编辑',
					 href:'<%=basePath%>win/additem.do?taskId='+id
			 };
			 
			 parent.openPanel(newObj);
		});
		
		
		$('#i_editItem').click(function(){
			
			var row = $('#itemgrid').datagrid("getSelected");
			if(row!=null){
				
				var i_id = row.id;
				
				var newObj = {
						 
						 id:'itemedit',
						 title:'任务编辑',
						 href:'<%=basePath%>win/modifyitem.do?itemId='+i_id
				 };
				 
				 parent.openPanel(newObj);
				 
			} else {
				
				$.messager.alert('注意', '请选择行数据!',
				'info');
			}
		});
		
		$('#i_deleteItem').click(function(){
			
			var row = $('#itemgrid').datagrid("getSelections");
			if(row!=null && row.length!=0){
				
				 $.messager.confirm('Confirm','您确定删除:"'+ row.length+'"行任务吗?',function(r){
					 
					    if (r){
					    	
							var id = new Array();
					    	
					    	$.each(row,function(i, o){
							   
					    		 var rowIndex = $('#itemgrid').datagrid('getRowIndex', o);
								 $('#itemgrid').datagrid('deleteRow',rowIndex);
								 
								 id[i] = o.id;
							});
							 
							 $.ajax({
								 
								type: "POST",
								url: "<%=basePath%>	item/del.do?id.ids=" + id,
								cache : false,
								dataType : "json",
								success : function(
										data) {

									var _data = data.md;
									if (_data.t) {
										$.messager
												.alert(
														'成功',
														_data.message,
														'info');
									} else {
										$.messager
												.alert(
														'失败',
														_data.message,
														'info');
									}
								}
							});
						}
					});
				} else {

					$.messager.alert('注意',
							'请选择行数据!', 'info');
				}
			});
		
			$('#i_finishItem').click(function(){
				
				var row = $('#itemgrid').datagrid("getSelections");
				if(row!=null && row.length!=0){
					
					 $.messager.confirm('Confirm','您确定关闭:"'+ row.length+'"行任务吗?',function(r){
						 
						    if (r){
						    	
						    	var id = new Array();
						    	
						    	$.each(row,function(i, o){
								   
									 id[i] = o.id;
								});
						    	
						    	$.ajax({
									 
									type: "POST",
									url: "<%=basePath%>	item/finish.do?id.ids=" + id,
									cache : false,
									dataType : "json",
									success : function(data){
										 
						    			 var _data = data.md;
						    			 if(_data.t){
						    				 
						    				 var num = _data.intF;
						    				 if(num!=0){
						    					 
						    					 $.messager.alert('注意',
						    								_data.message, 'info');
						    				 }
						    			 }
									}
						    	});
						    }
					 });
				} else {

					$.messager.alert('注意',
							'请选择行数据!', 'info');
				}
			});
			
			$('#i_quoteItem').click(function(){
				
				var row = $('#itemgrid').datagrid("getSelections");
				if(row!=null){
					if(row.length == 1){
						var itemtype = row[0].itemType;
						var itemId = row[0].id;
						
						var isR = itemtype.indexOf("报价");
						
						if(isR!=-1){
							
							var flag = row[0].flag;
							
							if(flag==0){
								
								//打开报价单
								//查找报价单，并打开报价单
								$.ajax({
										 
										type: "POST",
										url: "<%=basePath%>	quote/find.do?itemId=" + itemId,
										cache : false,
										dataType : "json",
										success : function(data){
											
											var _data = data.md;
											if(_data.t){
												
												var pqId = _data.intF;
												
												var newObj = {
							    						 
							    						 id:'quotewindow',
							    						 title:'产品报价',
							    						 href:'<%=basePath%>win/quote.do?itemId=' + itemId + '&quoteId=' + pqId
							    				 };
							    				 
							    				 parent.openPanel(newObj);
												
											} else {
												
												$.messager.alert('注意',
														_data.message , 'info');
											}
										}
								});
								
								
							} else if(flag==1) {
								
								$.messager.alert('注意',
										'该任务已经报价!', 'info');
							}
							
						} else {
							
							$.messager.alert('注意',
									'请选择产品报价的任务!', 'info');
						}
						
					} else {
						
						$.messager.alert('注意',
								'请选择单个任务!', 'info');
					}
					
				} else {
					
					//行为空，对项目进行整体报价，如果是一般任务，那么只能是新建一个任务
					//否则，直接建立任务与报价单
					
				}
			});
		});
</script>
<title>任务列表</title>
</head>
<body>
	<!-- 事件列表 -->
	<div style="float: left;">
		<div class="easyui-panel" data-options="border:true" title="项目详情"
			style="width: 720px; height:300px; padding: 10px 60px 20px 60px;">
			<input style="display: none;" type="text" name="taskId" id="taskId"
				value="${task.id }" />
			<table
				style="width: 650px; margin-right: auto; margin-left: auto; font-family: '宋体'; font-size: 13px;">
				<tr>
					<td style="width: 80px; height: 20px;">项目编号：</td>
					<td style="width: 180px; height: 20px;">${task.code }</td>
					<td style="width: 80px; height: 20px;">项目类型：</td>
					<td>${task.taskType.name }</td>
				</tr>
				<tr>
					<td style="width: 80px; height: 20px;">客户名称：</td>
					<td style="width: 180px; height: 20px;">${task.customer.name }</td>
					<td style="width: 80px; height: 20px;">创建时间：</td>
					<td>${task.createTime }</td>
				</tr>
				<tr>
					<td style="width: 110px; height: 25px;">内 容：</td>
					<td colspan="3">${task.name }</td>
				</tr>
				<%
					List<Product> products = (List<Product>)request.getAttribute("products");
					String str = "";
					if(products!=null){
						
						int i = 1;
						Iterator<Product> it = products.iterator();
						while(it.hasNext()){
							
							Product p = it.next();
							
							str +="<span style='color:#EE2C2C;'>" + p.getName() + "</span>&nbsp;   ";
							
							if(i%3==0){
								
								str += "</br>";
							}
							i++;
						}
					}
				
				%>
				<tr>
					<td style="width: 110px; height: 25px;">产 品：</td>
					<td colspan="3"><%=str %></td>
				</tr>
				<tr>
					<td style="width: 80px; height: 20px;">创建者：</td>
					<td style="width: 180px; height: 20px;">${task.createUser.name }</td>
					<td style="width: 80px; height: 20px;">跟进者：</td>
					<td>${task.updateUser.name }</td>
				</tr>
				<tr>
					<td style="width: 110px; height: 25px;">备 注：</td>
					<td colspan="3">${task.remarks }</td>
				</tr>
				<tr>
					<td style="width: 80px; height: 25px;">报价状态：</td>
					<td style="width: 180px; height: 20px;">
						<s:if test="task.taskType.name.indexOf('日常任务')==-1">
							<s:if test='task.flag == 1'>
								<span style="background-color:#D1D1D1;color:#2E8B57;font-weight:bold;">需求有改变，未<a href="#" onClick="newQuote(${task.id});">报价</a>
								</span>
							</s:if> 
							<s:else>
								<span style="background-color:#D1D1D1;color:#CD0000;">已报价</span>
							</s:else>
						</s:if>
					</td>
					<td style="width: 80px; height: 25px;">项目状态：</td>
					<td style="height: 20px;">
						<s:if test='task.status == 0'>
							<span style="background-color:#D1D1D1;color:#2E8B57;font-weight:bold;">进行中</span>
						</s:if>
						<s:elseif test='task.status == 1'>
							<span style="background-color:#D1D1D1;color:#CD0000;">完成</span>
						</s:elseif>
						<s:else>
							<span style="background-color:#D1D1D1;color:#CD0000;">已放弃</span>
						</s:else>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div style="padding-left:10px; float: left;">
		<div>
			<table id="contactgrid" class="easyui-datagrid"
				data-options="
					url:'<%=basePath%>contact/list.do?customerId = <s:if test="task.customer==null">0</s:if><s:else>${task.customer.id }</s:else>',
					loadMsg:'数据加载中请稍后……',  
					title:'相关联系人',
					width:520,
					height:120,
					collapsible:false,
					autoRowHeight:true,
					autoRowWidth:true
					">
				<thead>
					<tr>
						<th data-options="field:'id',hidden:true">序号</th>
						<th data-options="field:'customerId',hidden:true">公司ID</th>
						<th data-options="field:'name',width:120">姓名</th>
						<th data-options="field:'sexId',hidden:true">性别</th>
						<th data-options="field:'sex',width:40">性别</th>
						<th data-options="field:'phone',width:140">手机</th>
						<th data-options="field:'email',width:120">邮箱</th>
						<th data-options="field:'duty',width:50">职务</th>
						<th data-options="field:'remarks',width:40">备注</th>
					</tr>
				</thead>
			</table>
		</div>
		<div style="padding-top:10px;">
			<table id="quotegrid" class="easyui-datagrid"
				data-options="
					url:'<%=basePath%>quote/list.do?taskId='+${task.id },
					loadMsg:'数据加载中请稍后……',  
					title:'相关报价单',
					width:520,
					height:170,
					collapsible:false,
					autoRowHeight:true,
					autoRowWidth:true,
					pagination:true,
					onDblClickRow:function openItemDetail(index,row){
	    	
					    	var _id = row.itemId;
					    	parent.addPanel('itemdetail','任务详情','<%=basePath%>win/itemdetail.do?itemId='+_id);
					   }
					">
				<thead>
					<tr>
						<th data-options="field:'id',hidden:true"></th>
						<th data-options="field:'itemId',hidden:true"></th>
						<th data-options="field:'itemCode',width:180">任务编号</th>
						<th data-options="field:'pqId',hidden:true"></th>
						<th data-options="field:'pqCode',width:180">报价单号</th>
						<th data-options="field:'price',width:60">总价</th>
						<th data-options="field:'remarks',width:40">备注</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>

	<div style="padding-top: 10px; clear: both;">
		<table id="itemgrid" cellspacing="0" cellpadding="0"
			class="easyui-datagrid" style="height: 320px;"
			data-options="
						url:'<%=basePath%>item/list.do?td.id=${task.id }',
						loadMsg:'数据加载中请稍后……',  
						title:'任务列表',
						checkOnSelect:true,
						singleSelect:false,
						collapsible:false,
						autoRowHeight:true,
						autoRowWidth:true,
						pagination:true,
						pageNumber:1,
						pageSize:10,
						sortOrder:'desc',
						rowStyler: function(index,row){
							if (row.statusStr == 'N'){
							//#6293BB
							return 'background-color:#B2DFEE;color:#000;';
							}
						},
						onDblClickRow:function openItemDetail(index,row){
	    	
					    	var _id = row.id;
					    	parent.addPanel('itemdetail','任务详情','<%=basePath%>win/itemdetail.do?itemId='+_id);
					    }
					">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th data-options="field:'id',width:30,hidden:true">序号</th>
					<th data-options="field:'code',width:150,align:'center'">编码</th>
					<th data-options="field:'itemType',width:80,align:'center'">任务类型</th>
					<th data-options="field:'customerId',hidden:true"></th>
					<th data-options="field:'customer',width:200,align:'center'">客户</th>
					<th data-options="field:'name',width:250,align:'center'">内容</th>
					<th data-options="field:'contactId',hidden:true"></th>
					<th data-options="field:'contact',width:100,align:'center'">联系人</th>
					<th data-options="field:'userId',hidden:true"></th>
					<th data-options="field:'user',width:80,align:'center'">跟进者</th>
					<th data-options="field:'createTime',width:100,align:'center'">创建时间</th>
					<th data-options="field:'updateTime',width:100,align:'center'">修改时间</th>
					<th data-options="field:'statusStr',width:50,align:'center'">状态</th>
					<th data-options="field:'flag',hidden:true"></th>
					<th data-options="field:'flagStr',width:50,align:'center',styler:cellStyler">报价</th>
					<th data-options="field:'remark',width:60,align:'center'">备注</th>
				</tr>
			</thead>
		</table>
		<div id="tb"
			style="border-bottom: 1px solid #ddd; height: 30px; padding: 3px 10px 0px 5px; background: #fafafa;">
			<div style="float: left;">
				<a id="i_addItem" href="#" class="easyui-linkbutton" plain="true"
					icon="icon-add">新增</a>
			</div>

			<div style="float: left;">
				<a id="i_editItem" href="#" class="easyui-linkbutton" plain="true"
					icon="icon-save">编辑</a>
			</div>

			<div style="float: left;">
				<a id="i_deleteItem" href="#" class="easyui-linkbutton" plain="true"
					icon="icon-remove">删除</a>
			</div>
			
			<div style="float: left;">
				<a id="i_finishItem" href="#" class="easyui-linkbutton" plain="true"
					icon="icon-remove">关闭任务</a>
			</div>
			
			<div style="float: left;">
				<a id="i_quoteItem" href="#" class="easyui-linkbutton" plain="true"
					icon="icon-remove">报价</a>
			</div>
			
			<div style="float: left;">
				<a id="i_upload" href="#" class="easyui-linkbutton" plain="true"
					icon="icon-remove">上传</a>
			</div>
			
			<div style="float: left;">
				<a id="i_download" href="#" class="easyui-linkbutton" plain="true"
					icon="icon-remove">下载</a>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		function cellStyler(value,row,index){
			var flag = row.flag;
			if (flag==0){
				return 'background-color:#ffee00;color:red;';
			}
		}
		
		function newQuote(taskId){
			
			$.messager.confirm('Confirm','你是否要为这个项目报价呢？',function(r){
				
				if(r){
					
					//新建Item
					var itemId = 0;
					
					$.ajax({
						
						type:"POST",
			    		 url:"<%=basePath%>item/addquote.do?td.id=" + taskId,
			    		 cache:false,
			    		 dataType:'json',
			    		 success:function(data){
			    			 
			    			 var _data = data.md;
			    			 if(_data.t){
			    				 
			    				 itemId = _data.intF;
			    				 
			    				 
			    				 if(itemId!=0){
			 						
			 						$.ajax({
			 							
			 							type:"POST",
			 				    		 url:"<%=basePath%>quote/add.do?itemId=" + itemId,
			 				    		 cache:false,
			 				    		 dataType:'json',
			 				    		 success:function(data){
			 				    			 
			 				    			 var _data = data.md;
			 				    			 if(_data.t){
			 				    			 	
			 				    				 var pqId = _data.intF;
			 				    				 var newObj = {
			 				    						 
			 				    						 id:'quotewindow',
			 				    						 title:'产品报价',
			 				    						 href:'<%=basePath%>win/quote.do?itemId=' + itemId + "&quoteId=" + pqId
			 				    				 };
			 				    				 
			 				    				 parent.openAndClose(newObj,'任务编辑');
			 				    				 
			 				    			 } else {
			 				    				 
			 				    				 $.messager.alert('失败', _data.message,
			 										'info');
			 				    			 }
			 				    		 }
			 						});
			 					} else {
			 						
			 						$.messager.alert('失败', "要报的任务Id不能为0",
			 						'info');
			 					}
			    				 
			    			 } else {
			    				 
			    				 $.messager.alert('失败', _data.message,
									'info');
			    			 }
			    		 }
					});
				}
			});
		}
	</script>
</body>

</html>