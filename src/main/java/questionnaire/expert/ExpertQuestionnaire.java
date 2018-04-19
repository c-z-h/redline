package questionnaire.expert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import questionnaire.Question;
import questionnaire.QuestionnaireIntf;
import questionnaire.resident.ResidentQuestionnaire;

public class ExpertQuestionnaire extends QuestionnaireIntf {
	
	public List<Double> getAnswers() {
		return answers;
	}

	@Override
	public void getAnswers(HttpServletRequest request) throws NullPointerException, NumberFormatException, Exception {
		//get major from request
		String major=request.getParameter("major");
		if (major==null) throw new Exception();
		
		answers=new ArrayList<>();
		for (Question question : questions) {
			if (((ExpertQuestion) question).getMajor().equals(major)){
				String st=request.getParameter(question.getQuestion());
				double answer=Double.parseDouble(st);
				double alpha=((ExpertQuestion) question).getAlpha();
				answers.add(answer<alpha?answer/alpha:1);
			}
			else{
				answers.add(-1.0);
			}
		}
	}
	
	//just for test
	public void getAnswers(Map<String, Integer> map) {
		String major="M1";
		
		answers=new ArrayList<>();
		for (Question question : questions) {
			if (((ExpertQuestion) question).getMajor().equals(major)){				
				double answer=map.get(question.getQuestion());
				double alpha=((ExpertQuestion) question).getAlpha();
				answers.add(answer<alpha?answer/alpha:1);
			}
			else{
				answers.add(-1.0);
			}
		}
	}

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("questionnaire/applicationContext-Question-Expert.xml");
		ExpertQuestionnaire rq=context.getBean(ExpertQuestionnaire.class);

		Map<String, Integer> map=new HashMap<>();
		map.put("Q2", 0);
		map.put("Q3", 0);		map.put("Q4", 0);
		map.put("Q5", 0);		map.put("Q6", 0);
		map.put("Q7", 0);		
		
		rq.getAnswers(map);
		System.out.println(rq.solveWeight());
	}
}
