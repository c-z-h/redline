package knowledgeGraph;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.RDFReader;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.SimpleSelector;

import jdk.nashorn.internal.ir.RuntimeNode.Request;



@WebServlet("/KnowledgeServlet")
public class KnowledgeServlet extends HttpServlet{
	
	public static final String NS = "http://www.semanticweb.org/administrator/ontologies/2018/3/untitled-ontology-30#";
	
	public KnowledgeServlet() {
		super();
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String keyword = req.getParameter("keyword");
		if ("".equals(keyword)||keyword == null) keyword = "redline";
		String path=req.getSession().getServletContext().getRealPath("")+"redline.owl";
		OntClass ontology = getOntClass(keyword, path);
		Entity entity = null;
		req.setAttribute("entityList", getHierarchy(ontology));
		if (ontology == null)
			entity = new Entity("","","");
		else
			entity = new Entity(ontology.getLocalName(), "本体：",ontology.getComment(null));
		req.setAttribute("ontology", entity);
		req.getRequestDispatcher("knowledge_search_result.jsp").forward(req, resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	public List<Entity> getHierarchy(OntClass ontology) {
		List<Entity> list = new ArrayList<>();
		if (ontology == null) 
			return list;
		Iterator<OntClass> itr1 = ontology.listSubClasses();
		Iterator<OntClass> itr2 = ontology.listSuperClasses();
		while (itr1.hasNext()) {
			OntClass temp = itr1.next();
			list.add(new Entity(temp.getLocalName(),"<span style='color:gray'>子类</span>",temp.getComment(null)));
		}
		while (itr2.hasNext()) {
			OntClass temp = itr2.next();
			list.add(new Entity(temp.getLocalName(),"<span style='color:lightgreen'>父类</span>",temp.getComment(null)));
		}
		return list;
	}
	
	public OntClass getOntClass(String keyword, String path) {
		OntModel model = ModelFactory.createOntologyModel();
		
		File file=new File(path);
		FileInputStream f1 = null;
		OntClass ans = null;
		try {
			f1 = new FileInputStream(file);
			RDFReader d = model.getReader();
			d.read(model, f1, "");
	        ans = model.getOntClass(NS+keyword);	   
	        f1.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ans;
	}
	
	
	
//	public static void main(String[] args) {
//		OntModel model = ModelFactory.createOntologyModel();
//		File file=new File("C:\\Users\\Administrator\\Desktop\\redline.owl");
//		FileInputStream f1 = null;
//		OntClass ans = null;
//		try {
//			f1 = new FileInputStream(file);
//			RDFReader d = model.getReader();
//			d.read(model, f1, "");
//	        ans = model.getOntClass("http://www.semanticweb.org/administrator/ontologies/2018/3/untitled-ontology-30#redline");	   
//	        f1.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		System.out.println(ans.getComment(null));
//	}
	
}
