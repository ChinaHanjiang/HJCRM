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
	$(document).ready(function(){
	
		//导入事件，显示导入弹出窗口
	    this.importClick = function ()
	    {
	        $('#import-excel-template').window('open');
	        document.getElementById("importFileForm").style.display = "block";
	    }
	    //关闭导入弹出窗口
	    this.closeImportClick = function () {
	        document.getElementById('fileImport').value = null;
	        document.getElementById('fileName').innerHTML = "";
	        document.getElementById('uploadInfo').innerHTML = "";
	        $('#import-excel-template').window('close');
	    };
	    
	    //导入文件
	    this.importFileClick = function ()
	    {
	        //获取上传文件控件内容
	        var file = document.getElementById('fileImport').files[0];
	        //判断控件中是否存在文件内容，如果不存在，弹出提示信息，阻止进一步操作
	        if (file == null) { alert('错误，请选择文件'); return; }
	        //获取文件名称
	        var fileName = file.name;
	        //获取文件类型名称
	        var file_typename = fileName.substring(fileName.lastIndexOf('.'), fileName.length);
	        //这里限定上传文件文件类型必须为.xlsx，如果文件类型不符，提示错误信息
	        if (file_typename == '.xlsx')
	        {
	            //计算文件大小
	            var fileSize = 0;
	            //如果文件大小大于1024字节X1024字节，则显示文件大小单位为MB，否则为KB
	            if (file.size > 1024 * 1024) {
	            	fileSize = Math.round(file.size * 100 / (1024 * 1024)) / 100;
	            	if (fileSize > 10) {
	                    alert('错误，文件超过10MB，禁止上传！'); return;
	                }
	            	fileSize = fileSize.toString() + 'MB';
	            }
	            else {
	                fileSize = (Math.round(file.size * 100 / 1024) / 100).toString() + 'KB';
	            }
	            //将文件名和文件大小显示在前端label文本中
	            document.getElementById('fileName').innerHTML = "<span style='color:Blue'>文件名: " + file.name + ',大小: ' + fileSize + "</span>";
	            //获取form数据
	            var formData = new FormData($("#importFileForm")[0]);
	            //调用apicontroller后台action方法，将form数据传递给后台处理。contentType必须设置为"multipart/form-data"
	            $.ajax({
	                url: "<%=basePath%>fileupload/save.do",
	                type: 'POST',
	                data: formData,
	                async: false,
	                cache: false,
	                //contentType: "multipart/form-data",
	                processData: false,
	                success: function (returnInfo) {
	                    //上传成功后将控件内容清空，并显示上传成功信息
	                    document.getElementById('fileImport').value = null;
	                    document.getElementById('uploadInfo').innerHTML = "<span style='color:Red'>" + returnInfo + "</span>";
	                },
	                error: function (returnInfo) {
	                    //上传失败时显示上传失败信息
	                    document.getElementById('uploadInfo').innerHTML = "<span style='color:Red'>" + returnInfo + "</span>";
	                }
	            });
	        }
	        else {
	            alert("文件类型错误");
	            //将错误信息显示在前端label文本中
	            document.getElementById('fileName').innerHTML = "<span style='color:Red'>错误提示:上传文件应该是.xlsx后缀而不应该是" + file_typename + ",请重新选择文件</span>";
	        }
	    }
		
	});
</script>
</head>
<body>
	<!-- <input type="button" onclick="importClick()" value="clickme" />
	<div class="easyui-window" id="import-excel-template" title="文件上传" style="width:400px;height:160px;padding:2px;" closed="true">
    <form id="importFileForm" method="post" enctype="multipart/form-data" style="display:none">
        <table style="margin:5px;height:70px;">
            <tr>
                <td>请选择文件</td>
                <td width="5px;"></td>
                <td><input type="file" id="fileImport" name="fileImport" style="width:260px;"></td>
                <td></td></tr>
            <tr>
                <td colspan="4"><label id="fileName" /></td>
            </tr>
            <tr>
                <td colspan="4">
                    <label id="uploadInfo" />
                </td>
            </tr>
        </table><div style="text-align:center;clear:both;margin:5px;">
            <a id="uploadFile" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick='importFileClick()' href="javascript:void(0)">上传</a>
            <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick='closeImportClick()' href="javascript:void(0)">关闭</a>
        </div>
    </form>
</div> -->
	<!--在进行文件上传时，表单提交方式一定要是post的方式，因为文件上传时二进制文件可能会很大，还有就是enctype属性，这个属性一定要写成multipart/form-data，
　　不然就会以二进制文本上传到服务器端--> 
　　<form action="<%=basePath%>fileupload/save.do" method="post" enctype="multipart/form-data">
    　　
        username: <input type="text" name="username"><br>
        file: <input type="file" name="file"><br>
        
        <input type="submit" value="submit">
    </form>
</body>
</html>