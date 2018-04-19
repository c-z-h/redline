package questionnaire.expert;

import questionnaire.Question;

public class ExpertQuestion extends Question{
	private double alpha;
	private String major;

	public double getAlpha() {
		return alpha;
	}
	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	public String getMajor() {
		return major;
	} 
	public void setMajor(String major) {
		this.major = major;
	}
}
