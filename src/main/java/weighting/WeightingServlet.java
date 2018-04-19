package weighting;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import questionnaire.expert.ExpertQuestionnaireDAO;
import questionnaire.monitor.MonitorQuestionnaireDAO;
import questionnaire.resident.ResidentQuestionnaire;
import questionnaire.resident.ResidentQuestionnaireDAO;

/**
 * Servlet implementation class WeightingServlet
 */
@WebServlet("/WeightingServlet")
public class WeightingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WeightingServlet() {
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
		EristPoints ePoints=context.getBean(EristPoints.class);
		WeightingDAO wDao=context.getBean(WeightingDAO.class);
		ePoints.setEristPoints(wDao.getAllPoints());
		ExpertQuestionnaireDAO eqDAO=context.getBean(ExpertQuestionnaireDAO.class);
		ePoints.getExpertWeights(eqDAO);
		MonitorQuestionnaireDAO mqDAO=context.getBean(MonitorQuestionnaireDAO.class);
		ePoints.getMonitorWeights(mqDAO);
		ResidentQuestionnaireDAO rqDAO=context.getBean(ResidentQuestionnaireDAO.class);
		ePoints.getResidentWeights(rqDAO);
		ePoints.solveWeights()
				.getLevels();
		response.getOutputStream().print(ePoints.toJsonByLevel(0));
	}

}
