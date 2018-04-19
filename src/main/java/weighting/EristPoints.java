package weighting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import questionnaire.expert.ExpertQuestionnaireDAO;
import questionnaire.monitor.MonitorQuestionnaireDAO;
import questionnaire.resident.ResidentQuestionnaireDAO;

public class EristPoints {
	private List<EristPoint> eristPoints;
	private List<Double> questionnaireWeights;
	private List<Double> levelWeights;
	
	public List<Double> getQuestionnaireWeights() {
		return questionnaireWeights;
	}
	public void setQuestionnaireWeights(List<Double> questionnaireWeights) {
		this.questionnaireWeights = questionnaireWeights;
	}
	public void setEristPoints(List<EristPoint> eristPoints) {
		this.eristPoints = eristPoints;
	}	
	public List<Double> getLevelWeights() {
		return levelWeights;
	}
	public void setLevelWeights(List<Double> levelWeights) {
		this.levelWeights = levelWeights;
	}
	
	public EristPoints getExpertWeights(ExpertQuestionnaireDAO dao) {
		for (EristPoint eristPoint : eristPoints) {
			eristPoint.setExpertWeight(dao.getWeightByLL(eristPoint.getLongitude(), eristPoint.getLatitude()));
		}
		return this;
	}
	public EristPoints getMonitorWeights(MonitorQuestionnaireDAO dao) {
		for (EristPoint eristPoint : eristPoints) {
			eristPoint.setMonitorWeight(dao.getWeightByLL(eristPoint.getLongitude(), eristPoint.getLatitude()));
		}
		return this;
	}
	public EristPoints getResidentWeights(ResidentQuestionnaireDAO dao) {
		for (EristPoint eristPoint : eristPoints) {
			eristPoint.setResidentWeight(dao.getWeightByLL(eristPoint.getLongitude(), eristPoint.getLatitude()));
		}
		return this;
	}
	
	public EristPoints solveWeights() {
		for (EristPoint eristPoint : eristPoints) {
			double sum=0;
			double regular=0;
			if (eristPoint.getExpertWeight()>=0){
				sum+=eristPoint.getExpertWeight()*questionnaireWeights.get(0);
				regular+=questionnaireWeights.get(0);
			}
			if (eristPoint.getMonitorWeight()>=0){
				sum+=eristPoint.getMonitorWeight()*questionnaireWeights.get(1);
				regular+=questionnaireWeights.get(1);
			}
			if (eristPoint.getResidentWeight()>=0){
				sum+=eristPoint.getResidentWeight()*questionnaireWeights.get(2);
				regular+=questionnaireWeights.get(2);
			}
			eristPoint.setFinalWeight(sum/regular);
		}
		return this;
	}
	
	public List<EristPoint> gEristPoints() {
		return eristPoints;
		
	}
	
	public EristPoints getLevels() {
		for (EristPoint eristPoint : eristPoints) {
			for (int i=0; i<levelWeights.size(); i++){
				if (eristPoint.getFinalWeight()>=levelWeights.get(i)){
					eristPoint.setLevel(i);
					break;
				}
			}
		}
		return this;
	}
	
	public String toJsonByLevel(int level) {
		JSONArray jArray=new JSONArray();
		for (EristPoint eristPoint : eristPoints) {
			if (eristPoint.getLevel()==level){
				Map<String, String> map=new HashMap<>();
				map.put("longitude", eristPoint.getLongitude()+"");
				map.put("latitude", eristPoint.getLatitude()+"");
				jArray.put(map);
			}
		}
		JSONObject jObject=new JSONObject();
		jObject.put("points", jArray);
		return jObject.toString();
	}
	public String toJson() {
		return null;
	}
}
