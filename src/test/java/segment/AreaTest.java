package segment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import segment.Area.BoundaryList;
import segment.Area.Point;
import weighting.EristPoint;

public class AreaTest {
	
	Area area;

	//get bean
	public AreaTest setUp() throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("segment/applicationContext-Segment.xml");
		area=(Area)context.getBean(Area.class);		
		return this;
	}

	public AreaTest testWeightByEPs(List<EristPoint> list, boolean debug) {
		/*ep=new EristPoint(99, 99);
		ep.setFinalWeight(1);
		list.add(ep);*/
		area.weightByEPs(list);
		if (debug){
			for (int i=0;i<area.getxNum();i++){
				for (int j=0;j<area.getyNum();j++){
					System.out.print(area.points[i][j]+" ");
				}
				System.out.println();
			}
		}
		return this;
	}

	public AreaTest testAvgWeights(boolean debug) {
		area.avgWeights();
		if (debug){
			for (int i=0;i<area.getxNum();i++){
				for (int j=0;j<area.getyNum();j++){
					System.out.print(area.points[i][j]+" ");
				}
				System.out.println();
			}
		}
		return this;
	}

	public AreaTest testLevelArea(boolean debug) {
		area.levelArea();
		if (debug){
			File file=new File("E:\\programme\\java\\erist_v2\\1.txt");
			try {
				FileWriter writer=new FileWriter(file);
				for (int i=0;i<area.getxNum();i++){
					for (int j=0;j<area.getyNum();j++){
						writer.write(area.leveledPoints[i][j]?"1":"0");
					}
					writer.write("\n");
				}
				writer.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return this;
	}
	
	public AreaTest testRemoveNegNoise(boolean debug) {
		area.removeNegNoise();
		if (debug){
			for (int i=0;i<area.getxNum();i++){
				for (int j=0;j<area.getyNum();j++){
					System.out.print(area.leveledPoints[i][j]+" ");
				}
				System.out.println();
			}
		}
		return this;
	}
	
	public AreaTest testRemovePosNoise(boolean debug) {
		area.removePosNoise();
		if (debug){
			for (int i=0;i<area.getxNum();i++){
				for (int j=0;j<area.getyNum();j++){
					System.out.print((area.leveledPoints[i][j]==true?1:0)+" ");
				}
				System.out.println();
			}
		}
		return this;
	}

	public AreaTest testFindBoundary(boolean debug) {
		area.findBoundary();
		if (debug){
			for (int i=0;i<area.getxNum();i++){
				for (int j=0;j<area.getyNum();j++){
					System.out.print((area.boundPosMap[i][j]==true?1:0)+" ");
				}
				System.out.println();
			}
		}
		return this;
	}
	
	public AreaTest testBoundaryTrack(int i, int j, int dir, boolean debug) {
		BoundaryList list=area.boundaryPosTrack(i, j, dir);
		if (debug){
			for (Point point : list.list) {
				System.out.println(point.i+" "+point.j);
			}
		}
		return this;
	}
	
	public AreaTest testRemoveBoundPosMapBFS(int i, int j, boolean debug) {
		BoundaryList list=area.boundaryPosTrack(i, j, 7);
		area.removePosBoundary(list.list);
		List<BoundaryList> lists=area.removeBoundPosMapBFS(i, j);
		if (debug){
			for (BoundaryList list1 : lists) {
				System.out.println("list:");
				for (Point point : list1.list) {
					System.out.println(point.i+" "+point.j);
				}
			}
		}
		return this;
	}
	
	public AreaTest testGetBoundarys(boolean debug) {
		area.getBoundarys();
		if (debug){
			for (BoundaryList list1 : area.boundaryLists) {
				System.out.println("list:"+(list1.pos==true?"pos":"neg"));
				for (Point point : list1.list) {
					System.out.println(point.i+" "+point.j);
				}
			}
		}
		return this;
	}

	public AreaTest testSampling(boolean debug){
		area.sampling();
		return this;
	}
	
	public String testToJSON(boolean debug) {
		return area.toJSON();
	}

	//孔
	public String test1(double xStart, double xEnd, double yStart, double yEnd, int zoomLevel) throws Exception {
		AreaTest areaTest=new AreaTest();
		List<EristPoint> list=new LinkedList<>();
		EristPoint ep1=new EristPoint(110.293438, 31.732897);
		ep1.setFinalWeight(1);
		list.add(ep1);
		EristPoint ep2=new EristPoint(110.716576, 31.729949);
		ep2.setFinalWeight(1);
		list.add(ep2);
		EristPoint ep3=new EristPoint(110.717726, 31.484907);
		ep3.setFinalWeight(1);
		list.add(ep3);
		EristPoint ep4=new EristPoint(110.322184, 31.494761);
		ep4.setFinalWeight(1);
		list.add(ep4);
		EristPoint ep5=new EristPoint(110.540652, 31.625714);
		ep5.setFinalWeight(0);
		list.add(ep5);
		areaTest.setUp();
		areaTest.area.setParam(xStart, xEnd, yStart, yEnd, zoomLevel);
		//areaTest.area.setParam(0, 16, 0, 16, 1);
		areaTest.area.init();
		areaTest.testWeightByEPs(list, false)
				.testAvgWeights(false);
		
		/*for (int i=2;i<8;i++) areaTest.area.points[2][i]=0;
		for (int i=2;i<8;i++) areaTest.area.points[3][i]=0;
		areaTest.area.points[4][2]=0;areaTest.area.points[4][3]=0;
		areaTest.area.points[4][4]=1;areaTest.area.points[4][5]=1;
		areaTest.area.points[4][6]=0;areaTest.area.points[4][7]=0;
		areaTest.area.points[5][2]=0;areaTest.area.points[5][3]=0;
		areaTest.area.points[5][4]=1;areaTest.area.points[5][5]=1;
		areaTest.area.points[5][6]=0;areaTest.area.points[5][7]=0;
		for (int i=2;i<8;i++) areaTest.area.points[6][i]=0;
		for (int i=2;i<8;i++) areaTest.area.points[7][i]=0;*/
		
		areaTest.testLevelArea(false)
				.testRemoveNegNoise(false)
				.testRemovePosNoise(false)
				.testFindBoundary(false);
//		areaTest.testBoundaryTrack(0, 0, 7, false)
//				.testRemoveBoundPosMapBFS(0, 0, false)
		return areaTest.testGetBoundarys(false)
				.testSampling(false)
				.testToJSON(true);
	}
	
	//交叉临界点
	public void test2() throws Exception {
		AreaTest areaTest=new AreaTest();
		List<EristPoint> list=new LinkedList<>();
		EristPoint ep=new EristPoint(0, 0);
		ep.setFinalWeight(1);
		list.add(ep);
		ep=new EristPoint(23, 23);
		ep.setFinalWeight(1);
		list.add(ep);
		areaTest.setUp()
				.testWeightByEPs(list, false)
				.testAvgWeights(false);
		areaTest.area.points[11][12]=1;
		areaTest.testLevelArea(true)
				.testFindBoundary(false);
		areaTest.testGetBoundarys(true)
				.testToJSON(false);
	}
	
	//双重孔
	public String test3() throws Exception {
		AreaTest areaTest=new AreaTest();
		List<EristPoint> list=new LinkedList<>();
		EristPoint ep=new EristPoint(0, 0);
		ep.setFinalWeight(1);
		list.add(ep);
		ep=new EristPoint(23, 23);
		ep.setFinalWeight(1);
		list.add(ep);
		areaTest.setUp()
				.testWeightByEPs(list, false)
				.testAvgWeights(false);
		areaTest.area.points[11][12]=1;
		areaTest.area.points[4][4]=0;areaTest.area.points[4][5]=0;
		areaTest.area.points[5][4]=0;areaTest.area.points[5][5]=0;
		areaTest.area.points[18][18]=0;areaTest.area.points[18][19]=0;
		areaTest.area.points[19][18]=0;areaTest.area.points[19][19]=0;
		areaTest.testLevelArea(false)
				.testFindBoundary(false);
		return 
			areaTest.testGetBoundarys(false)
					.testToJSON(true);
	}
	
	public static void main(String[] args) throws Exception {
		AreaTest areaTest=new AreaTest();
		System.out.println(areaTest.test1(0,16,0,16,1));
	//	areaTest.test2();
	//	System.out.println(areaTest.test3());
	}
}
