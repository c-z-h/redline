<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basepath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ERIST</title>
    <link href="http://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="css/htmleaf-demo.css">
    <link rel="stylesheet" href="assets/css/jquery.mCustomScrollbar.min.css" />
    <link rel="stylesheet" href="assets/css/custom.css">
    <link rel="stylesheet" href="assets/css/Indicator_Result.css">
</head>
<body>
    <div class="page-wrapper toggled">
    <nav id="sidebar" class="sidebar-wrapper">
    <div class="sidebar-content">
    <a href="#" id="toggle-sidebar"><i class="fa fa-bars"></i></a>
    <div class="sidebar-brand">
    <a href="index.html">ERIST</a>
    </div>
    <div class="sidebar-header">
    <div class="user-pic">
    <img class="img-responsive img-rounded" src="assets/img/user.jpg" alt="">
    </div>
    <!--<div class="user-info">-->
    <!--<span class="user-name">Zhaoning <strong>Yu</strong></span>-->
    <!--<span class="user-role">Administrator</span>-->
    <!--<div class="user-status">-->
    <!--<a href="#"><span class="label label-success">Online</span></a>-->
    <!--</div>-->
    <!--</div>-->
    </div><!-- sidebar-header  -->
    <!--<div class="sidebar-search">-->
    <!--<div>-->
    <!--<div class="input-group">-->
    <!--<input type="text" class="form-control search-menu" placeholder="Search for...">-->
    <!--<span class="input-group-addon"><i class="fa fa-search"></i></span>-->
    <!--</div>-->
    <!--</div>-->
    <!--</div>&lt;!&ndash; sidebar-search  &ndash;&gt;-->
    <div class="sidebar-menu">
    <ul>
    <li class="header-menu"><span>主要功能</span></li>
    <li class="sidebar-dropdown">
    <a  href="#" ><i class="fa fa-tv"></i><span>文献查询</span><span class="label label-danger"></span></a>
    <div class="sidebar-submenu">
    <ul>
    <li><a href="Lite_Query.html">文献介绍<span class="label label-success"></span></a> </li>
    </ul>
    </div>
    </li>
    <li class="sidebar-dropdown">
    <a href="#"><i class="fa fa-photo"></i><span>指标体系查询</span><span class="badge"></span></a>
    <div class="sidebar-submenu">
    <ul>
    <li><a href="Indicator_Query.html">指标体系介绍<span class="badge"></span></a></li>
    <li><a href="indicatorAdd.jsp">个性化指标体系服务 <span class="badge"></span></a></li>
    </ul>
    </div>
    </li>
    <li class="sidebar-dropdown">
    <a href="#"><i class="fa fa-bar-chart-o"></i><span>红线区查询</span></a>
    <div class="sidebar-submenu">
    <ul>
    <li><a href="RL_Query.html">红线区介绍</a></li>
    </ul>
    </div>
    </li>
    <li class="sidebar-dropdown active">
    <a href="#"><i class="fa fa-diamond"></i><span>知识图谱查询</span></a>
    <div class="sidebar-submenu" style="display: block;">
    <ul>
                       <li><a href="knowledge_graph_search.html">知识库查询</a></li>
                       <li><a href="knowledgeService.html">知识库应用</a></li>
                       <li><a href="Dictionary">知识库字典</a></li>
    </ul>
    </div>
    </li>

    <li class="sidebar-dropdown">
    <a href="Feedback.html"><i class="fa fa-diamond"></i><span>反馈与问题</span></a>
    </li>
    </ul>
    </div><!-- sidebar-menu  -->
    </div><!-- sidebar-content  -->

    <div class="sidebar-footer">
    <a href="#"><i class="fa fa-bell"></i><span class="label label-warning notification"></span></a>
    <a href="#"><i class="fa fa-envelope"></i><span class="label label-success notification"></span></a>
    <a href="#"><i class="fa fa-gear"></i></a>
    <a href="#"><i class="fa fa-power-off"></i></a>
    </div>
    </nav><!-- sidebar-wrapper  -->
    <main class="page-content">
    <div class="container-fluid">
    <header class="htmleaf-header" id ="main-content">
    <div class="img">
    <img src="media/img/logo.png" class="logo">
    </div>
    <div class="indicator" style="border-right: 1px #9c9c9c solid;width: 70%;">
<!-- 		<h1>知识图谱查询<a href="Knowledge?keyword=redline" style="color:white">（总览）</a></h1> -->
<!--     <h3><a href="knowledgeDictionary.html" style="color:white">知识字典</a></h3> -->
	
    <div class="Main_Search">
    	<h3><span style="font-size:14px;">
    	使用指南：
    	左边是当前知识与其余知识的关系网图，蓝色节点为当前知识，点击相应的节点可展开对应知识关系网。
    	右边是以表格的形式展示当前知识有直接父子关系的知识。
    	</span></h3>
	    <form action="Knowledge" method="get" id="search_form">
		    <div class="RL_Query">
			    <div>
				    <div class="input-group">
					    <input type="text" name="keyword" class="form-control search-menu indi_input" placeholder="">
					    <span class="input-group-addon"><a id="submitBtn" onclick="document:search_form.submit()" style="cursor: pointer;"><i class="fa fa-search"></i></a></span>
				    </div>
			    </div>
		    </div>
	    </form>
	    
		<div id="chart">
		<div class="panel panel-info">
			<div class="panel-body">
				<iframe src="./chart.jsp" width="100%" height="500" frameborder="0" scrolling="no" webkitallowfullscreen="" mozallowfullscreen="" allowfullscreen=""></iframe>
			</div>
		</div>
		</div>
		

    </div>
    </div>
    <div class="indicator_result" style="border-left:0px #9c9c9c solid;width:30%;margin:5% 0;height:auto;">
    <h3 id="ontKey" style="color:white;">${ontology.ename}</h3>
    <div class="query_result" style='border:0px;'>
    <c:set var="list" value="${requestScope.entityList}"/>
	<ul class="list-group" style="color:black;">
	<li class="list-group-item" style="color:#494A5F">${ontology.comment}</li>
    <c:forEach var="entity" items="${list}">
	<li class="list-group-item" style="background-color:#494A5F"><a href="Knowledge?keyword=${entity.ename}" style="color:white">${entity.ename}</a></li>
	</c:forEach>
	</ul>


    </div>
    </div>

    <script>
    $(document).ready(function(){
    var x = $("#Copyright").css("width");
    var y = 2000 / x;
    $("#Copyright").attr("margin-top", y );
    })
    </script>
    </header>
    </div>
    </main><!-- page-content" -->
    </div><!-- page-wrapper -->



<script src="http://cdn.bootcss.com/jquery/1.11.0/jquery.min.js" type="text/javascript"></script>
<script>window.jQuery || document.write('<script src="js/jquery-1.11.0.min.js"><\/script>')</script>
<script src="http://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="assets/js/jquery.mCustomScrollbar.concat.min.js"></script>
<script src="assets/js/custom.js"></script>
</body>
</html>
