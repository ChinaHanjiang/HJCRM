<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="java.util.*,com.chinahanjiang.crm.pojo.*"%>
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
<script type="text/javascript" src="<%=basePath%>js/ajaxfileupload.js"></script>
<script type="text/javascript">
	
	var _i_init = 1;
	var btnNum = 1;
	/*定义全局变量*/
	var _i_id;
	var productDiv = "";
	var hasCustomer = 0;
	var _i_itemtypeId = 0;
	var _i_contactId = 0;
	var _i_customerId = 0;
	var _i_contact;
	var _i_name;
	var _i_code;
	var _i_remarks;
	
	$(document).ready(function(){
		
		<%
			Task task = (Task)request.getAttribute("task");
			Customer customer = task.getCustomer();
			int type = (Integer)request.getAttribute("type");
			List<Product> products = null;
			if(type==1){
				
				products = task.getProducts();
		%>
				$('#itemdeit').panel({
					title:'任务添加'
				});
				_i_itemypteId = 0;
				_i_contactId = 0;
				_i_contact = '';
				$('#i_bsubmit').show();
				$('#i_bmodify').hide();
		<%
			} else {
				
				Item item = (Item)request.getAttribute("item");
				if(customer==null)
					customer = item.getCustomer();
				
				products = item.getProducts();
				if(products == null) {
					products = task.getProducts();
				}
		%>
				$('#itemdeit').panel({
					title:'任务修改'
				});
				_i_id = <%=item.getId()%>;
				_i_itemtypeId = <%=item.getItemType().getId()%>;
		<%
				Contact contact = item.getContact();
				if(contact!=null){
					
		%>
					_i_contactId = <%=item.getContact().getId()%>;
					_i_contact = '<%=item.getContact().getName()%>';
		<%
				}
		%>		
				
				_i_name = '<%=item.getName()%>';
				_i_code = '<%=item.getCode()%>';
				_i_remarks = '<%=item.getRemarks()%>';
				
				$('#i_name').textbox('setText','<%=item.getName()%>');
				$('#i_remarks').textbox('setText','<%=item.getRemarks()%>');
				
				$('#i_itemtype').combobox('disable');
				$('#i_bmodify').show();
				$('#i_bsubmit').hide();
/*				
				$('#i_product').textbox('disable');
				$('#chkOne').attr("disabled","disabled");
 				$('#tr_chkOne').remove();
				$('#tr_product').remove();
				$('#tr_choseProducts').remove();
*/				
		<%
			}
		%>
		
		<%
			
			if(customer!=null){
		%>
				hasCustomer = 1;
				
				$('#i_customer').combobox({
					editable:false
				});
				
				$('#i_customerId').val(<%=customer.getId()%>);
				_i_customerId = <%=customer.getId()%>;
				
				$('#i_customer').combobox('setText','<%=customer.getName()%>');
				
				$('#i_contact').combogrid({
					url:'<%=basePath%>contact/find.do?cd.id=' + <%=customer.getId()%>
				});
				
		<%
			} else {
				
		%>
				$('#i_customer').combobox({
					editable:true
				});
				
				_i_init = 0;
				_i_customerId = 0;
		<%
			}
		
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
		%>
		
		$('#choseProducts').append(productDiv);
		
		$('#i_bsubmit').click(function(){
			
			var t_id;
			var i_name;
			var i_code;
			var i_contactId;
			var i_remarks;
			var i_itemtypeId;
			var i_itemtype;
			var i_customerId;
			var i_product = [];
			var i_oproduct = "";
			var i_product_add = [];
			var i_product_delete = [];
			var i_productadd;
			var i_productdelete;
			var j=0;
			var k=0;
			var i_flag = -1;
			
			t_id = $('#i_taskId').val();
			i_name = $('#i_name').textbox('getValue');
			i_code = $('#i_code').textbox('getValue');
			i_itemtypeId = $("#i_itemtype").combobox('getValue');
			i_itemtype = $("#i_itemtype").combobox('getText');
			i_remarks = $('#i_remarks').textbox('getText');
			i_contactId = $('#i_contact').combogrid('getValue');
			
			if($('#chkOne').is(':checked')){
				
				$("input[name^='product']").each(function(i, o){
				    i_product[i] = $(o).val();
				    
				    var isH = _t_product.indexOf(i_product[i]);
				    if(isH==-1){
				    	
				    	i_product_add[j] = i_product[i];
				    	j++;
				    	
				    } else {
				    	
				    	i_oproduct += i_product[i];
				    }
				});
				
				var t_products = _t_product.split(',');
				$.each( t_products, function(i, n){
					
					var isR = i_oproduct.indexOf(n);
					
					if(isR==-1){
						
						i_product_delete[k] = t_products[i];
						k++;
					}
				});
				
				var addlen = i_product_add.length;
				if(addlen!=0){
					i_productadd = "";
				}
				$.each( i_product_add, function(i, n){
					if(i!=addlen-1){
						
						i_productadd += n + ",";
					} else {
						
						i_productadd += n;
					}
					
				});
				
				var deletelen = i_product_delete.length;
				if(deletelen!=0){
					i_productdelete = "";
				}
				$.each( i_product_delete, function(i, n){
					
					if(i!=deletelen-1){
						
						i_productdelete += n + ",";
					} else {
						
						i_productdelete += n;
					}
					
				});
			}
			
			var l = $('#addItemForm').form('enableValidation').form('validate');
			if(l){
				
				var isR = i_itemtype.indexOf('报价');
				if(isR!=-1){
					
					i_flag = 0;
				}
				
				var str = '';
				str += "td.id=" + t_id;
				if(i_productadd!=undefined)
					str += "&td.addProducts=" + i_productadd;
				if(i_productdelete!=undefined)
					str += "&td.deleteProducts=" + i_productdelete;
				str += "&id.name=" + i_name;
				str += "&id.code=" + i_code;
				str += "&id.itemTypeId=" + i_itemtypeId;
				str += "&id.remarks=" + i_remarks;
				str += "&id.contactId=" + i_contactId;
				str += "&id.flag=" + i_flag;
				if(hasCustomer!=1){
					
					i_customerId = $('#i_customerId').val();
					str += "&id.customerId=" + i_customerId;
				}
				
				$.ajax({
					 type:"POST",
		    		 url:"<%=basePath%>item/add.do?" + str,
		    		 cache:false,
		    		 dataType:'json',
		    		 success:function(data){
		    			 
		    			 var _data = data.md;
		    			 if(_data.t){
		    				 
		    				var id = _data.intF;
		    				_i_id = id;
	    				 	var itemtype = $("#i_itemtype").combobox('getText');
	    				 	var isHas = itemtype.indexOf('报价');
	    				 	if(isHas!=-1){
	    				 		
	    				 		$.messager.confirm('Confirm','你是否要为这个任务报价呢？',function(r){
			    					 
			    					 if(r){
			    						 
			    						 $.ajax({
			    							 type:"POST",
			    				    		 url:"<%=basePath%>quote/add.do?itemId=" + id,
			    				    		 cache:false,
			    				    		 dataType:'json',
			    				    		 success:function(data){
			    				    			 
			    				    			 var _data = data.md;
			    				    			 if(_data.t){
			    				    			 	
			    				    				 var pqId = _data.intF;
			    				    				 var newObj = {
							    						 id:'quotewindow',
							    						 title:'产品报价',
							    						 href:'<%=basePath%>win/quote.do?itemId=' + id + "&quoteId=" + pqId
								    				 };
								    				 
								    				 parent.openAndClose(newObj,'添加任务');
								    				 
			    				    			 } else {
			    				    				 
			    				    				 $.messager.alert('失败', _data.message,
			    										'info');
			    				    			 }
			    				    		 }
			    						 });
			    						 
			    					 } else {
			    						
			    						var taskId = $('#i_taskId').val();
										var newObj = {
				    						 id:'taskdetail',
				    						 title:'项目详情',
				    						 href:'<%=basePath%>win/taskdetail.do?taskId=' + taskId
					    				 };
					    				 
					    				 parent.openAndClose(newObj,'添加任务');
			    						 
			    					 }
	    				 		});
	    				 	} else {
	    				 		
	    				 		//询问是否需要上传附件,上传完成后需询问是否关闭项目
	    				 		$.messager.confirm('Confirm','你是否要为这个任务 上传附件呢？',function(r){
			    					 
			    					 if(r){
			    					 
			    						 $('#addFileWindow').window('open');
			    						 
			    					 } else {
			    						 
			    						var taskId = $('#i_taskId').val();
										var newObj = {
				    						 id:'taskdetail',
				    						 title:'项目详情',
				    						 href:'<%=basePath%>win/taskdetail.do?taskId=' + taskId
					    				 };
					    				 parent.openAndClose(newObj,'添加任务');
			    						 
			    					 }
	    				 		});
	    				 	}
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
		
		$('#i_bmodify').click(function(){
			
			var t_id;
			var i_name;
			var i_code;
			var i_contactId;
			var i_remarks;
			var i_itemtypeId;
			var i_itemtype;
			var i_customerId;
			var i_product = [];
			var i_oproduct = "";
			var i_product_add = [];
			var i_product_delete = [];
			var i_productadd;
			var i_productdelete;
			var j=0;
			var k=0;
			var i_flag = -1;
			
			t_id = $('#i_taskId').val();
			i_name = $('#i_name').textbox('getText');
			i_code = $('#i_code').textbox('getText');
			i_remarks = $('#i_remarks').textbox('getText');
			i_customerId = $('#i_customerId').val();
			i_contactId = $('#i_contact').combogrid('getValue');
			i_itemtypeId = $("#i_itemtype").combobox('getValue');
			i_itemtype = $("#i_itemtype").combobox('getText');
			
			/* var grid = $('#i_contact').combogrid('grid');	
			var row = grid.datagrid('getSelected');	
			i_contactId = row.id; */
			
			if($('#chkOne').is(':checked')){
				
				$("input[name^='product']").each(function(i, o){
				    i_product[i] = $(o).val();
				    
				    var isH = _t_product.indexOf(i_product[i]);
				    if(isH==-1){
				    	
				    	i_product_add[j] = i_product[i];
				    	j++;
				    	
				    } else {
				    	
				    	i_oproduct += i_product[i];
				    }
				});
				
				var t_products = _t_product.split(',');
				$.each( t_products, function(i, n){
					
					var isR = i_oproduct.indexOf(n);
					
					if(isR==-1){
						
						i_product_delete[k] = t_products[i];
						k++;
					}
				});
				
				var addlen = i_product_add.length;
				if(addlen!=0){
					i_productadd = "";
				}
				$.each( i_product_add, function(i, n){
					if(i!=addlen-1){
						
						i_productadd += n + ",";
					} else {
						
						i_productadd += n;
					}
					
				});
				
				var deletelen = i_product_delete.length;
				if(deletelen!=0){
					i_productdelete = "";
				}
				$.each( i_product_delete, function(i, n){
					
					if(i!=deletelen-1){
						
						i_productdelete += n + ",";
					} else {
						
						i_productdelete += n;
					}
					
				});
			}
			
			var l = $('#addItemForm').form('enableValidation').form('validate');
			if(l){
				
				var isChange = false;
				
				if(_i_name != i_name){
					
					isChange = true;
				}
				if(_i_code != i_code){
					
					isChange = true;
				}
				if(hasCustomer!=1){
					
					if(_i_customerId != i_customerId){
						
						isChange = true;
					}
				}
				if(_i_itemtypeId != i_itemtypeId){
					
					isChange = true;
					
					var isR = i_itemtype.indexOf('报价');
					if(isR!=-1){
						
						i_flag = 0;
					}
				}
				if(_i_contactId != i_contactId){
					
					isChange = true;
				}
				if(_i_remarks != i_remarks){
					
					isChange = true;
				}
				if(i_productadd!=undefined){
					
					isChange = true;
				}
				if(i_productdelete!=undefined){
					
					isChange = true;
				}
				
				if(isChange){
					
					var str = '';
					str += "id.id=" + _i_id;
					str += "&td.id=" + t_id;
					if(i_productadd!=undefined)
						str += "&td.addProducts=" + i_productadd;
					if(i_productdelete!=undefined)
						str += "&td.deleteProducts=" + i_productdelete;
					str += "&id.name=" + i_name;
					str += "&id.code=" + i_code;
					str += "&id.itemTypeId=" + i_itemtypeId;
					str += "&id.remarks=" + i_remarks;
					str += "&id.contactId=" + i_contactId;
					str += "&id.flag=" + i_flag;
					if(hasCustomer!=1){
						
						i_customerId = $('#i_customerId').val();
						str += "&id.customerId=" + i_customerId;
					}
					
					$.ajax({
						 type:"POST",
			    		 url:"<%=basePath%>item/modify.do?" + str,
			    		 cache:false,
			    		 dataType:'json',
			    		 success:function(data){
			    			 
			    			 var _data = data.md;
			    			 if(_data.t){
			    				 
			    				var taskId = $('#i_taskId').val();
								var newObj = {
		    						 id:'taskdetail',
		    						 title:'项目详情',
		    						 href:'<%=basePath%>win/taskdetail.do?taskId=' + taskId
			    				 };
			    				 
			    				 parent.openAndClose(newObj,'任务编辑');
			    				 
			    			 } else {
			    				 
			    				 $.messager.alert('失败', _data.message,
									'info');
			    			 }
			    		 }
					});
					
				} else {
					
					$.messager.alert('注意', '信息没有修改！',
					'info');
				}
				
			} else {
				
				$.messager.alert('注意', '请填完整的表单！',
				'info');
			}
		});
		
		$('#i_bcancel').click(function(){
			
			var taskId = $('#i_taskId').val();
			
			var newObj = {
				 id:'taskdetail',
				 title:'项目详情',
				 href:'<%=basePath%>win/taskdetail.do?taskId=' + taskId
			 };
			
			 parent.openAndClose(newObj,'任务编辑');
			
		});
		
		$('#i_product').combobox({
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
		
		$('#i_itemtype').combobox({ 
		      editable:false, //不可编辑状态
		      required:true, 
		      cache: false,
		      panelHeight: 'auto',//自动高度适合
		      onLoadSuccess:function(){
		    	  
		    	  $(this).combobox('setValue', _i_itemtypeId);
		      }
		});
		
		$('#chkOne').click(function(){
			
			if($('#chkOne').is(':checked')){
				
				$('img').show();
				$('#i_product').combobox({
					editable:true
				});
				
			} else {
				
				$('#i_product').combobox({
					editable:false
				});
				$('#choseProducts').html('');
				$('#choseProducts').append(productDiv);
			}
		});
	
		$("#btn").click(function(){
		    	   
	    	   var filediv = "<div id='_file" + btnNum + "'>" +
	    	   					"<input type='checkbox' name='fileCheckBox' id='chk_" + btnNum + "' value='" + btnNum + "' />" +
	    	   					"<input type='file' name='file' id='file" + btnNum + "' style='width:250px;' /><input id='filename" + btnNum + "' type='text' name='filename' style='display:none;' />" +
	    	   					"<input type='button' name='delbtn" + btnNum + "' value='删除' onClick='delfilearea(" + btnNum  + ")'>" + 
	    	   					"<input type='button' name='upbtn" + btnNum + "' value='上传' onClick='ajaxFileUpload("  + btnNum + ")'>" + 
	    	   					"</div>";
	           
	           $("#filearea").append(filediv);
	           
	           btnNum ++;
	   });
		
		$('#f_bsubmit').click(function(){
			
			var all = $("input[name='fileCheckBox']");
			var checkboxs = $("input[name='fileCheckBox']:checked");
			
			if(all.length != checkboxs.length){
				$.messager.confirm('Confirm','有些文件没上传，是不是要保存！',function(r){
					 
					 if(r){
						 
						 saveAttachements(checkboxs);
					 }
				});
			} else {
				
				saveAttachements(checkboxs);
			}
			
		});
		
		$('#f_cancel').click(function(){
			
			
			
		});
	});
	
	function saveAttachements(checkboxs){
		
		var attachmentPath = new Array();
		var taskId = $('#i_taskId').val();
		
		$.each(checkboxs,function(i,o){
			
			var v = $(o).attr('value');
			var path = $('#filename'+v).attr('value');
			attachmentPath[i] = path;
		});
		
		var str = "id.attachments = " + attachmentPath;
			str += "&id.id=" + _i_id;
		
		$.ajax({
			 type:"POST",
    		 url:"<%=basePath%>item/saveattttachments.do?" + str,
    		 cache:false,
    		 dataType:'json',
    		 success:function(data){
    		 
    		 }
		});
		
	}
	
	function delfilearea(btnNum){
    	
    	var filearea = $('#_file'+btnNum);
    	filearea.remove();
    }
       
    function getFileName(o){
        var pos=o.lastIndexOf("\\");
        return o.substring(pos+1);  
    }
    
    function ajaxFileUpload(btnNum) {
    	
    	$("#wait_loading").window('open');
    	
        var file = $("#file"+btnNum).val();
        var fileName = getFileName(file);
        var taskId = $('#i_taskId').val();
        
        $.ajaxFileUpload({
            url: '<%=basePath%>file/upload.do', 
            type: 'post',
            secureuri: false, //一般设置为false
            fileElementId: 'file' + btnNum, // 上传文件的id、name属性名
            dataType: 'text', //返回值类型，一般设置为json、application/json
            data:{fileName:fileName, taskId:taskId, itemId:_i_id},
            success: function(data, status){ 
            	
            	$("#wait_loading").window('close');
            	var obj = eval('(' + data + ')');
            	if(typeof(obj.error) != 'undefined'){
                    if(obj.error != '')
                    {
                        alert(obj.error);
                    }else{    
                        alert("项目："+taskId+",任务:" + _i_id + "的附件上传成功！");
                        $('#chk_'+btnNum).attr("checked","checked");
                        $("#filename"+btnNum).attr('value',obj.path);
                    }
                }
            },
            error: function(data, status, e){
            	
            	$("#wait_loading").window('close');
                alert(e);
            }
        });
    }
	</script>
<title>添加任务</title>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
		<div id="itemdeit" title="事件" class="easyui-panel"
			data-options="border:true" style="width: 450px;">
			<div id="f_item" style="padding: 10px;">
				<div style="padding: 0px 0px 0px 50px">
					<form id="addItemForm" method="post">
						<table cellpadding="5">
							<tr>
								<td><input id="i_taskId" type="text" style="display: none"
									value='${task.id }' /> 项目编码:</td>
								<td>${task.code }</td>
							</tr>
							<tr>
								<td>项目名称:</td>
								<td>${task.name }</td>
							</tr>
							<tr>
								<td>客户:</td>
								<td><input id="i_customerId" type="text"
									style="display: none" value="" /> <input id="i_customer"
									class="easyui-combobox" name="f_i_customer" required
									style="width: 100%;"
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
											
											$('#i_customerId').val(id);
											
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
								<td>任务内容:</td>
								<td><input id="i_name" class="easyui-textbox"
									style="width: 200px; height: 40px;" type="text" name="f_i_name"
									required data-options="multiline:true"></input></td>
							</tr>
							<tr>
								<td>编码:</td>
								<td><input id="i_code" class="easyui-textbox"
									style="width: 200px; height: 20px;" type="text" name="f_i_code"
									required data-options="editable:false" value="${itemCode}" /></td>
							</tr>
							<tr>
								<td>任务类型:</td>
								<td><select id="i_itemtype" class="easyui-combobox"
									style="width: 200px" name="f_i_itemtype">
										<option value=0 selected="selected">请选择</option>
										<s:if test="itemTypes!=null">
											<s:iterator value="itemTypes" var="i">
												<option value=${i.id }>${i.name }</option>
											</s:iterator>
										</s:if>
								</select></td>
							</tr>

							<tr id="tr_chkOne">
								<td>是否修改产品:</td>
								<td><input type="checkbox" name="myBox" id="chkOne"
									value="1" /></td>
							</tr>
							<tr id="tr_product">
								<td>产品:</td>
								<td><input id="i_product" class="easyui-combobox"
									name="f_i_product" style="width: 100%;"
									data-options="
										prompt:'输入要检索的产品名称',
										url:'<%=basePath%>product/search.do',
										mode:'remote',
										method:'get',
										valueField:'id',
										editable:false,
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
							<tr id="tr_choseProducts">
								<td>选择的产品</td>
								<td id="choseProducts"><s:if test="task!=null">
										<s:iterator value="task.products" var="d">

										</s:iterator>
									</s:if></td>
							</tr>
							<tr>
								<td>联系人:</td>
								<td><select id="i_contact" class="easyui-combogrid"
									style="width: 200px;" required
									data-options=" 
							                    panelWidth: 500,
							                    idField: 'id',
							                    textField: 'name',
							                    columns: [[
							                        {field:'id',title:'序号',width:80,align:'center'},
							                        {field:'name',title:'名称',width:120,align:'center'},
							                        {field:'duty',title:'职务',width:80,align:'center'}
							                    ]],
							                    fitColumns: true,
							                    onLoadSuccess:function(){
							                    	
							                    	if(_i_init==1){
							                    	
							                    		<s:if test="{item.contact!=null}">
							                    		$('#i_contact').combogrid('setValue','${item.contact.id}');
							                    		</s:if>
							                    		
							                    		_i_init = 0;
							                    	}
							                    	
							                    }
							                ">
								</select></td>
							</tr>
							<tr>
								<td>备注:</td>
								<td><input id="i_remarks" class="easyui-textbox"
									style="width: 200px; height: 40px;" type="text"
									name="f_i_remarks" required data-options="multiline:true"></input></td>
							</tr>
						</table>
						<div style="text-align: center; padding: 5px">
							<a id="i_bsubmit" href="javascript:void(0)" style="width: 80px;"
								class="easyui-linkbutton">提交</a> <a id="i_bmodify"
								href="javascript:void(0)" style="width: 80px;"
								class="easyui-linkbutton">修改</a> <a id="i_bcancel"
								href="javascript:void(0)" style="width: 80px;"
								class="easyui-linkbutton">取消</a>
						</div>
					</form>
				</div>
			</div>
		</div>

		<div id="addFileWindow" class="easyui-window" title="添加附件"
			style="width: 450px; height: 300px;"
			data-options="modal:true,closed:true,minimizable:false,collapsible:false,
				maximizable:false">
			<div id="filearea" align="center" style="padding-top: 15px;"></div>
			<input type="button" value="添加" id="btn"> <br>
			<div align="center">
				<input type="button" id="f_bsubmit" name="_f_submit" value="提交" />
				<input type="button" id="f_bcancel" name="_f_cancel" value="取消" />
			</div>
		</div>

		<div id="wait_loading" class="easyui-window"
			data-options="modal:true,closed:true,minimizable:false,collapsible:false,
				maximizable:false,noheader:true,border:false">
			<img src="<%=basePath %>images/loading-1.gif" />
		</div>
	</div>
	<script type="text/javascript">
	
		function closeDiv(id){
			
			$('#pdiv' + id).remove();
		}
		
		function addProduct(){
			
			parent.addPanel('productsManager','产品管理','<%=basePath%>win/productlist.do');
		}
		
		function addCustomer(){
			
			parent.addPanel('customerlist','客户管理','<%=basePath%>win/customerlist.do');
		}
	</script>
</body>

</html>