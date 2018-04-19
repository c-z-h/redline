package segment;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import weighting.WeightingDAO;

/**
 * Servlet implementation class SegmentServlet
 */
@WebServlet("/SegmentServlet")
public class SegmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SegmentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		WebApplicationContext context=ContextLoader.getCurrentWebApplicationContext();
		WeightingDAO wDao=context.getBean(WeightingDAO.class);
		Area area=(Area)context.getBean(Area.class);
		
		String json=null;
		try{
			double xStart=Double.parseDouble(request.getParameter("xstart"));
			double xEnd=Double.parseDouble(request.getParameter("xend"));
			double yStart=Double.parseDouble(request.getParameter("ystart"));
			double yEnd=Double.parseDouble(request.getParameter("yend"));
			int zoomLevel=Integer.parseInt(request.getParameter("zoomlevel"));
			area.setParam(xStart, xEnd, yStart, yEnd, zoomLevel-3);//百度地图最小的zoomlevel为3
			
			AreaTest areaTest=new AreaTest();
			json=areaTest.test1(xStart, xEnd, yStart, yEnd, zoomLevel-3);
		}
		catch(Exception e){		
			e.printStackTrace();
		}
		//json=
		//	area.init()
		//		.segment(wDao.getAllPoints())
		//		.toJSON();	
		
		response.getOutputStream().print(json);
	}

}
