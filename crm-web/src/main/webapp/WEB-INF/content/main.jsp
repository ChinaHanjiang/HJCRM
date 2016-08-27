<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
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
	href="<%=basePath%>themes/metro/easyui.css">
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>themes/icon.css">
<script type="text/javascript" src="<%=basePath%>js/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>js/jquery.easyui.min.js"></script>
	
<script type="text/javascript">
	$(document).ready(function(){
		
	});
</script>
<title>任务管理信息系统</title>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false"
		style="height: 60px; background: #B3DFDA; padding-top: 5px; padding-left: 15px;">

		<h2>韩江机械任务管理信息系统v1.0</h2>
	</div>
	<div data-options="region:'west',title:'导航栏',border:true"
		style="width: 150px;">
		<div class="easyui-accordion" data-options="fit:true,border:false">
			<div title="任务管理" style="padding: 10px;">
				<div style="width: 100%; text-align: center">
					<div style="padding-top: 10px; cursor: pointer;"
						onclick="addPanel('tasklist','积分列表','<%=basePath%>win/tasklist.do')">
						<img alt="任务列表" src="<%=basePath%>icon/document_new.png">
						<div>任务列表</div>
					</div>
					<div style="padding-top: 10px; cursor: pointer;"
						onclick="addPanel('itemlist','事件列表','<%=basePath%>win/itemlist.do')">
						<img alt="事件列表" src="<%=basePath%>icon/lists.png">
						<div>任务列表</div>
					</div>
				</div>
			</div>
			<div title="客户管理" data-options="selected:true" style="padding: 10px;">
				<div style="width: 100%; text-align: center">
					<div style="padding-top: 10px; cursor: pointer;"
						onclick="addPanel('customerlist',' 客户管理','<%=basePath%>win/customerlist.do')">
						<img alt="客户管理" src="<%=basePath%>icon/evolution_tasks.png">
						<div>客户管理</div>
					</div>
				</div>
			</div>
			<div title="报表管理" data-options="selected:true" style="padding: 10px;">
				<div style="width: 100%; text-align: center">
					<div style="padding-top: 10px; cursor: pointer;"
						onclick="addPanel('customerlist',' 客户管理','<%=basePath%>win/customerlist.do')">
						<img alt="客户管理" src="<%=basePath%>icon/evolution_tasks.png">
						<div>客户管理</div>
					</div>
				</div>
			</div>
			<div title="系统设置" style="padding: 10px">
				<div style="width: 100%; text-align: center">
					<div style="padding-top: 10px; cursor: pointer;"
						onclick="addPanel('userlist','用户管理','<%=basePath%>win/userlist.do')">
						<img alt="用户管理" src="<%=basePath%>icon/wlm.png">
						<div>用户管理</div>
					</div>
					<div style="padding-top: 10px; cursor: pointer;"
						onclick="addPanel('tasktypelist','任务类型管理','<%=basePath%>win/tasktypelist.do')">
						<img alt="任务类型管理" src="<%=basePath%>icon/wlm.png">
						<div>任务类型管理</div>
					</div>
					<div style="padding-top: 10px; cursor: pointer;"
						onclick="addPanel('authorityManager','集团管理','<%=basePath%>win/groupslist.do')">
						<img alt="集团管理" src="<%=basePath%>icon/key_blue.png">
						<div>集团管理</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div data-options="region:'south',border:false"
		style="height: 30px; background: #A9FACD; padding: 10px;">south
		region</div>
	<div data-options="region:'center'">
		<div id="tabs" class="easyui-tabs" data-options="border:false"
			style="width: auto; height: 660px;">
			<div title="主页" style="padding: 10px">

				<div style="padding-top: 15px;">

					<div style="padding-left: 20px; display: block; float: left;">
						<div id="p1" class="easyui-panel" title='当天的任务列表' data-options="border:true"
							style="width: 600px; height: 290px;">
							<table id="dalytaskgrid" class="easyui-datagrid"
								data-options="
											url:'<%=basePath %>task/dalytask.do',
											loadMsg:'数据加载中请稍后……',  
											rownumbers:true,
											height: 260, 
											collapsible:false,
											showHeader:false,
											pagination:true,
											border:false
											">
					
								<thead>
									<tr>
										<th data-options="field:'code',width:100">编号</th>
										<th data-options="field:'tasktype',width:100">类型</th>
										<th data-options="field:'name',width:100">内容</th>
										<th data-options="field:'customer',width:100">客户</th>
										<th data-options="field:'state',width:80">状态</th>
									</tr>
								</thead>
							</table>
						</div>
					</div>
					<div style="padding-left: 10px; display: block; float: left;">
						<div id="p2" class="easyui-panel" title='当天的事件列表' data-options="border:true"
							style="width: 600px; height: 290px;">
							<table id="dalyitemgrid" class="easyui-datagrid"
								data-options="
											url:'<%=basePath %>task/dalytask.do',
											loadMsg:'数据加载中请稍后……',  
											rownumbers:true,
											height: 260, 
											collapsible:false,
											showHeader:false,
											pagination:true,
											border:false
											">
					
								<thead>
									<tr>
										<th data-options="field:'code',width:100">编号</th>
										<th data-options="field:'tasktype',width:100">类型</th>
										<th data-options="field:'taskCode',width:100">任务号</th>
										<th data-options="field:'name',width:100">内容</th>
										<th data-options="field:'customer',width:100">客户</th>
										<th data-options="field:'state',width:80">状态</th>
									</tr>
								</thead>
							</table>
						</div>
					</div>
					
					<div style="clear: both;"></div>

					<div style="padding-left: 20px; padding-top:10px; display: block; float: left;">
						<div id="p4" class="easyui-panel" title="未完成任务列表" data-options="border:true"
						style="width: 600px; height: 290px;">
							<table id="undotaskgrid" class="easyui-datagrid"
								data-options="
											url:'<%=basePath %>task/undotask.do',
											loadMsg:'数据加载中请稍后……',  
											rownumbers:true,
											height: 260, 
											collapsible:false,
											showHeader:false,
											pagination:true,
											border:false
											">
					
								<thead>
									<tr>
										<th data-options="field:'code',width:100">编号</th>
										<th data-options="field:'tasktype',width:100">类型</th>
										<th data-options="field:'name',width:100">内容</th>
										<th data-options="field:'customer',width:100">客户</th>
										<th data-options="field:'state',width:80">状态</th>
									</tr>
								</thead>
							</table>
						</div>
					</div>
					<div
						style="padding-left: 10px; padding-top:10px; display: block; float: left;">
						<div id="p5" class="easyui-panel"  title="未完成事件"  data-options="border:true"
							style="width: 600px; height: 290px;">
								<table id="undoitemgrid" class="easyui-datagrid"
								data-options="
											url:'<%=basePath %>item/undoitem.do',
											loadMsg:'数据加载中请稍后……',  
											rownumbers:true,
											height: 260, 
											collapsible:false,
											showHeader:false,
											pagination:true,
											border:false
											">
								<thead>
									<tr>
										<th data-options="field:'code',width:100">编号</th>
										<th data-options="field:'tasktype',width:100">类型</th>
										<th data-options="field:'taskCode',width:100">任务号</th>
										<th data-options="field:'name',width:100">内容</th>
										<th data-options="field:'customer',width:100">客户</th>
										<th data-options="field:'state',width:80">状态</th>
									</tr>
								</thead>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">

	    function addPanel(id,title, href){  
	        var tt = $('#tabs');  
	        if (tt.tabs('exists', title)){//如果tab已经存在,则选中并刷新该tab          
	            tt.tabs('select', title);  
	            refreshTab({tabTitle:title,url:href});  
	        } else {  
	            if (href){  
	                var content = '<iframe scrolling="no" frameborder="0"  src="'+href+'" style="width:100%;height:100%;"></iframe>';  
	            } else {  
	                var content = '未实现';  
	            }  
	            tt.tabs('add',{ 
	            	id:id,
	                title:title,  
	                closable:true,  
	                content:content
	            });  
	        }  
	    }  
	    /**     
	     * 刷新tab 
	     * @cfg  
	     *example: {tabTitle:'tabTitle',url:'refreshUrl'} 
	     *如果tabTitle为空，则默认刷新当前选中的tab 
	     *如果url为空，则默认以原来的url进行reload 
	     */  
	    function refreshTab(cfg){  
	        var refresh_tab = cfg.tabTitle?$('#tabs').tabs('getTab',cfg.tabTitle):$('#tabs').tabs('getSelected');  
	        if(refresh_tab && refresh_tab.find('iframe').length > 0){  
	        var _refresh_ifram = refresh_tab.find('iframe')[0];  
	        var refresh_url = cfg.url?cfg.url:_refresh_ifram.src;  
	        //_refresh_ifram.src = refresh_url;  
	        _refresh_ifram.contentWindow.location.href=refresh_url;  
	        }  
	    }  
	</script>
</body>
</html>