package questionnaire;

public interface QuestionnaireDAO {
	public int insertQuestinnaire(QuestionnaireIntf questionnaire);
	public double getWeightByLL(double longitude,double latitude);
}
