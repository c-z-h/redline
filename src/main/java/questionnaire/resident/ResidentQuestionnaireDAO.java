package questionnaire.resident;

import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import questionnaire.QuestionnaireDAO;
import questionnaire.QuestionnaireIntf;

public class ResidentQuestionnaireDAO extends JdbcDaoSupport implements QuestionnaireDAO{
	@Override
	public int insertQuestinnaire(QuestionnaireIntf questionnaire) {
		String sql = "insert into resident_questionnaire(longitude, latitude, weight) values(?,?,?)";
		return getJdbcTemplate().update(sql, new Object[] {
				questionnaire.getLongitude(), questionnaire.getLatitude(), questionnaire.solveWeight()});
	}

	@Override
	public double getWeightByLL(double longitude, double latitude) {
		String sql="select weight from resident_questionnaire where longitude=? and latitude=?";
		List<Double> list=getJdbcTemplate()
				.queryForList(sql, new Object[]{longitude, latitude}, Double.class);
		if (list.size()==0) return -1;
		double sum=0;
		for (Double double1 : list) {
			sum+=double1;
		}
		return sum/list.size();
	}
	
}
