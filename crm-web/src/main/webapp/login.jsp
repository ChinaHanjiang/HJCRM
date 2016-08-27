<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://"
		+ request.getServerName() + ":" + request.getServerPort()
		+ path + "/";
%>
<html class="login-bg">
<head>
	<title>韩江机械管理网站</title>
    
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	
    <!-- bootstrap -->
    <link href="<%=basePath %>css/bootstrap/bootstrap.css" rel="stylesheet" />
    <link href="<%=basePath %>css/bootstrap/bootstrap-responsive.css" rel="stylesheet" />
    <link href="<%=basePath %>css/bootstrap/bootstrap-overrides.css" type="text/css" rel="stylesheet" />

    <!-- global styles -->
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/layout.css" />
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/elements.css" />
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/icons.css" />

    <!-- libraries -->
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/lib/font-awesome.css" />
    
    <!-- this page specific styles -->
    <link rel="stylesheet" href="<%=basePath %>css/compiled/signin.css" type="text/css" media="screen" />

    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /></head>
<body>

    <div class="row-fluid login-wrapper">
        <a href="#">
        </a>
        
        <div class="span4 box">
            <div class="content-wrap">
                <h6>韩江机械管理系统</h6>
                <form id="loginform" action="<%=basePath %>login/on.do" method="post">
	                <input id="cardName" name="ud.cardName" class="span12" type="text" placeholder="请输入用户名" />
	                <input id="password" name="ud.password" class="span12" type="password" placeholder="请输入密码" />
	                <!-- <a href="#" class="forgot">Forgot password?</a> -->
	                <div class="remember">
	                    <input id="remember-me" type="checkbox" />
	                    <label for="remember-me">记住我</label>
	                </div>
	                <a class="btn-glow primary login" href="#">登陆</a>
                </form>
            </div>
        </div>
		<!-- 
        <div class="span4 no-account">
            <p>Don't have an account?</p>
            <a href="signup.html">Sign up</a>
        </div>
         -->
    </div>

	<!-- scripts -->
    <script src="<%=basePath %>js/jquery.min.js"></script>
    <script src="<%=basePath %>js/bootstrap.min.js"></script>
    <script src="<%=basePath %>js/theme.js"></script>

    <!-- pre load bg imgs -->
    <script type="text/javascript">
        $(function () {
            // bg switcher
            var $btns = $(".btn-glow");
            $btns.click(function (e) {
            	
            	var form = $('#loginform');
            	var _cardName = $('#cardName').val();
            	var _password = $('#password').val();
            	if(_cardName==""){
            		
            		alert("用户名不能为空！");
            		return;
            	}
            	if( _password==""){
            		
            		alert("密码不能为空！");
            		return;
            	}
            	
            	form.submit();
            });

        });
    </script>
<div style="display:none"><script src='http://v7.cnzz.com/stat.php?id=155540&web_id=155540' language='JavaScript' charset='gb2312'></script></div>
</body>
</html>