var map = new BMap.Map("container", {enableMapClick:false});          // 创建地图实例  

var opoint = new BMap.Point(110.538352, 31.592255);  // 创建点坐标  
map.centerAndZoom(opoint, 10);                 // 初始化地图，设置中心点坐标和地图级别  

map.enableScrollWheelZoom();
		
//可以向地图添加多个控件。在本例中我们向地图添加一个平移缩放控件。在地图中添加控件后，它们即刻生效。
map.addControl(new BMap.NavigationControl());     

function getBoundary(){ 
	var ctx = this.canvas.getContext("2d");
	if (!ctx) return;
	ctx.clearRect(0, 0, ctx.canvas.width, ctx.canvas.height);            
    
            
    sw=map.getBounds().getSouthWest();  //可视区域西南角的点
    xstart=sw.lng;  //经度起始值
    ystart=sw.lat;  //纬度起始值
    ne=map.getBounds().getNorthEast();  //可视区域东北角的点
    xend=ne.lng;    //经度终止值
    yend=ne.lat;    //纬度终止值
    zoomlevel=map.getZoom();    //缩放等级
    
    
       
    pointPre=new BMap.Point(); 
    pointNxt=new BMap.Point();
    pointDst=new BMap.Point();
    $.post("SegmentServlet",
    {
        "xstart":xstart,
        "xend":xend,
        "ystart":ystart,
        "yend":yend,
        "zoomlevel":zoomlevel
    },
    function(data){
    	//设定填充颜色
        ctx.fillStyle = "rgba(255, 0, 0, 0.5)";
        //开始路径
        ctx.beginPath();
        //将返回字符串解析成JSON对象
    	json=$.parseJSON(data);
        //遍历轮廓
        for (var k=0;k<json.bounds.length;k++){
        	var points=json.bounds[k].points;
            var i,di;
            if(json.bounds[k].pos=='true'){
                //外层轮廓，倒序连接
            	i=points.length+1;
            	di=-1;
            }
            else{
                //孔轮廓，顺序连接
            	i=0;
            	di=1;
            }
            var x,y;
            //为了闭合路径将前两个点复制到末尾
            points[points.length]=points[0];
            points[points.length]=points[1];
            for (var j=0;j<points.length-1;j++){
            	pointPre.lat=parseFloat(points[i].latitude);
            	pointPre.lng=parseFloat(points[i].longitude);
            	if (j==0){                    
                    pointNxt.lat=parseFloat(points[i+di].latitude);
                    pointNxt.lng=parseFloat(points[i+di].longitude);
                    //前两个轮廓点的中点为起始点
                    pointDst.lat=(pointPre.lat+pointNxt.lat)/2;
                    pointDst.lng=(pointPre.lng+pointNxt.lng)/2;
                    pixelDst=map.pointToPixel(pointDst);
            		ctx.moveTo(pixelDst.x, pixelDst.y);
                    pointPre.x=pointNxt.x;
                    pointPre.y=pointNxt.y;
            	}
            	else{
                    pointNxt.lat=parseFloat(points[i+di].latitude);
                    pointNxt.lng=parseFloat(points[i+di].longitude);
                    //取与下一个轮廓点的中点为终点
                    pointDst.lat=(pointPre.lat+pointNxt.lat)/2;
                    pointDst.lng=(pointPre.lng+pointNxt.lng)/2;
                    pixelDst=map.pointToPixel(pointDst);
                    //取当前轮廓点为控制点
                    pixelCtl=map.pointToPixel(pointPre);
            		ctx.quadraticCurveTo(pixelCtl.x, pixelCtl.y, pixelDst.x, pixelDst.y);
                    pointPre.x=pointNxt.x;
                    pointPre.y=pointNxt.y;           		
            	}
            	i+=di;
            }
        }    
        //填充路径
        ctx.fill();
    });  	   

}
var canvasLayer = new BMap.CanvasLayer({
	update: getBoundary
});        
map.addOverlay(canvasLayer);
