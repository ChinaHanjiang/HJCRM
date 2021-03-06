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
		var productDiv = "";
		var _t_tasktypeId = 0;
		var _t_type = 0;
		var _t_customerId;
		<%
			int type = (Integer)request.getAttribute("type");
			if(type==1){
		%>
				_t_type = 1;
				$('#t_bsubmit').show();
				$('#t_bmodify').hide();
				$('#taskedit').panel({
					
					title:'项目添加'
				});
		
		<%
			} else {
				
				Task task = (Task)request.getAttribute("task");
				if(task!=null){
					Customer customer = task.getCustomer();
					
		%>
				
					_t_type = 2;
					$('#t_bsubmit').hide();
					$('#t_bmodify').show();
					$('#taskedit').panel({
						
						title:'项目修改'
					});
					$('#t_product').textbox('disable');
					
					var _t_id = <%=task.getId()%>;
					var _t_name = '<%=task.getName()%>';
					var _t_code = '<%=task.getCode()%>';
					var _t_tasktypeId = <%=task.getTaskType().getId()%>;
					var _t_tasktype = '<%=task.getTaskType().getName()%>';
					var _t_remarks = '<%=task.getRemarks()==null?"":task.getRemarks()%>';
					$('#t_name').textbox('setText',_t_name);
					$('#t_code').textbox('setText',_t_code);
					$('#t_remarks').textbox('setText',_t_remarks);
					
					
		<%
					if(customer!=null){
		%>
						_t_customerId = <%=customer.getId()%>;
						if(_t_customerId==""){
							_t_cusomerId = 0;
						}
						var _t_customer = '<%=customer.getName()%>';
						$('#t_customer').combobox('setText',_t_customer);
						
		<%
					}
				
				List<Product> products = task.getProducts();
				if(products!=null){
					
					String s = "";
					int i = 1;
					int len = products.size();
					Iterator<Product> it = products.iterator();
					while(it.hasNext()){
						
						Product p = it.next();
		%>
						productDiv += "<div id='pdiv<%=p.getId() %>' style='vertical-align:middle; line-height:25px; height:25px; margin-bottom:2px; background:#EDEDED; color:#000' >";
						productDiv += "<input name='product' id='product<%=p.getId() %>' type='text'  value=<%=p.getId() %> style='display:none;' />";
					    productDiv += '<%=p.getName() %>';
					    productDiv += "<img style='display:none; float:right; padding-top:5px; padding-right:8px;' src='<%=basePath%>icon/close.png' onClick='closeDiv(<%=p.getId() %>)'></div>";
		<%
						if(i!=len){
							
							s += p.getId() + ",";
						} else {
							
							s += p.getId();
						}
		
						i++;
					}
		%>
						var _t_product = '<%=s %>';
		<%
				}
			}
		}
		%>
		
		$('#choseProducts').append(productDiv);
		
		
		$('#t_bsubmit').click(function(){
			
			var t_name;
			var t_code;
			var t_customerId;
			var t_tasktypeId;
			var t_remarks;
			var t_product = [];
			
			t_name = $('#t_name').textbox('getText');
			t_code = $('#t_code').textbox('getText');
			t_customerId = $('#t_customer').combobox('getValue');
			if(t_customerId==""){
				t_customerId = 0;
			}
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
		    								 
		    								 id:'itemedit',
		    								 title:'任务编辑',
		    								 href:'<%=basePath%>win/additem.do?taskId='+id
		    						 };
		    						 
		    						 parent.openAndClose(newObj,'项目编辑');
		    						 
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
			
			var t_name;
			var t_code;
			var t_customerId;
			var t_remarks;
			var t_product = [];
			
			t_name = $('#t_name').textbox('getText');
			t_code = $('#t_code').textbox('getText');
			t_customerId = $('#t_customer').combobox('getValue');
			if(t_customerId==""){
				t_customerId = 0;
			}
			t_remarks = $('#t_remarks').textbox('getText');
			
			var t = $('#addTaskForm').form('enableValidation').form('validate');
			if(t){
				
				var isChange = false;
				
				if(_t_name != t_name){
					
					isChange = true;
				}
				if(_t_code != t_code){
					
					isChange = true;
				}
				if(_t_remarks != t_remarks){
					
					isChange = true;
				}
				if(_t_customerId != t_customerId){
					
					isChange=true;
				}
				if(_t_remarks != t_remarks){
					
					isChange = true;
				}
				
				if(isChange){
					
					var str = '';
					str += "td.id=" + _t_id;
					str += "&td.name=" + t_name;
					str += "&td.code=" + t_code;
					str += "&td.customerId=" + t_customerId;
					str += "&td.remarks=" + t_remarks; 
					
					$.ajax({
						 type:"POST",
			    		 url:"<%=basePath%>task/modify.do?" + str,
			    		 cache:false,
			    		 dataType:'json',
			    		 success:function(data){
			    			 
			    			 var _data = data.md;
			    			 if(_data.t){
			    				 
			    				 var id = _data.intF;
			    				 
			    				 var newObj = {
	    								 id:'tasklist',
	    								 title:'任务列表',
	    								 href:'<%=basePath%>win/taskdetail.do?taskId=' + id
	    						 };
	    						 
	    						 parent.openAndClose(newObj,'项目编辑');
			    				 
			    			 } else {
			    				 
			    				 $.messager.alert('失败', _data.message,
									'info');
			    			 }
			    		 }
					});
				}
			} else {
				
				$.messager.alert('注意', '请填完整的表单！',
				'info');
			}
			
		});
		
		$('#t_bcancel').click(function(){
			
			 parent.closePanel('项目编辑');
			
		});
		
		$('#t_tasktype').combobox({ 
		      editable:false, //不可编辑状态
		      required:true, 
		      cache: false,
		      panelHeight: 'auto',//自动高度适合
		      onChange:function(){
		    	  
		    	  if(_t_type==1){
		    		  
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
		      },
		      onLoadSuccess:function(){
		    	  
		    	  if(_t_type==2){
		    		  
		    		  $('#t_tasktype').textbox('setText',_t_tasktype);
		    		  $('#t_tasktype').textbox('disable');
		    	  }
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
								name="f_t_product" style="width: 100%;"
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