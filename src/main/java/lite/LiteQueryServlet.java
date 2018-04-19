package lite;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import segment.Area;
import segment.AreaTest;
import weighting.WeightingDAO;

/**
 * Servlet implementation class liteQueryServlet
 */
@WebServlet("/Lite_Query_Result")
public class LiteQueryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LiteQueryServlet() {
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
		LiteQueryDAO lDao=context.getBean(LiteQueryDAO.class);
		
		List<Lite> list=null;
		
		String query=request.getParameter("query");
		String content=request.getParameter("content");
		if (query!=null && content!=null && content!="")
			switch (query) {
			case "title":
				list=lDao.queryByID(content);
				break;
			case "book":
				list=lDao.queryByBook(content);
				break;
			case "year":
				try{
					list=lDao.queryByYear(content);
				}catch(Exception e){}
				break;
			case "author":
				list=lDao.queryByAuthor(content);
				break;
			case "kwd":
				list=lDao.queryByKwd(content);
				break;
			default:
				break;
			}
		
		request.setAttribute("Litelist", list);
		request.getRequestDispatcher("Lite_Query_Result.jsp").forward(request, response);
	}

}
