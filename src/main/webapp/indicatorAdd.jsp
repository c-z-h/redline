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
    <link href="https://cdn.bootcss.com/bootstrap-validator/0.5.3/css/bootstrapValidator.min.css" rel="stylesheet">
	<script src="http://cdn.bootcss.com/jquery/1.11.0/jquery.min.js" type="text/javascript"></script>
	<script>window.jQuery || document.write('<script src="js/jquery-1.11.0.min.js"><\/script>')</script>
	<script src="http://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src="assets/js/jquery.mCustomScrollbar.concat.min.js"></script>
	<script src="https://cdn.bootcss.com/bootstrap-validator/0.5.3/js/bootstrapValidator.min.js"></script>
	<script src="assets/js/custom.js"></script>
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
    <li><a href="Indicator_Query.html">指标体系介绍<span class="badge"></span></a></li><li><a href="indicatorAdd.jsp">个性化指标体系服务： <span class="badge"></span></a></li>
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
    <li class="sidebar-dropdown">
    <a href="#"><i class="fa fa-diamond"></i><span>知识图谱查询</span></a>
    <div class="sidebar-submenu">
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
    <div class="indicator" style="border-right: 0px #9c9c9c solid;width: 100%;">
   
		<div class="row">
		  <div class="col-lg-12">
		  	<h3><span style="font-size:18px;">使用指南：先输入新指标体系名称，点击添加新指标按钮即可添加新指标。完成后点击提交即可。刷新页面会清除所有指标。</span></h3>
		    <div class="input-group">
		      <span class="input-group-btn">
		        <button class="btn btn-default" type="button">新指标体系名称</button>
		      </span>
		      <form action="#" id="sform">
		      	<input type="text" class="form-control" placeholder="在这里输入新指标体系名称" id="isystem" name="isystem">
		      </form>
		      <span class="input-group-btn">
		        <button class="btn btn-default" type="button" style="margin-left:5px;" onclick="submitAll()">提交</button>
		      </span>
		    </div><!-- /input-group -->
		  </div><!-- /.col-lg-12 -->	  
		</div><!-- /.row -->
		
		<div class="row" style="height:35px;">
			<div class="col-lg-12">
				<div id="tip" style="text-align:left;"></div>
				<div id="say" style="text-align:left;"></div>
			</div>
		</div>
		
		<div class="row" style="">
	    <div class="col-lg-12">
		    <div class="input-group">
		      <span class="input-group-btn">
		        <button onclick="checkisystem()" class="btn btn-default btn-lg" type="button" style="width:100%"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>添加新指标</button>
		      </span>
		    </div><!-- /input-group -->
		  </div><!-- /.col-lg-12 -->
		</div>
		
		<!-- Modal -->
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        <h4 class="modal-title" id="myModalLabel" style="color:#494A5F">添加新指标</h4>
		      </div>
		      <div class="modal-body" style="color:black">
				 <form action="#" id="myform">
				  <div class="form-group" style="text-align:left;">
				    <label for="iname">指标名称</label>
				    <input type="text" class="form-control" id="iname" placeholder="指标名称" name="iname">
				  </div>
				  <div class="form-group" style="text-align:left;">
				    <label for="iweight">指标权重</label>
				    <input type="text" class="form-control" id="iweight" placeholder="指标权重" name="iweight">
				  </div>
				  <div class="form-group" style="text-align:left;">
				    <label for="isource">指标来源</label>
				    <input type="text" class="form-control" id="isource" placeholder="指标来源" name="isource">
				  </div>
				  <button type="button" class="btn btn-default" onclick="modalSave()">保存</button>
				  
				  <script type="text/javascript">
				  $('#myform').bootstrapValidator({
					  message: 'This value is not valid',
			          feedbackIcons: {
			        	  valid: 'glyphicon glyphicon-ok',
			        	  invalid: 'glyphicon glyphicon-remove',
			        	  validating: 'glyphicon glyphicon-refresh'
			        	  },
			        fields: {
			                iname: {
			                    validators: {
			                        notEmpty: {
			                            message: '指标名称不能为空'
			                        }
			                    }
			                },
			                iweight: {
			                    validators: {
			                        notEmpty: {
			                            message: '指标权重不能为空'
			                        },
				                    regexp: {
			                            regexp: /^(([1-9]+\.[0-9]+)|(0\.[0-9]+))$/,
			                            message: '指标权重只能是小数'
			                        }
			                    }
			                },
			                isource: {
			                    validators: {
			                        notEmpty: {
			                            message: '指标来源不能为空'
			                        }
			                    }
			                }
			            }
			        });
				  $('#sform').bootstrapValidator({
					           container: '#tip',
					           message: 'This value is not valid',
					           feedbackIcons: {
					        	   valid: 'glyphicon glyphicon-ok',
					        	   invalid: 'glyphicon glyphicon-remove',
					        	   validating: 'glyphicon glyphicon-refresh'
					        	   },
			        fields: {
			                isystem: {
			                    validators: {
			                        notEmpty: {
			                            message: '指标体系名称不能为空'
			                        }
			                    }
			                }
			            }
			        });
				  var indicatorCount = 1;
				  function modalSave() {
					  var bootstrapValidator = $('#myform').data('bootstrapValidator');
					  var k = bootstrapValidator.isValid();
					  bootstrapValidator.validate();
					  console.log(k);
					  if (!k) return;
					  $('#myModal').modal('hide');
					  $('#indicatorContent').append(''
							+'<div class="col-lg-3 col-sm-6 panel_list_item" style="margin-top:20px;">'
								+'<div class="panel panel-default">'
								  +'<div class="panel-heading">'+$('#iname').val()+'</div>'
								  +'<div class="panel-body" style="text-align: left;color:#666">'
								    +'<p class="weight">权重：<span>'+$('#iweight').val()+'</span></p>'
								    +'<p class="source">来源：<span>'+$('#isource').val()+'</span></p>'
								  +'</div>'
								+'</div>'
							+'</div>'
						);
				  }
				  function checkisystem() {
					  $('#say').text('');
					  var bootstrapValidator = $('#sform').data('bootstrapValidator');
					  var k = bootstrapValidator.isValid();
					  bootstrapValidator.validate();
					  if (!k) return;
					  $('#myModal').modal({
						  keyboard: true
						});
				  }
				  function submitAll() {
					  var bootstrapValidator = $('#sform').data('bootstrapValidator');
					  var k = bootstrapValidator.isValid();
					  bootstrapValidator.validate();
					  if (!k) return;
					  var isystem = $('#isystem').val();
					  var ilist = [];
					  $(".panel_list_item").each(function(){
						  var temp = {};
						  temp['name'] = $(this).find(".panel-heading").text();
						  temp['weight'] = $(this).find("p.weight span").text();
						  temp['source'] = $(this).find("p.source span").text();
						  ilist.push(temp);
					  });
					  var params = {"indicatorSystemName":isystem,"indicatorList":JSON.stringify(ilist)};
					  $.ajax({
						  async:false,
						  data:params,
						  dataType:'json',
						  type:"POST",
						  url:"<%=path%>/IndiSys_Insert_Result",
						  success: function(data) {
							  if (data.status != '0' && data.status != '-1') {
								  window.location.href="indicatorAddSuccess.html";
							  } else {
								  $('#say').text(data.say);
							  }
						  }
					  });
				  }
				  </script>
				</form>
		      </div>
		    </div>
		  </div>
		</div>
		
		<!--指标内容  -->
		<div class="row" id="indicatorContent">
		
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




</body>
</html>
