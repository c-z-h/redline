package questionnaire.monitor;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * Servlet implementation class MonitorServlet
 */
//@WebServlet("/MonitorServlet")
public class MonitorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MonitorServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		WebApplicationContext context=ContextLoader.getCurrentWebApplicationContext();
		MonitorQuestionnaire mq=context.getBean(MonitorQuestionnaire.class);
		try{
			mq.getLL(request);
			mq.getAnswers(request);
			MonitorQuestionnaireDAO mqDAO=context.getBean(MonitorQuestionnaireDAO.class);
			mqDAO.insertQuestinnaire(mq);
		}
		catch(NullPointerException e){
			e.printStackTrace();
			return;
		}
		catch(NumberFormatException e){
			e.printStackTrace();
			return;
		}
		response.getOutputStream().print(mq.solveWeight());
	}

}
