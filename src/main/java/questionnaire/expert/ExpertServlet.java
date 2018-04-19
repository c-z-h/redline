package questionnaire.expert;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import questionnaire.monitor.MonitorQuestionnaire;
import questionnaire.monitor.MonitorQuestionnaireDAO;

/**
 * Servlet implementation class ExpertServlet
 */
@WebServlet("/ExpertServlet")
public class ExpertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExpertServlet() {
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
		ExpertQuestionnaire eq=context.getBean(ExpertQuestionnaire.class);
		try{
			eq.getLL(request);
			eq.getAnswers(request);
			ExpertQuestionnaireDAO eqDAO=context.getBean(ExpertQuestionnaireDAO.class);
			eqDAO.insertQuestinnaire(eq);
		}
		catch(NullPointerException e){
			e.printStackTrace();
			return;
		}
		catch(NumberFormatException e){
			e.printStackTrace();
			return;
		} 
		catch (Exception e) {
			e.printStackTrace();
			return;
		}
		response.getOutputStream().print(eq.solveWeight());
	}

}
