package segment;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import weighting.EristPoint;

public class PointReader {
	public static List<EristPoint> read(String path){
		List<EristPoint> list=new LinkedList<>();
		try {
			BufferedReader br=new BufferedReader(new FileReader(new File(path)));
			String st=null;
			int index;
			double weight = 0,lng,lat;
			while ((st=br.readLine())!=null){
				if ((index=st.indexOf(':'))>-1){
					weight=Double.parseDouble(st.substring(0, index));					
				}
				else if ((index=st.indexOf(','))>-1){
					lng=Double.parseDouble(st.substring(0, index));
					lat=Double.parseDouble(st.substring(index+2, st.length()));
					EristPoint p=new EristPoint(lng, lat);
					p.setFinalWeight(weight);
					list.add(p);					
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public static void main(String[] args) {
		PointReader.read("E:\\programme\\java\\erist_v2\\src\\main\\webapp\\111.json");
	}
}
