package weighting;

public class EristPoint {
	private double longitude;
	private double latitude;
	private double expertWeight;
	private double monitorWeight;
	private double residentWeight;
	private double finalWeight;
	private int level;

	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}	
	public double getExpertWeight() {
		return expertWeight;
	}
	public void setExpertWeight(double expertWeight) {
		this.expertWeight = expertWeight;
	}
	public double getMonitorWeight() {
		return monitorWeight;
	}
	public void setMonitorWeight(double monitorWeight) {
		this.monitorWeight = monitorWeight;
	}
	public double getResidentWeight() {
		return residentWeight;
	}
	public void setResidentWeight(double residentWeight) {
		this.residentWeight = residentWeight;
	}
	public double getFinalWeight() {
		return finalWeight;
	}
	public void setFinalWeight(double finalWeight) {
		this.finalWeight = finalWeight;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public EristPoint(double longitude, double latitude) {
		this.longitude=longitude;
		this.latitude=latitude;
	}
}
