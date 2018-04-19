package questionnaire;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class QuestionnaireIntf {
	//经纬度
	private double longitude;
	private double latitude;
	//记录查表后对应的权值
	protected List<Double> answers;
	@Autowired
	protected List<Question> questions;
	
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public List<Question> getQuestions() {
		return questions;
	}
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
	
	//从http请求提取经纬度
	public void getLL(HttpServletRequest request) throws NullPointerException, NumberFormatException{
		String st=request.getParameter("longitude");
		setLongitude(Double.parseDouble(st));
		st=request.getParameter("latitude");
		setLatitude(Double.parseDouble(st));
	}
	//从http请求提取数据并得出权值
	public abstract void getAnswers(HttpServletRequest request) throws Exception;
	//计算问卷的综合权值
	public double solveWeight(){
		double result=0;
		for(int i=0;i<answers.size();i++){
			result+=answers.get(i)*questions.get(i).getWeight();
		}
		return result;
	}
}
