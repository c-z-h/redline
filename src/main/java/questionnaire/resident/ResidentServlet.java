package questionnaire.resident;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * Servlet implementation class Test
 */
//@WebServlet("/Test")
public class ResidentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResidentServlet() {
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
		WebApplicationContext context=ContextLoader.getCurrentWebApplicationContext();
		ResidentQuestionnaire rq=context.getBean(ResidentQuestionnaire.class);
		try{
			rq.getLL(request);
			rq.getAnswers(request);
			ResidentQuestionnaireDAO rqDAO=context.getBean(ResidentQuestionnaireDAO.class);
			rqDAO.insertQuestinnaire(rq);
		}
		catch(NumberFormatException e){
			e.printStackTrace();
			return;
		}
		catch(IndexOutOfBoundsException e) {
			e.printStackTrace();
			return;
		}
		response.getOutputStream().print(rq.solveWeight());
	}

}
