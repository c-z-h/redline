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
    
    
       
    point0=new BMap.Point(); 
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
        	points=json.bounds[k].points;
            var i,di;
            if(json.bounds[k].pos=='true'){
                //外层轮廓，倒序连接
            	i=points.length-1;
            	di=-1;
            }
            else{
                //孔轮廓，顺序连接
            	i=0;
            	di=1;
            }
            var x,y;
            points[points.length]=points[0];
            for (var j=0;j<points.length;j++){
            	point.lat=parseFloat(points[i].latitude);
            	point.lng=parseFloat(points[i].longitude);
                //将经纬度转换成屏幕位置
            	pixel0=map.pointToPixel(point);
            	if (j==0){
                    point.lat=parseFloat(points[i+di].latitude);
                    point.lng=parseFloat(points[i+di].longitude);
                    pixel1=map.pointToPixel(point);
                    x=(pixel0.x+pixel1.x)/2;
                    y=(pixel0.y+pixel1.y)/2;
            		ctx.moveTo(x, y);
                    pixel0.x=pixel1.x;
                    pixel0.y=pixel1.y;
            	}
            	else{
                    point.lat=parseFloat(points[i+di].latitude);
                    point.lng=parseFloat(points[i+di].longitude);
                    pixel1=map.pointToPixel(point);
                    x=(pixel0.x+pixel1.x)/2;
                    y=(pixel0.y+pixel1.y)/2;
            		ctx.quadraticCurveTo(pixel0.x, pixel0.y, x, y);
                    pixel0.x=pixel1.x;
                    pixel0.y=pixel1.y;           		
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
