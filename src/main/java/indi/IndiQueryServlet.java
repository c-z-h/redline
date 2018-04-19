package indi;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import lite.LiteQueryDAO;

/**
 * Servlet implementation class IndiQueryServlet
 */
@WebServlet("/Indicator_Query_Result")
public class IndiQueryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndiQueryServlet() {
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
		IndiQueryDAO iDao=context.getBean(IndiQueryDAO.class);

		String query=request.getParameter("query");
		String content=request.getParameter("content");
		
		List<Indi> list=null;
		
		if (query!=null && content!=null && content!="")
			switch (query) {
			case "title":
				list=iDao.queryByID(content);
				break;
			case "word":
				list=iDao.queryByWord(content);
				break;
			default:
				break;
			}
		
		request.setAttribute("IndiList", list);
		request.getRequestDispatcher("Indicator_Query_Result.jsp").forward(request, response);
		
	}

}
