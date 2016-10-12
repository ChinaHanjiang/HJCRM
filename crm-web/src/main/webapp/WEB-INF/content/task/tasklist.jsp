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
	src="<%=basePath%>js/date.js"></script>
<script type="text/javascript"
	src="<%=basePath%>locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		
		$('#t_addTask').click(function(){
			
			parent.addPanel('taskedit','项目编辑','<%=basePath%>win/addtask.do');
		});
		
		
		$('#t_editTask').click(function(){
			
			var row = $('#taskgrid').datagrid("getSelections");
			
			if(row!=null && row.length == 1){
				var taskId = row[0].id;
				parent.addPanel('taskedit','项目编辑','<%=basePath%>win/modifytask.do?taskId='+taskId);
				
			} else {
				
				$.messager.alert('注意', '请选择1行数据!',
				'info');
			}
		});
		
		$('#t_deleteTask').click(function(){
			
			var row = $('#taskgrid').datagrid("getSelections");
			if(row!=null && row.length!=0){
				
				 $.messager.confirm('Confirm','您确定删除:"'+ row.length+'"个任务吗?',function(r){
					 
					    if (r){
					    	
					    	var id = new Array();
					    	
					    	$.each(row,function(i, o){
							   
					    		 var rowIndex = $('#taskgrid').datagrid('getRowIndex', o);
								 $('#taskgrid').datagrid('deleteRow',rowIndex);
								 
								 id[i] = o.id;
							});
							 
							 $.ajax({
								 
								type: "POST",
								url: "<%=basePath%>task/del.do?td.ids=" + id,
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
		
		/**
		关闭
		*/
		$('#t_closeTask').click(function(){
			
			var row = $('#taskgrid').datagrid("getSelections");
			if(row !=null && row.length==1){
				var tId = row[0].id;
				var status = row[0].statusStr;
				
				if(status=="N"){
					
					$('#t_closeWin').window('open');
					$('#taskId').val(tId);
					
				} else {
					
					$.messager.alert('注意', '项目已关闭，请重新选择!',
					'info');
				}
				
			} else {
				
				$.messager.alert('注意', '选择1个项目进行操作!',
				'info');
			}
			
		});
	
		/**/
		$(".close").change(function() {
			
			var $selectedvalue = $("input[name='t_status']:checked").val();
			if ($selectedvalue == 1) {
				
				$('#t_remarks').textbox('disable');
			} else {
				
				$('#t_remarks').textbox('enable');
			}
		});
		
		$('#t_bsubmit').click(function(){
			
			var t_id = $('#taskId').val();
			var t_status = $("input[name='t_status']:checked").val();
			var t_remarks = $('#t_remarks').textbox('getText');
			var t = $('#t_closeWinForm').form('enableValidation').form('validate');
			if(t){
				
				var str = "";
				str += "td.id = " + t_id;
				str += "&td.status=" + t_status;
				str += "&td.remarks=" + t_remarks;
				
				if(t_status==1){
					
					//需不需要判断子任务中是否还有没有关闭的任务
					$.ajax({
						 type:"POST",
			    		 url:"<%=basePath%>item/checkstatus.do?td.id=" + t_id,
			    		 cache:false,
			    		 dataType:'json',
			    		 success:function(data){
			    			 
			    			 var _data = data.md;
			    			 if(_data.t){
			    				 
			    				 var num = _data.intF;
			    				 if(num!=0){
			    					 $.messager.confirm('Confirm',_data.message + ",你确定执行操作吗?点击'确定'将项目设置为完成状态，"
			    							 +"点击'取消'将进入项目详细页面",function(r){
			    						 
			    						    if (r){
			    						    	//确定将执行
			    						    	
			    						    	$.ajax({
													 type:"POST",
										    		 url:"<%=basePath%>task/finish.do?" + str,
										    		 cache:false,
										    		 dataType:'json',
										    		 success:function(data){
										    			 
										    			 $('#t_closeWin').window('close');
										    			 $('#taskgrid').datagrid({
											 					queryParams:{
											 						tid:t_id
											 					}
											 				});
										    		 }
			    						    	});
			    						    	
			    						    } else {
			    						    	//否将退到项目详情页面
			    						    	$('#t_closeWin').window('close');
			    						    	parent.addPanel('taskdetail','项目详情','<%=basePath%>win/taskdetail.do?taskId=' + t_id);
			    						    }
			    					 });
			    					 
			    				 } else {
			    					 
			    					 $.ajax({
										 type:"POST",
							    		 url:"<%=basePath%>task/finish.do?" + str,
							    		 cache:false,
							    		 dataType:'json',
							    		 success:function(data){
							    			 
							    			 $('#t_closeWin').window('close');
							    			 $('#taskgrid').datagrid({
							 					queryParams:{
							 						tid:t_id
							 					}
							 				});
							    		 }
    						    	});
			    				 }
			    			 } else {
			    				 
			    				 $.messager.alert('注意', _data.message,
			    					'info');
			    			 }
			    		 }
					});
				} else {
					
					$.ajax({
						 type:"POST",
			    		 url:"<%=basePath%>task/giveup.do?" + str,
			    		 cache:false,
			    		 dataType:'json',
			    		 success:function(data){
			    			 
			    			 $('#t_closeWin').window('close');
			    			 $('#taskgrid').datagrid('reload');
			    		 }
			    	});
				}
			} else {
				
				$.messager.alert('注意', '请填完整的表单！',
				'info');
			}
		});
		
		
		$('#t_bcancel').click(function(){
			
			$('#t_closeWin').window('close');
		});
		
		
		$('#t_closeWin').window({
			onClose:function(){
				
				$('input:radio[name=t_status]')[0].checked = true;
				$('#t_remarks').textbox('disable');
			}
		});
		
		$('#beginTime').datebox({
			onSelect: function(date){
				
				var b = date.Format("yyyy-MM-dd");
				var e = $('#endTime').datebox('getValue');
				var now = new Date().Format("yyyy-MM-dd");
				
				if(e==""){
					if(b<=now){
						
						$('#endTime').datebox('setValue',now);
						e = now;
						
					} else {
						$.messager.alert('注意',"开始日期不能大于当前日期",
						'info');
						$('#beginTime').datebox('setValue',now);
						b = now;
						$('#endTime').datebox('setValue',now);
						e = now;
					}
					
				} else {
					
					if(b>e){
						
						$.messager.alert('注意',"开始日期不能大于结束日期",
						'info');
						$('#endTime').datebox('setValue',now);
						e = now;
					} 
				}
				
				var tasktypeId = $('#t_tasktype').combobox('getValue');
				var status = $('#t_status').combobox('getValue');
				
				$('#taskgrid').datagrid({
					queryParams:{
						type:1,
						beginTime:b,
						endTime:e,
						tasktypeId:tasktypeId,
						status:status
					}
				});
			}
		});
		
		$('#endTime').datebox({
			onSelect: function(date){
				
				var e = date.Format("yyyy-MM-dd");
				var b = $('#beginTime').datebox('getValue');
				var now = new Date().Format("yyyy-MM-dd");
				
				if(b==""){
					if(e<=now){
						
						$('#beginTime').datebox('setValue',"");
						b = "";
						
					} else {
						$.messager.alert('注意'," 结束日期不能大于当前日期",
						'info');
						$('#beginTime').datebox('setValue',"");
						b = "";
						$('#endTime').datebox('setValue',now);
						e = now;
					}
					
				} else {
					
					if(b>e){
						
						$.messager.alert('注意',"开始日期不能大于结束日期",
						'info');
						$('#beginTime').datebox('setValue',"");
						b = "";
					} 
				}
				
				var tasktypeId = $('#t_tasktype').combobox('getValue');
				var status = $('#t_status').combobox('getValue');
				
				$('#taskgrid').datagrid({
					queryParams:{
						type:1,
						beginTime:b,
						endTime:e,
						tasktypeId:tasktypeId,
						status:status
					}
				});
			}
		});
		
		$('#t_tasktype').combobox({
			editable:false,
			onChange:function(newValue,oldValue){
				
				var b = $('#beginTime').datebox('getValue');
				var e = $('#endTime').datebox('getValue');
				var status = $('#t_status').combobox('getValue');
				
				$('#taskgrid').datagrid({
					queryParams:{
						type:1,
						beginTime:b,
						endTime:e,
						tasktypeId:newValue,
						status:status
					}
				});
			 }
		});
		
		$('#t_status').combobox({
			
			editable:false,
			onChange:function(newValue,oldValue){
				
				var b = $('#beginTime').datebox('getValue');
				var e = $('#endTime').datebox('getValue');
				var tasktypeId = $('#t_tasktype').combobox('getValue');
				
				$('#taskgrid').datagrid({
					queryParams:{
						type:1,
						beginTime:b,
						endTime:e,
						tasktypeId:tasktypeId,
						status:newValue
					}
				});
			 }
		});
		
		$('#search').searchbox({
			searcher:function(value,name){
				
				if(name=="name"){
					
					$('#taskgrid').datagrid({
						queryParams:{
							type:2,
							name:value
						}
					});
					
				} else {
					
					$('#taskgrid').datagrid({
						queryParams:{
							type:3,
							customerName:value
						}
					});
				}
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
						title:'项目列表',
						singleSelect:false,
						collapsible:false,
						autoRowHeight:true,
						autoRowWidth:true,
						pagination:true,
						pageNumber:1,
						pageSize:20,
						sortOrder:'desc',
			    		toolbar: '#tb',
			    		onDblClickRow:function(index,row){
						
							var taskId = row.id;
							parent.addPanel('taskdetail','项目详情','<%=basePath%>win/taskdetail.do?taskId=' + taskId);
						},
						rowStyler: function(index,row){
							if (row.statusStr == 'N'){
							//#6293BB
								return 'background-color:#B2DFEE;color:#000000;';
								}
							if (row.statusStr == 'R'){
						//#6293BB
							return 'background-color:#FFB90F;color:#000000;';
							}
							if (row.statusStr == 'O'){
						//#6293BB
							return 'background-color:#CAFF70;color:#000000;';
							}
						}
					">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th data-options="field:'id',width:30,hidden:true">序号</th>
					<th data-options="field:'code',width:150,align:'center'">项目编码</th>
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
					<th data-options="field:'itemNum',width:50,align:'center'">任务数</th>
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
			
			<div style="float: left;">
				<a id="t_closeTask" href="#" class="easyui-linkbutton" plain="true"
					icon="icon-complete">关闭项目</a>
			</div>
			
			
			<div style="float: right; padding-right: 10px;">
				<input id="search" class="easyui-searchbox"
					data-options="prompt:'请输入查询内容',menu:'#mm'" style="width: 180px;"></input>
				<div id="mm">
				    <div data-options="name:'customer'">客户</div>
				    <div data-options="name:'name'">内容</div>
				</div>
			</div>
			
			<div style="float: right; padding-right:10px;">
				状态：<select id="t_status" class="easyui-combobox" style="width: 60px"
						data-options="panelHeight: 'auto'" name="tb_status">
						<option value=-1 selected="selected">所有 </option>
						<option value=0>未完成 </option>
						<option value=1>完成 </option>
						<option value=2>放弃 </option>
					</select>
			</div>
	
			<div style="float: right; padding-right: 5px;">
				<select id="t_tasktype" class="easyui-combobox" style="width: 100px"
					data-options="panelHeight: 'auto'" name="tb_tasktype">
					<option value=0 selected="selected">请选择类型 </option>
					<s:if test="taskTypes!=null">
						<s:iterator value="taskTypes" var="tt">
							<option value=${tt.id }>${tt.name }</option>
						</s:iterator>
					</s:if>
				</select>
			</div>
			
			<div style="float: right; padding-right: 10px;">
				到:  <input id="endTime" class="easyui-datebox" style="width: 100px"
					style="width:200px">
			</div>
			<div style="float: right; padding-right: 5px;">
				开始:  <input id="beginTime" class="easyui-datebox" style="width: 100px"
					style="width:200px">
			</div>
		</div>
	</div>
	
	<!-- 关闭窗口 -->
	<div id="t_closeWin" class="easyui-window" title="项目关闭"
			data-options="modal:true,closed:true,minimizable:false,collapsible:false,maximizable:false"
			style="width: 450px; padding: 10px;">
			<div style="padding: 10px 60px 20px 60px">
				<form id="t_closeWinForm" method="post">
					<input id="taskId" style="display:none;"/>
					<table cellpadding="5">
						<tr id="tcw_cause">
							<td align="center">项目状态:</td>
							<td>
								<input type="radio" class="close" name="t_status" value="1" checked>完成</input>
								<input type="radio" class="close" name="t_status" value="2">放弃</input>
							</td>
						</tr>
						<tr id="tcw_remarks">
							<td align="center">原&nbsp;&nbsp;&nbsp;&nbsp;因:</td>
							<td><input id="t_remarks" class="easyui-textbox"
								style="width: 200px; height: 40px;" type="text" name="f_t_remarks" required
								data-options="multiline:true,disabled:true"></td>
						</tr>
					</table>
				</form>
				<div style="text-align: center; padding: 5px">
					<a id="t_bsubmit" href="javascript:void(0)" style="width: 80px;"
						class="easyui-linkbutton">提交</a> <a id="t_bcancel"
						href="javascript:void(0)" style="width: 80px;"
						class="easyui-linkbutton">取消</a>
				</div>
			</div>
		</div>
	
	<script type="text/javascript">
	
		function closeDiv(id){
			
			$('#pdiv' + id).remove();
		}
		
		function addCustomer(){
			
			parent.addPanel('customerlist','客户管理','<%=basePath%>win/customerlist.do');
		}
		
		function addProduct(){
			
			parent.addPanel('productsManager','产品管理','<%=basePath%>win/productlist.do');
		}
	</script>
</body>

</html>