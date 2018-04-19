package segment;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

import weighting.EristPoint;

public class Area {	
	class Point{
		public Point(int i, int j) {
			this.i=i;
			this.j=j;
		}
		int i,j;
		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof Point)) return false;
			if (((Point)obj).i==i && ((Point)obj).j==j) return true;
			return false;
		}
		public double getLongitude() {
			return i*unit+xStart;
		}
		public double getLatitude() {
			return j*unit+yStart;
		}
	}
	
	class BoundaryList{
		List<Point> list;
		boolean pos;
		public BoundaryList(List<Point> list, boolean pos) {
			this.list=list;
			this.pos=pos;
		}
	}
	
	private double xStart,yStart; //经纬度起始坐标   x:纬度 y:经度
	private int xNum,yNum; //经纬度划分点数
	private double ounit; //缩放级别最大时的每个点数的间距
	private double unit = 1; //当前请求的间隔
	
	private double xLevel; //水平段折点的距离
	private double k; //曲线段距离的指数
	private double xZero; //曲线段零点段的截止距离
	
	private double weightThreshold; //权重阈值
//	private double pointThreshold; //噪点阈值
	private int minNegArea;	//最小非红线区栅格数，用于去噪
	private int minPosArea;	//同上
	private int samplingHops; //最终采样跃点数
	
	int[] dx={ 0,-1,-1,-1, 0, 1, 1, 1}; //8邻域 从下方开始 顺时针排列
	int[] dy={ 1, 1, 0,-1,-1,-1, 0, 1};
	int[] bfsDx={-1, 0, 0, 1}; //4邻域
	int[] bfsDy={ 0,-1, 1, 0};
//	int[] direction={128,64,32,1,16,2,4,8};
	double[][] points;  //二维数组表示地图样方的实际权值
	int[][] bases;	//记录实际权值计算的过程中，每个样方的被引用次数，然后可以计算平均值
	boolean[][] leveledPoints; //根据阈值二值化后的样方
	boolean[][] reversedPoints; //二值化反相的样方
	boolean[][] backupLeveledPoints; //二值化样方的备份
	boolean[][] bfsMap; //用于dfs的标记图
	boolean[][] boundPosMap; //红线区的边界点
	boolean[][] boundNegMap; //非红线区的边界点
	
	List<BoundaryList> boundaryLists;
	
	public double getxStart() {
		return xStart;
	}
	public void setxStart(double xStart) {
		this.xStart=xStart;
	}
	public double getyStart() {
		return yStart;
	}
	public void setyStart(double yStart) {
		this.yStart = yStart;
	}
	public int getxNum() {
		return xNum;
	}
	public void setxNum(int xNum) {
		this.xNum = xNum;
	}
	public int getyNum() {
		return yNum;
	}
	public void setyNum(int yNum) {
		this.yNum = yNum;
	}
	public double getOunit() {
		return ounit;
	}
	public void setOunit(double ounit) {
		this.ounit = ounit;
	}
	public double getxLevel() {
		return xLevel;
	}
	public void setxLevel(double xLevel) {
		this.xLevel = xLevel;
	}
	public double getK() {
		return k;
	}
	public void setK(double k) {
		this.k = k;
	}
	public double getxZero() {
		return xZero;
	}
	public void setxZero(double xZero) {
		this.xZero = xZero;
	}	
	public double getWeightThreshold() {
		return weightThreshold;
	}
	public void setWeightThreshold(double weightThreshold) {
		this.weightThreshold = weightThreshold;
	}	
	public int getSamplingHops() {
		return samplingHops;
	}
	public void setSamplingHops(int samplingHops) {
		this.samplingHops = samplingHops;
	}
	public int getMinNegArea() {
		return minNegArea;
	}
	public void setMinNegArea(int minNegArea) {
		this.minNegArea = minNegArea;
	}
	public int getMinPosArea() {
		return minPosArea;
	}
	public void setMinPosArea(int minPosArea) {
		this.minPosArea = minPosArea;
	}
	
	private double dist2(int i, int j, int epi, int epj) {
		//转成double防止溢出
		return (((double)j-epj)*(j-epj)+((double)i-epi)*(i-epi))*unit*unit;
	}
	
	private double dist(int i, int j, int epi, int epj) {
		return Math.sqrt(dist2(i, j, epi, epj));
	}
	
	//init
	public Area init() {
		points = new double[xNum][yNum];
		bases = new int[xNum][yNum];
		leveledPoints = new boolean[xNum][yNum];
		reversedPoints = new boolean[xNum][yNum];
		bfsMap = new boolean[xNum][yNum];
		boundPosMap = new boolean[xNum][yNum];
		boundNegMap = new boolean[xNum][yNum];
//		boundCount = new byte[xNum][yNum];
		return this;
	}
	
	public Area setParam(double xStart, double xEnd, double yStart, double yEnd, int zoomLevel){
		this.xStart=xStart;
		this.yStart=yStart;
		this.unit=ounit / Math.pow(2.0f, zoomLevel);
		this.xNum=(int)(Math.ceil((xEnd-xStart)/unit))+1;
		this.yNum=(int)(Math.ceil((yEnd-yStart)/unit))+1;
		return this;
	}
	
	//将问卷的点分布到离散化地图上
	public Area weightByEPs(List<EristPoint> eps) {
		//直接加到该样方的权值中，并增加该样方的引用次数
		//该函数结束后调用avgWeights进行平均
		for (EristPoint ep : eps) {
			double epx=ep.getLongitude();
			double epy=ep.getLatitude();
			int epi=(int) Math.floor((epx-xStart)/unit);
			int epj=(int) Math.floor((epy-yStart)/unit);
			
			int i;
			//往下
			for (i=Math.max(0, epi); i<xNum && (i-epi)*unit<=xZero; i++){
				int j;
				//往右水平段
				for (j=Math.max(0, epj); j<yNum && dist2(i, j, epi, epj)<=xLevel*xLevel; j++){						
					points[i][j]+=ep.getFinalWeight();
					bases[i][j]++;
				}
				//往右曲线段
				for (;j<yNum && dist2(i, j, epi, epj)<=xZero*xZero; j++){
					points[i][j]+=ep.getFinalWeight()*xLevel*xLevel/dist2(i, j, epi, epj);
					bases[i][j]++;
				}
				//往左水平段
				for (j=Math.min(yNum-1, epj-1); j>=0 && dist2(i, j, epi, epj)<=xLevel*xLevel; j--){
					points[i][j]+=ep.getFinalWeight();
					bases[i][j]++;
				}
				//往左曲线段
				for (;j>=0 && dist2(i, j, epi, epj)<=xZero*xZero; j--){
					points[i][j]+=ep.getFinalWeight()*xLevel*xLevel/dist2(i, j, epi, epj);
					bases[i][j]++;
				}
			}
			//往上
			for (i=Math.min(xNum-1, epi-1); i>=0 && (epi-i)*unit<=xZero; i--){
				int j;
				//往右水平段
				for (j=Math.max(0, epj); j<yNum && dist2(i, j, epi, epj)<=xLevel*xLevel; j++){
					points[i][j]+=ep.getFinalWeight();
					bases[i][j]++;
				}
				//往右曲线段
				for (;j<yNum && dist2(i, j, epi, epj)<=xZero*xZero; j++){
					points[i][j]+=ep.getFinalWeight()*xLevel*xLevel/dist2(i, j, epi, epj);
					bases[i][j]++;
				}
				//往左水平段
				for (j=Math.min(yNum-1, epj-1); j>=0 && dist2(i, j, epi, epj)<=xLevel*xLevel; j--){
					points[i][j]+=ep.getFinalWeight();
					bases[i][j]++;
				}
				//往左曲线段
				for (;j>=0 && dist2(i, j, epi, epj)<=xZero*xZero; j--){
					points[i][j]+=ep.getFinalWeight()*xLevel*xLevel/dist2(i, j, epi, epj);
					bases[i][j]++;
				}
			}			
		}
		return this;
	}
	
	//对离散地图的权值进行平均
	public Area avgWeights() {
		for (int i=0;i<xNum;i++)
			for (int j=0;j<yNum;j++)
				points[i][j]/=bases[i][j];
		return this;
	}
	
	//根据权重阈值进行二值化
	public Area levelArea() {
		for (int i=0;i<xNum;i++)
			for (int j=0;j<yNum;j++){
				if (points[i][j]>=weightThreshold)
					leveledPoints[i][j]=true;
			}
		for (int i=0;i<xNum;i++)
			for (int j=0;j<yNum;j++)
				reversedPoints[i][j]=!leveledPoints[i][j];		
		return this;
	}
	
	private int areaBFS(int i, int j, List<Point> list, boolean flag) {
		//在leveledPoints中，从[i][j]的位置开始bfs，搜索值为flag的样方，并添加到list中，返回本次搜索到的样方数
		List<Point> bfs=new LinkedList<>();
		bfs.add(new Point(i, j));
		int count=0;
		while (bfs.size()>0){
			Point head=bfs.remove(0);
			int i1=head.i;
			int j1=head.j;
			if (bfsMap[i1][j1]) continue;
			bfsMap[i1][j1]=true;
			count++;
			list.add(new Point(i1, j1));
			int ti,tj;
			for (int k=0;k<4;k++){
				ti=i1+bfsDx[k];
				tj=j1+bfsDy[k];
				if (ti>=0 && ti<xNum && tj>=0 && tj<yNum){
					if ((leveledPoints[ti][tj]==flag) && !bfsMap[ti][tj])
						bfs.add(new Point(ti, tj));
				}
			}
		}		
		return count;
	}
	
	//去除面积过小的[非]红线区噪点
	public Area removeNegNoise() {
		for (int i=0;i<xNum;i++)
			for (int j=0;j<yNum;j++)
				bfsMap[i][j]=false;
		for (int i=0;i<xNum;i++)
			for (int j=0;j<yNum;j++)
				if (!leveledPoints[i][j] && !bfsMap[i][j]){
					List<Point> list=new LinkedList<>();
					if (areaBFS(i, j, list, false) < minNegArea)
						for (Point point : list) {
							leveledPoints[point.i][point.j]=true;	
							reversedPoints[point.i][point.j]=false;	
						}
				}
		return this;
	}
	
	//去除面积过小的红线区噪点
	public Area removePosNoise() {
		for (int i=0;i<xNum;i++)
			for (int j=0;j<yNum;j++)
				bfsMap[i][j]=false;
		for (int i=0;i<xNum;i++)
			for (int j=0;j<yNum;j++)
				if (leveledPoints[i][j] && !bfsMap[i][j]){
					List<Point> list=new LinkedList<>();
					if (areaBFS(i, j, list, true) < minPosArea)
						for (Point point : list) {
							leveledPoints[point.i][point.j]=false;
							reversedPoints[point.i][point.j]=false;	
						}
				}
		return this;
	}
	
	//判断4-邻接点不是满的，下同
	private boolean updatePosMap(int i, int j) {
		for (int k=0;k<4;k++){
			int ti=i+bfsDx[k];
			int tj=j+bfsDy[k];
			if (ti>=0 && ti<xNum && tj>=0 && tj<yNum){
				if (!leveledPoints[ti][tj])	{
					boundPosMap[i][j]=true;
					break;
				}
			}
			else {
				boundPosMap[i][j]=true;
				break;
			}
		}
		return boundPosMap[i][j];
	}
	
	private boolean updateNegMap(int i, int j) {
		for (int k=0;k<4;k++){
			int ti=i+bfsDx[k];
			int tj=j+bfsDy[k];
			if (ti>=0 && ti<xNum && tj>=0 && tj<yNum){
				if (leveledPoints[ti][tj])	{
					boundNegMap[i][j]=true;
					break;
				}
			}
			else {
				boundNegMap[i][j]=true;
				break;
			}
		}
		return boundNegMap[i][j];
	}
	
	//寻找边界点，包括红线区与非红线区的
	public Area findBoundary() {		
		for (int i=0;i<xNum;i++)
			for (int j=0;j<yNum;j++)
				if (leveledPoints[i][j]) updatePosMap(i, j);	//红线区
				else updateNegMap(i, j);						//非红线区
		return this;
	}	
	
	//边缘跟踪算法，下同
	protected BoundaryList boundaryPosTrack(int i, int j, int dir) {
		List<Point> list=new LinkedList<>();
		list.add(new Point(i, j));
		while (true){
			if (dir%2==0) dir=(dir+7)%8;
			else dir=(dir+6)%8;
			while (true){
				int ti=i+dx[dir];
				int tj=j+dy[dir];
				if (ti>=0 && ti<xNum && tj>=0 && tj<yNum){
					if (leveledPoints[ti][tj])	{
						list.add(new Point(ti, tj));
						i=ti;
						j=tj;
						break;
					}
				}
				dir=(dir+1)%8;
			}
			int size=list.size();
			if (size>=4)
				if (list.get(0).equals(list.get(size-2)) && list.get(1).equals(list.get(size-1)))
					break;
		}
		list.remove(list.size()-1);
		list.remove(list.size()-1);
		return new BoundaryList(list, true);
	}
	
	protected BoundaryList boundaryNegTrack(int i, int j, int dir) {
		List<Point> list=new LinkedList<>();
		list.add(new Point(i, j));
		while (true){
			if (dir%2==0) dir=(dir+7)%8;
			else dir=(dir+6)%8;
			while (true){
				int ti=i+dx[dir];
				int tj=j+dy[dir];
				if (ti>=0 && ti<xNum && tj>=0 && tj<yNum){
					if (reversedPoints[ti][tj])	{
						list.add(new Point(ti, tj));
						i=ti;
						j=tj;
						break;
					}
				}
				dir=(dir+1)%8;
			}
			int size=list.size();
			if (size>=4)
				if (list.get(0).equals(list.get(size-2)) && list.get(1).equals(list.get(size-1)))
					break;
		}
		list.remove(list.size()-1);
		list.remove(list.size()-1);
		return new BoundaryList(list, false);
	}
	
	//红线区处理的非红线区镜像
	private void removeNegBoundary(List<Point> list) {
		for (Point point : list) {
			boundNegMap[point.i][point.j]=false;
		}
	}
	
	protected List<BoundaryList> removeBoundNegMapBFS(int i, int j) {
		List<BoundaryList> lists=new LinkedList<>();
		List<Point> bfs=new LinkedList<>();
		bfs.add(new Point(i, j));
		while (bfs.size()>0){
			Point head=bfs.remove(0);
			int i1=head.i;
			int j1=head.j;
			if (!reversedPoints[i1][j1]) continue;
			reversedPoints[i1][j1]=false;
			//孔边界
			if (boundNegMap[i1][j1]){
				for (int k=0;k<8;k++){
					int ti=i1+dx[k];
					int tj=j1+dy[k];
					if (ti>=0 && ti<xNum && tj>=0 && tj<yNum)
						if (boundPosMap[ti][tj])
							lists.addAll(getPosBoundarys(ti, tj));
				}
			}
			
			int ti,tj;
			for (int k=0;k<8;k++){
				ti=i1+dx[k];
				tj=j1+dy[k];
				if (ti>=0 && ti<xNum && tj>=0 && tj<yNum)
					if (reversedPoints[ti][tj])
						bfs.add(new Point(ti, tj));				
			}
		}	
		return lists;
	}
	
	private List<BoundaryList> getNegBoundarys(int i, int j) {
		List<BoundaryList> lists=new LinkedList<>();
		int dir = 7;
		for (int k=0;k<8;k++){
			int i1=i+dx[k];
			int i2=i+dx[(k+1)%8];
			int j1=j+dy[k];
			int j2=j+dy[(k+1)%8];
			if (i2>=0 && i2<xNum && j2>=0 && j2<yNum)
				if (reversedPoints[i2][j2])
					if (i1>=0 && i1<xNum && j1>=0 && j1<yNum){
						if (!reversedPoints[i1][j1]) {
							dir=k+2;
							break;
						}
					}
					else {
						dir=k+2;
						break;
					}
		}
		BoundaryList list=boundaryNegTrack(i, j, dir);
		lists.add(list);
		removeNegBoundary(list.list);
		lists.addAll(removeBoundNegMapBFS(i, j));
		return lists;
	}
	
	//bfs取消红线区二值图标记，并处理孔
	protected List<BoundaryList> removeBoundPosMapBFS(int i, int j) {
		List<BoundaryList> lists=new LinkedList<>();
		List<Point> bfs=new LinkedList<>();
		bfs.add(new Point(i, j));
		while (bfs.size()>0){
			Point head=bfs.remove(0);
			int i1=head.i;
			int j1=head.j;
			if (!leveledPoints[i1][j1]) continue;
			leveledPoints[i1][j1]=false;
			if (boundPosMap[i1][j1]){
				//8邻域搜索非红线区边界
				for (int k=0;k<8;k++){
					int ti=i1+dx[k];
					int tj=j1+dy[k];
					if (ti>=0 && ti<xNum && tj>=0 && tj<yNum)
						if (boundNegMap[ti][tj])
							//发现孔，交给非红线区的函数处理
							lists.addAll(getNegBoundarys(ti, tj));
				}
			}
			
			int ti,tj;
			for (int k=0;k<8;k++){
				ti=i1+dx[k];
				tj=j1+dy[k];
				if (ti>=0 && ti<xNum && tj>=0 && tj<yNum)
					if (leveledPoints[ti][tj]){
						bfs.add(new Point(ti, tj));	
					}
			}
		}	
		return lists;
	}
	
	//根据list取消红线区边界boundPosMap的标记
	protected void removePosBoundary(List<Point> list) {
		for (Point point : list) {
			boundPosMap[point.i][point.j]=false;
		}
	}
	
	private List<BoundaryList> getPosBoundarys(int i, int j) {
		List<BoundaryList> lists=new LinkedList<>();
		//搜索边缘跟踪的起始dir
		int dir = 7;
		for (int k=0;k<8;k++){
			int i1=i+dx[k];
			int i2=i+dx[(k+1)%8];
			int j1=j+dy[k];
			int j2=j+dy[(k+1)%8];
			if (i2>=0 && i2<xNum && j2>=0 && j2<yNum)
				if (leveledPoints[i2][j2])
					if (i1>=0 && i1<xNum && j1>=0 && j1<yNum){
						if (!leveledPoints[i1][j1]) {
							dir=k+2;
							break;
						}
					}
					else {
						dir=k+2;
						break;
					}
		}
		//边缘跟踪
		BoundaryList list=boundaryPosTrack(i, j, dir);
		lists.add(list);
		//取消边缘标记
		removePosBoundary(list.list);
		//bfs取消二值图标记（并处理孔
		lists.addAll(removeBoundPosMapBFS(i, j));
		return lists;
	}
		
	//从左上开始搜索红线区边缘
	public Area getBoundarys() {
		boundaryLists=new LinkedList<>();
		for (int i=0;i<xNum;i++)
			for (int j=0;j<yNum;j++){
				if (leveledPoints[i][j]){
					List<BoundaryList> list=getPosBoundarys(i, j);
					boundaryLists.addAll(list);
				}
			}
		return this;
	}
	
	//对点列进行采样
	public Area sampling() {
		for (BoundaryList bList : boundaryLists) {
			List<Point> list=bList.list;
			for (int i=0; i<list.size(); i++){
				for (int j=1; j<samplingHops && i<list.size(); j++)
					list.remove(i);
			}
		}
		return this;
	}
	
	//分割，实际业务函数
	public Area segment(List<EristPoint> eps){
		weightByEPs(eps);
		avgWeights();
		levelArea();
		removeNegNoise();
		removePosNoise();
		findBoundary();
		getBoundarys();
		sampling();
		return this;
	}
	
	//json
	public String toJSON() {
		JSONObject jObject=new JSONObject();	
		JSONArray bounds=new JSONArray();
		for (BoundaryList bList : boundaryLists) {
			JSONArray bound=new JSONArray();
//			Map<String, String> boundMap=new HashMap<>();
			JSONObject boundObject=new JSONObject();
			for (Point point : bList.list) {
				Map<String, String> pointMap=new HashMap<>();
				pointMap.put("longitude", point.getLongitude()+"");
				pointMap.put("latitude", point.getLatitude()+"");
				bound.put(pointMap);
			}
//			boundMap.put("pos", bList.pos+"");
//			boundMap.put("points", bound.toString());
			boundObject.put("pos", bList.pos+"");
			boundObject.put("points", bound);
			bounds.put(boundObject);			
		}
		jObject.put("bounds", bounds);
		return jObject.toString();
	}
}
