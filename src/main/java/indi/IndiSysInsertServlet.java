package indi;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class IndiSysInsertServlet
 */
@WebServlet("/IndiSys_Insert_Result")
public class IndiSysInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndiSysInsertServlet() {
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
		IndiSysQueryDAO isDao=context.getBean(IndiSysQueryDAO.class);
		IndiQueryDAO iDao=context.getBean(IndiQueryDAO.class);
		String indSys=request.getParameter("indicatorSystemName");
		String indList=request.getParameter("indicatorList");
		System.out.println(indSys);
		System.out.println(indList);
		JSONObject object=new JSONObject();
		if (indSys!=null && !"".equals(indSys.trim())){
			int i=isDao.insertSys(indSys);
			object.put("status", i);
			System.out.println(i);
			switch(i){
			case 0:
				object.put("say", "体系名称重复");
				break;
			case -1:
				object.put("say", "服务器错误");
				break;
			default:
				try{
					List<Indi> indis=JSONArray.toList(JSONArray.fromObject(indList), Indi.class);
					for (Indi indi : indis) {
						indi.setSys(i);
					}
					int sum=iDao.insertIndis(indis);
					object.put("say","插入成功"+sum+"个指标");
				}
				catch (Exception e) {
					object.put("say", "服务器错误");
				}
			}
		}
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(object.toString());
	}

}
