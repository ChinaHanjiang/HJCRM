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
		
		/*定义全局变量*/
		var _t_id;
		
		<%
			int type = (Integer)request.getAttribute("type");
			if(type==1){
				
		%>
		
			$('#t_bsubmit').show();
			$('#t_bmodify').hide();
		
		<%
			} else {
		%>
			$('#t_submit').hide();
			$('#t_bmodify').show();
		<%
			}
		%>
		
		$('#t_bsubmit').click(function(){
			
			var t_name;
			var t_code;
			var t_customerId;
			var t_tasktypeId;
			var t_remarks;
			var t_product = [];
			
			t_name = $('#t_name').textbox('getText');
			t_code = $('#t_code').textbox('getText');
			t_customerId = $('#t_customerId').val();
			t_remarks = $('#t_remarks').textbox('getText');
			t_tasktypeId = $('#t_tasktype').combobox('getValue');
			$("input[name^='product']").each(function(i, o){
			    t_product[i] = $(o).val();
			});
			
			var t = $('#addTaskForm').form('enableValidation').form('validate');
			if(t){
				
				var str = '';
				str += "td.name=" + t_name;
				str += "&td.code=" + t_code;
				str += "&td.customerId=" + t_customerId;
				str += "&td.taskTypeId=" + t_tasktypeId;
				str += "&td.addProducts=" + t_product;
				str += "&td.remarks=" + t_remarks; 
				
				$.ajax({
					 type:"POST",
		    		 url:"<%=basePath%>task/add.do?" + str,
		    		 cache:false,
		    		 dataType:'json',
		    		 success:function(data){
		    			 
		    			 var _data = data.md;
		    			 if(_data.t){
		    				 
		    				 var id = _data.intF;
		    				 
		    				 $.messager.confirm('Confirm','你是否要为这个项目添加第一个任务呢？',function(r){
		    					 
		    					 if(r){
		    						 
		    						 var newObj = {
		    								 
		    								 id:'additem',
		    								 title:'添加任务',
		    								 href:'<%=basePath%>win/additem.do?taskId='+id
		    						 };
		    						 
		    						 parent.openAndClose(newObj,'添加项目');
		    						 
		    					 } else {
		    						 
		    						 var newObj = {
		    								 id:'tasklist',
		    								 title:'任务列表',
		    								 href:'<%=basePath%>win/tasklist.do'
		    						 };
		    						 
		    						 parent.openAndClose(newObj,'添加项目');
		    					 }
		    				 });
		    				 
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
		
		$('#t_bmodify').click(function(){
			
			
		});
		
		$('#t_bcancel').click(function(){
			
			 parent.closePanel('添加项目');
			
		});
		
		$('#t_tasktype').combobox({ 
		      editable:false, //不可编辑状态
		      required:true, 
		      cache: false,
		      panelHeight: 'auto',//自动高度适合
		      onChange:function(){
		    	  
		    	  var _tasktypeId = $(this).combobox('getValue');
		    	  var str = "ttd.id=" + _tasktypeId;
		    	  $.ajax({
		    		  type:"POST",
			    		 url:"<%=basePath%>task/generatecode.do?" + str,
			    		 cache:false,
			    		 dataType:'json',
			    		 success:function(data){
			    			 
			    			 var _result = data.code;
			    			 $('#t_code').textbox('setText',_result);
			    			 $('#t_code').textbox('setValue',_result);
			    		 }
		    	  });
		      }
		});
		
		$('#t_product').combobox({
			onSelect:function(record){
				
				var text = record.q;
				var id = record.id;
				
				if(id==-1){
					
					addProduct();
					return;
				}
				
				var str = "<div id='pdiv" + id + "' style='vertical-align:middle; line-height:25px; height:25px; margin-bottom:2px; background:#EDEDED; color:#000'>"
				+ "<input name='product' id='product " + id + "' type='text'  value=" + id + " style='display:none;' />"
				+ text 
				+ "<img style='float:right;padding-top:5px;padding-right:8px;' src='<%=basePath%>icon/close.png' onClick='closeDiv(" + id + ")';></div>";
				
				$('#choseProducts').append(str);
			}
		});
		
	
	});
	</script>
<title>添加项目</title>
</head>
<body>
	<!-- 添加任务窗口 -->
	<div id="taskedit" title="项目" class="easyui-panel" data-options="border:true"
		style="width: 450px; height: auto;">
		<div id="f_task"
			 style=" padding: 10px">
			<div style="padding: 0px 0px 0px 50px">
				<form id="addTaskForm" method="post">
					<table cellpadding="5">
						<tr>
							<td>内容:</td>
							<td><input id="t_name" class="easyui-textbox"
								style="width: 200px; height: 40px;" type="text" name="f_t_name"
								required data-options="multiline:true"></input></td>
						</tr>
						<tr>
							<td>任务类别:</td>
							<td><select id="t_tasktype" class="easyui-combobox" required
								data-options="panelHeight: 'auto',editable:false"
								style="width: 200px" name="f_t_tasktype">
									<option value=0 selected="selected">请选择</option>
									<s:if test="taskTypes!=null">
										<s:iterator value="taskTypes" var="tt">
											<option value=${tt.id }>${tt.name }</option>
										</s:iterator>
									</s:if>
							</select></td>
						</tr>
						<tr>
							<td>编码:</td>
							<td><input id="t_code" class="easyui-textbox"
								style="width: 200px; height: 20px;" type="text" name="f_t_code"
								required data-options="editable:false"></input></td>
						</tr>
						<tr>
							<td>客户:</td>
							<td><input id="t_customerId" type="text" style="display:none" value="" />
							<input id="t_customer" class="easyui-combobox"
								name="f_t_customer" style="width: 100%;"
								data-options="
							prompt:'输入要检索的客户名称',
							url:'<%=basePath%>customer/find.do',
							mode:'remote',
							method:'get',
							valueField:'id',
							textField:'q',
							panelHeight:'auto',
							hasDownArrow:false,
							filter: function(q, row){
								var opts = $(this).combobox('options');
								return row[opts.textField].indexOf(q) == 0;
							},
							onBeforeLoad: function(param){
								if(param == null || param.q == null || param.q.replace(/ /g, '') == ''){
									var value = $(this).combobox('getValue');
									if(value){// 修改的时候才会出现q为空而value不为空
										param.id = value;
										return true;
									}
									return false;
								}
							},
							onSelect:function(record){
								
								var id = record.id;
								if(id==-1){
									
									addCustomer();
									return;
								}
								
								$('#t_customerId').val(id);
								
								var str = '';
								str += 'cd.id=' + id;
								
								$('#i_contact').combogrid({
									url:'<%=basePath%>contact/find.do?' + str.trim()
								});
							}
							"></input>
							</td>
						</tr>
						<tr>
							<td>产品:</td>
							<td><input id="t_product" class="easyui-combobox"
								name="f_t_customer" style="width: 100%;"
								data-options="
									prompt:'输入要检索的产品名称',
									url:'<%=basePath%>product/search.do',
									mode:'remote',
									method:'get',
									valueField:'id',
									textField:'q',
									panelHeight:'auto',
									hasDownArrow:false,
									filter: function(q, row){
										var opts = $(this).combobox('options');
										return row[opts.textField].indexOf(q) == 0;
									},
									onBeforeLoad: function(param){
										if(param == null || param.q == null || param.q.replace(/ /g, '') == ''){
											var value = $(this).combobox('getValue');
											if(value){// 修改的时候才会出现q为空而value不为空
												param.id = value;
												return true;
											}
											return false;
										}
									}
							"></input>
							</td>
						</tr>
						<tr>
							<td></td>
							<td id="choseProducts"></td>
						</tr>
						<tr>
							<td>备注:</td>
							<td><input id="t_remarks" class="easyui-textbox"
								style="width: 200px; height: 40px;" type="text" name="f_t_remarks"
								data-options="multiline:true"></input></td>
						</tr>
					</table>
					<div style="text-align: center; padding: 5px">
						<a id="t_bsubmit" href="javascript:void(0)" style="width: 80px;"
							class="easyui-linkbutton">提交</a> 
						<a id="t_bmodify" href="javascript:void(0)" style="width: 80px;"
							class="easyui-linkbutton">修改</a> 
						<a id="t_bcancel"
							href="javascript:void(0)" style="width: 80px;"
							class="easyui-linkbutton">取消</a>
					</div>
				</form>
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