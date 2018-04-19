package questionnaire.resident;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import questionnaire.Question;
import questionnaire.QuestionnaireIntf;

public class ResidentQuestionnaire extends QuestionnaireIntf{ 	
	@Override
	public void getAnswers(HttpServletRequest request) throws NumberFormatException, IndexOutOfBoundsException{
		answers=new ArrayList<Double>();
		for (Question question : questions) {
			String st=request.getParameter(question.getQuestion());
			int answer=Integer.parseInt(st);
			answers.add(((ResidentQuestion) question).getOptionWeights().get(answer));
		}	
	}

	//just for test
	public void getAnswers(Map<String, Integer> map) {
		answers=new ArrayList<Double>();
		for (Question question : questions) {
			int answer=map.get(question.getQuestion());
			answers.add(((ResidentQuestion) question).getOptionWeights().get(answer));
		}	
	}

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("questionnaire/applicationContext-Question-Resident.xml");
		ResidentQuestionnaire rq=(ResidentQuestionnaire)context.getBean(ResidentQuestionnaire.class);

		Map<String, Integer> map=new HashMap<>();
		map.put("Q1", 0);		map.put("Q2", 0);
		map.put("Q3", 0);		map.put("Q4", 0);
		map.put("Q5", 0);		map.put("Q6", 0);
		map.put("Q7", 0);		map.put("Q8", 0);
		map.put("Q9", 0);		map.put("Q10", 0);
		map.put("Q11", 0);		map.put("Q12", 0);
		map.put("Q13", 0);		map.put("Q14", 0);
		map.put("Q15", 0);		map.put("Q16", 0);
		map.put("Q17", 0);		map.put("Q18", 0);
		map.put("Q19", 0);		map.put("Q20", 0);
		map.put("Q21", 0);		map.put("Q22", 0);
		map.put("Q23", 0);
		
		rq.getAnswers(map);
		System.out.println(rq.solveWeight());
	}
}
