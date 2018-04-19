package questionnaire.expert;

import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import questionnaire.QuestionnaireDAO;
import questionnaire.QuestionnaireIntf;

public class ExpertQuestionnaireDAO extends JdbcDaoSupport implements QuestionnaireDAO {
	private Map<String, Answers> weights;

	public Map<String, Answers> getWeights() {
		return weights;
	}
	public void setWeights(Map<String, Answers> weights) {
		this.weights = weights;
	}

	@Override
	public int insertQuestinnaire(QuestionnaireIntf questionnaire) {
		String sql = "insert into expert_questionnaire(longitude, latitude, question, answer) values(?,?,?,?)";
		int sum=0;
		for (int i=0;i<questionnaire.getQuestions().size();i++) {
			double answer=((ExpertQuestionnaire) questionnaire).getAnswers().get(i);
			if (answer==-1) continue;
			sum+=getJdbcTemplate().update(sql, new Object[] {
					questionnaire.getLongitude(), questionnaire.getLatitude(), 
					questionnaire.getQuestions().get(i).getQuestion(), answer});
		}
		return sum;
	}

	@Override
	public double getWeightByLL(double longitude, double latitude) {
		String sql="select question,answer from expert_questionnaire where longitude=? and latitude=?";
		List<Map<String, Object>> list = getJdbcTemplate().queryForList(sql, new Object[]{longitude, latitude});
		if (list.size()==0) return -1;
		for (Map<String, Object> map : list) {
			weights.get(map.get("question")).answers.add((Double)map.get("answer"));
		}
		double sum=0;
		double regular=0;
		for (String key : weights.keySet()) {			
			Answers a=weights.get(key);
			if (a.answers.size()>0){
				double tempSum=0;
				regular+=a.getWeight();
				for (Double answer : a.answers) {
					tempSum+=answer*a.getWeight();
				}
				sum+=tempSum/a.answers.size();
			}
		}
		return sum/regular;
	}

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("questionnaire/applicationContext-SQL.xml");
		ExpertQuestionnaireDAO rq=context.getBean(ExpertQuestionnaireDAO.class);
		rq.getWeightByLL(5.51, 6.6);
		
	}
}
