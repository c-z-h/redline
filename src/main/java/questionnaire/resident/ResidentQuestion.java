package questionnaire.resident;

import java.util.List;

import questionnaire.Question;

public class ResidentQuestion extends Question{
	private List<Double> optionWeights;
	
	public List<Double> getOptionWeights() {
		return optionWeights;
	}
	public void setOptionWeights(List<Double> optionWeights) {
		this.optionWeights = optionWeights;
	}
	
	
}
