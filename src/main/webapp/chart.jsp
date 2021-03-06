<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basepath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<meta name="robots" content="noindex" >
<html lang="zh">
<head>
  <meta charset="UTF-8">
  <meta name="viewport"
        content="user-scalable=no, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, minimal-ui" >
  <title >Compound nodes</title >
<script src="http://cdn.bootcss.com/jquery/1.11.0/jquery.min.js" type="text/javascript"></script>
  <script src="https://cdn.bootcss.com/echarts/3.7.2/echarts.min.js" ></script >
</head >
<body >
<div id="chart" ></div >
<script type="text/javascript" >
  var worldMapContainer = document.getElementById('chart');

  //用于使chart自适应高度和宽度,通过窗体高宽计算容器高宽
  var resizeWorldMapContainer = function () {
    worldMapContainer.style.width = document.documentElement.clientWidth + 'px';
    worldMapContainer.style.height = document.documentElement.clientHeight + 'px';
  };
  //设置容器高宽
  resizeWorldMapContainer();

  // 基于准备好的dom，初始化echarts实例
  var myChart = echarts.init(document.getElementById('chart'));

  myChart.showLoading();


  var getfigure_url = './Graph';
  var wd = window.parent.document.getElementById('ontKey').innerHTML;
  console.log(getfigure_url);
  console.log(wd);
  var ajax_data = $.ajax({
	    url: getfigure_url,
	    data: { 'entity': decodeURI(wd) },
	    type: 'GET',
	    dataType: 'json'
	});
  Promise.all([ajax_data]).then(addData);

  var option = {
    tooltip: {
    },
    animation: false,
    series: [
      {
        tooltip: {
          show: true
        },
        name: 'Grpah',
        type: 'graph',
        layout: 'force',
        edgeLabel: {
          normal: {
            show: false
          }
        },
        edgeSymbol: ['circle', 'arrow'],
        edgeSymbolSize: [1, 10],
        categories: [{ name: "0" }, { name: "1" }],
        data: [],
        links: [],
        roam: true,
        label: {
          normal: {
            show: true,
            position: 'right'
          }
        },
        force: {
          repulsion: 1000
        },
        lineStyle: {
          normal: {
            opacity: 0.9,
            width: 1,
            curveness: 0
          }
        }
      }
    ]
  };

  function addData(data) {
	    myChart.hideLoading();
	    // 指定图表的配置项和数据
	    var edges = data[0].elements.edges;
	    var nodes = data[0].elements.nodes;

	    console.log(data[0].elements);

	    // 清空选择
	    for (var i = 0; i < option.series[0].data.length; i++) {
	      old_node = option.series[0].data[i];
	      old_node.category = 0;
	    }

	    nodes.forEach(function (node) {
	      // 合并nodes
	      var found = false;
	      for (var i = 0; i < option.series[0].data.length; i++) {
	        old_node = option.series[0].data[i];
	        if (old_node.id == node.id) {
	          found = true;
	          if (node.category == 1) {
	            old_node.category = 1;
	          }
	          break;
	        }
	      }
	      if (!found) {
	        node.itemStyle = null;
	        node.symbolSize = 30;
	        node.value = node.name;
	        console.log(node.shortname);
	        //node.category = node.attributes.modularity_class;
	        // Use random x, y
	        node.tooltip = {
	            formatter: function (params1) {
	                return node.name
	            }
	        }
	        node.x = node.y = null;
	        node.draggable = false;
	        option.series[0].data.push(node);
	      }
	    });

	    edges.forEach(function (edge) {
	      // 合并edges
	      var found = false;
	      for (var i = 0; i < option.series[0].links.length; i++) {
	        old_edge = option.series[0].links[i];
	        if (old_edge.source == edge.source && old_edge.target == edge.target) {
	          found = true;
	          break;
	        }
	      }

	      if (!found) {
	        // 双向edges
	        found = false;
	        for (var i = 0; i < option.series[0].links.length; i++) {
	          old_edge = option.series[0].links[i];
	          if (old_edge.source == edge.target && old_edge.target == edge.source) {
	            found = true;
	            break;
	          }
	        }

	        console.log(edge.attribute);
	        edge.label = {
	          normal: {
	            show: true,
	            formatter:""
	          }
	        };
	        
	    

	        if (found) {
	          edge.lineStyle = {
	            normal: {
	              width: 1,
	              curveness: 0.4
	            }
	          };
	        }

	        option.series[0].links.push(edge);
	      }
	    });

	    
	    // 事件
	    myChart.on('click', function (params) {
	      if (params.componentType === 'markPoint') {
	        // 点击到了 markPoint 上
	        if (params.seriesIndex === 5) {
	          // 点击到了 index 为 5 的 series 的 markPoint 上。
	        }
	      }
	      else if (params.componentType === 'series') {
	        if (params.seriesType === 'graph') {
	          if (params.dataType === 'edge') {
	            // 点击到了 graph 的 edge（边）上。
	          }
	          else {
	            // 点击到了 graph 的 node（节点）上。
	            myChart.showLoading();
	            var ajax_data = $.ajax({
	              url: getfigure_url,
	              data: { 'entity': decodeURI(params.data["name"]) },
	              type: 'GET',
	              dataType: 'json'
	            });
	            Promise.all([ajax_data]).then(addData);
	          }
	        }
	      }
	    });
	    
	    // 使用刚指定的配置项和数据显示图表。
	    myChart.setOption(option);
	  }


  //用于使chart自适应高度和宽度
  window.onresize = function () {
    //重置容器高宽
    resizeWorldMapContainer();
    myChart.resize();
  };
</script >
</body >
</html >