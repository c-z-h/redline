package questionnaire.monitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import questionnaire.Question;
import questionnaire.QuestionnaireIntf;

public class MonitorQuestionnaire extends QuestionnaireIntf {

	@Override
	public void getAnswers(HttpServletRequest request) throws NullPointerException, NumberFormatException {
		answers=new ArrayList<Double>();
		for (Question question : questions) {
			String st=request.getParameter(question.getQuestion());
			double answer=Double.parseDouble(st);
			double alpha=((MonitorQuestion) question).getAlpha();
			answers.add(answer<alpha?answer/alpha:1);
		}	
	}
	
	//just for test
	public void getAnswers(Map<String, Double> map) {
		answers=new ArrayList<>();
		for (Question question : questions) {
			double answer=map.get(question.getQuestion());
			answers.add(answer);
		}
	}

	public static void main(String[] args) {
		ApplicationContext context=new ClassPathXmlApplicationContext("questionnaire/applicationContext-Question-Monitor.xml");
		MonitorQuestionnaire mq=context.getBean(MonitorQuestionnaire.class);
		
		Map<String, Double> map=new HashMap<>();
		map.put("Q3", 1.0);		map.put("Q4", 1.0);
		map.put("Q5", 1.0);		map.put("Q6", 1.0);
		map.put("Q7", 1.0);		map.put("Q8", 1.0);
		map.put("Q9", 1.0);		map.put("Q10", 1.0);
		map.put("Q11", 1.0);	map.put("Q12", 1.0);
		map.put("Q13", 1.0);	map.put("Q14", 1.0);
		map.put("Q15", 1.0);	map.put("Q16", 1.0);
		map.put("Q17", 1.0);	map.put("Q18", 1.0);
		
		mq.getAnswers(map);
		System.out.println(mq.solveWeight());
	}

}
