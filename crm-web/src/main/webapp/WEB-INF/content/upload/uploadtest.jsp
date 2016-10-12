<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
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
    
    var btnNum = 1;
    var runType = 0;
    
    $(function(){
    	
       $("#btn").click(function(){
    	   
    	   var filediv = "<div id='_file" + btnNum + "'>" +
    	   					"<input type='file' name='file' id='file" + btnNum + "' style='width:250px;' /><input type='text' name='filename' style='display:none;' />" +
    	   					"<input type='button' name='delbtn" + btnNum + "' value='删除' onClick='delfilearea(" + btnNum  + ")'>" + 
    	   					"<input type='button' name='upbtn" + btnNum + "' value='上传' onClick='ajaxFileUpload("  + btnNum + ")'>" + 
    	   					"</div>";
           
           $("#filearea").append(filediv);
           
           btnNum ++;
       });
    });
    
    function delfilearea(btnNum){
    	
    	var filearea = $('#_file'+btnNum);
    	filearea.remove();
    }
       
    function getFileName(o){
        var pos=o.lastIndexOf("\\");
        return o.substring(pos+1);  
    }
    
    function getFileName2(){
    	var file = $("#file").val();
    	var strFileName=file.replace(/^.+?\\([^\\]+?)(\.[^\.\\]*?)?$/gi,"$1");  //正则表达式获取文件名，不带后缀
    	var FileExt=file.replace(/.+\./,"");   //正则表达式获取后缀
    }
    
    function ajaxFileUpload(btnNum) {
    	
    	$("#wait_loading").window('open');
    	
        var file = $("#file"+btnNum).val();
        var fileName = getFileName(file);
        
        $.ajaxFileUpload({
            url: '<%=basePath%>file/upload.do', 
            type: 'post',
            secureuri: false, //一般设置为false
            fileElementId: 'file'+btnNum, // 上传文件的id、name属性名
            dataType: 'text', //返回值类型，一般设置为json、application/json
            data:{fileName:fileName, taskId:4, itemId:5},
            success: function(data, status){ 
            	
            	$("#wait_loading").window('close');
            	var obj = eval('(' + data + ')');
            	if(typeof(obj.error) != 'undefined'){
                    if(obj.error != '')
                    {
                        alert(obj.error);
                    }else{    
                        alert("上传成功");
                        $("#filename").attr("value",obj.msg);
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
</head>

<body>
	<div class="easyui-layout" data-options="fit:true">
		<div id="filearea"></div>
		<input type="button" value="添加" id="btn">
		<br>
		
		<div id="wait_loading" class="easyui-window"
			data-options="modal:true,closed:true,minimizable:false,collapsible:false,
			maximizable:false,noheader:true,border:false">
			<img src="<%=basePath %>images/loading-1.gif" />
		</div>
	</div>
</body>
</html>